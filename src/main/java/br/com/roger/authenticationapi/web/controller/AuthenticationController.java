package br.com.roger.authenticationapi.web.controller;

import br.com.roger.authenticationapi.service.AuthenticationService;
import br.com.roger.authenticationapi.web.dto.LoginDTO;
import br.com.roger.authenticationapi.web.dto.Response;
import br.com.roger.authenticationapi.web.dto.TokenDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api( value = "API Authentication", tags = "authentication" )
@RequestMapping( "${application.path}" )
public class AuthenticationController extends AbstractController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping( "/login" )
    @ApiOperation( "Login" )
    public Response< TokenDTO > authenticate( @Valid @RequestBody @ApiParam( name = "loginDTO", required = true ) LoginDTO loginDTO ) {
        return new Response<>( authenticationService.authenticate( loginDTO ) );
    }

    @PostMapping( "/refresh" )
    @ApiOperation( "Refresh" )
    public Response< TokenDTO > refreshToken( @Valid @RequestBody @ApiParam( name = "tokenDTO", required = true) TokenDTO tokenDTO ) {
        return new Response<>( authenticationService.renewTokens( tokenDTO ) );
    }
}
