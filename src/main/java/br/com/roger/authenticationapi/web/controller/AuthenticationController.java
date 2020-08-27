package br.com.roger.authenticationapi.web.controller;

import br.com.roger.authenticationapi.service.AuthenticationService;
import br.com.roger.authenticationapi.web.dto.LoginDTO;
import br.com.roger.authenticationapi.web.dto.Response;
import br.com.roger.authenticationapi.web.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "${application.path}" )
public class AuthenticationController extends AbstractController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public Response< TokenDTO > authenticate( @RequestBody LoginDTO loginDTO) {
        return new Response<>( authenticationService.authenticate( loginDTO ) );
    }

    @PostMapping("/refresh")
    public Response< TokenDTO > refreshToken( @RequestBody TokenDTO tokenDTO ) {
        return new Response<>( authenticationService.renewTokens( tokenDTO ) );
    }
}
