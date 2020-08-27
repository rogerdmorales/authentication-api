package br.com.roger.authenticationapi.service;

import br.com.roger.authenticationapi.constants.Constants;
import br.com.roger.authenticationapi.domain.TokenInfo;
import br.com.roger.authenticationapi.enums.TokenType;
import br.com.roger.authenticationapi.utils.Converter;
import br.com.roger.authenticationapi.web.dto.TokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static br.com.roger.authenticationapi.constants.Constants.*;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class TokenParser {

    public static List< String > retrieveRoles( String token, String secret, TokenType type ) {
        Claims claims = parseToken( token, secret, type );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(
                claims.get( TOKEN_ROLES ),
                mapper.getTypeFactory().constructCollectionType(List.class, String.class));
    }

    public static TokenInfo retrieveTokenInfo( String token, String secret, TokenType type ) {
        Claims claims = parseToken( token, secret, type );
        return TokenInfo.builder()
                .username( claims.getSubject() )
                .expirationTime( claims.getExpiration().getTime() )
                .secretKey( secret )
                .build();
    }

    public static boolean validateTokensIdentity( TokenDTO tokenDTO, String secret ) {
        String accessTokenIdentity = retrieveIdentityFromToken( tokenDTO.getAccessToken(), secret, TokenType.ACCESS_TOKEN );
        String refreshTokenIdentity = retrieveIdentityFromToken( tokenDTO.getRefreshToken(), secret, TokenType.REFRESH_TOKEN );

        return accessTokenIdentity.equals( refreshTokenIdentity );
    }

    private static Claims parseToken( String token, String secret, TokenType type ) {
        String tokenWithoutPrefix = token.replace( TOKEN_PREFIX, "" );

        Claims body = Jwts.parser()
                .setAllowedClockSkewSeconds( 120 )
                .setSigningKey( Converter.convertToBase64( secret ) )
                .parseClaimsJws( tokenWithoutPrefix )
                .getBody();

        if( !type.toString().equals( body.get( TOKEN_TYPE ) ) ) {
            throw new JwtException( "Invalid token type" );
        }

        return body;
    }


    private static String retrieveIdentityFromToken( String token, String secret, TokenType type ) {
        try {
            Claims claims = parseToken( token, secret, type );
            return ( String ) claims.get( TOKEN_IDENTITY );
        } catch( ExpiredJwtException e ) {
            Claims claims = e.getClaims();
            return ( String ) claims.get( TOKEN_IDENTITY );
        }
    }
}
