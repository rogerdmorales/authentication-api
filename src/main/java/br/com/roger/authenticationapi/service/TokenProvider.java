package br.com.roger.authenticationapi.service;

import br.com.roger.authenticationapi.domain.TokenInfo;
import br.com.roger.authenticationapi.enums.TokenType;
import br.com.roger.authenticationapi.utils.Converter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Date;

import static br.com.roger.authenticationapi.constants.Constants.*;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class TokenProvider {

    private static final Logger LOGGER = LogManager.getLogger( TokenProvider.class );

    public static String createToken( TokenInfo tokenInfo, TokenType tokenType ) {
        Claims claims = Jwts.claims().setSubject( tokenInfo.getUsername() );
        claims.put( TOKEN_TYPE, tokenType );
        claims.put( TOKEN_IDENTITY, Converter.convertToBase64( createTokenIdentity( tokenInfo.getUsername() ) ) );
        claims.put( TOKEN_ROLES, tokenInfo.getRoles() );

        Date now = new Date();
        Date tokenExpirationTime = new Date( now.getTime() + tokenInfo.getExpirationTime() );

        LOGGER.info( "Generated {} at {}", tokenType, LocalDateTime.now() );

        return Jwts.builder()
                .setClaims( claims )
                .setIssuedAt( now )
                .setExpiration( tokenExpirationTime )
                .signWith( SignatureAlgorithm.HS256, Converter.convertToBase64( tokenInfo.getSecretKey() ) )
                .compact();
    }

    public static String createTokenIdentity( String userId ) {
        return userId + LocalDateTime.now();
    }
}
