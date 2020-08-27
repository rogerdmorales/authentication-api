package br.com.roger.authenticationapi.service;

import br.com.roger.authenticationapi.domain.TokenInfo;
import br.com.roger.authenticationapi.domain.User;
import br.com.roger.authenticationapi.enums.TokenType;
import br.com.roger.authenticationapi.exception.UnauthorizedException;
import br.com.roger.authenticationapi.web.dto.LoginDTO;
import br.com.roger.authenticationapi.web.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value( "${application.jwtExpirationInMs}" )
    private long jwtExpirationTime;

    @Value( "${application.secretKey}" )
    private String secretKey;

    public TokenDTO authenticate( LoginDTO loginDTO ) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate( usernamePasswordAuthenticationToken );
        SecurityContextHolder.getContext().setAuthentication( authentication );

        User user = ( User ) authentication.getDetails();
        List< String > roles = user.getRoles()
                .stream()
                .map( role -> role.getCode() )
                .collect( Collectors.toList() );

        return generateToken( user.getUsername(), roles );
    }

    public TokenDTO renewTokens( TokenDTO tokenDTO ) {
        if ( !TokenParser.validateTokensIdentity( tokenDTO, secretKey ) ) {
            throw new UnauthorizedException();
        }

        TokenInfo tokenInfo = TokenParser.retrieveTokenInfo( tokenDTO.getRefreshToken(), secretKey, TokenType.REFRESH_TOKEN );
        return generateToken( tokenInfo.getUsername(), tokenInfo.getRoles() );
    }

    private TokenDTO generateToken( String username, List< String > roles ) {
        TokenInfo tokenInfo = TokenInfo.builder()
                .username( username )
                .roles( roles )
                .expirationTime( jwtExpirationTime )
                .secretKey( secretKey )
                .build();

        String accessToken = TokenProvider.createToken( tokenInfo, TokenType.ACCESS_TOKEN );
        String refreshToken = TokenProvider.createToken( tokenInfo, TokenType.REFRESH_TOKEN );

        return new TokenDTO( accessToken, refreshToken );
    }
}
