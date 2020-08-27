package br.com.roger.authenticationapi.exception;

import br.com.roger.authenticationapi.enums.APIErrors;
import br.com.roger.authenticationapi.web.dto.ErrorResponse;
import br.com.roger.authenticationapi.web.dto.Response;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( {APIException.class} )
    public Response handleApiException( APIException apiException, WebRequest request ) {
        final ErrorResponse error = new ErrorResponse( apiException.getCode(), apiException.getMessage(), apiException.getDetail() );
        return new Response( error );
    }

    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( JwtException.class )
    public Response handleJwtException( JwtException jwtException, WebRequest request ) {
        final ErrorResponse error = new ErrorResponse( APIErrors.UNAUTHORIZED.getCode(), APIErrors.UNAUTHORIZED.getMessage(), "Authentication failure" );
        return new Response( error );
    }

    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( SignatureException.class )
    public Response handleSignatureException( SignatureException signatureException, WebRequest request ) {
        final ErrorResponse error = new ErrorResponse( APIErrors.UNAUTHORIZED.getCode(), APIErrors.UNAUTHORIZED.getMessage(), "Invalid signature" );
        return new Response( error );
    }

    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( ExpiredJwtException.class )
    public Response handleSignatureException( ExpiredJwtException expiredJwtException, WebRequest request ) {
        final ErrorResponse error = new ErrorResponse( APIErrors.UNAUTHORIZED.getCode(), APIErrors.UNAUTHORIZED.getMessage(), "Token expired" );
        return new Response( error );
    }

    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( UnauthorizedException.class )
    public Response handleUnauthorizedException( UnauthorizedException unauthorizedException, WebRequest request ) {
        final ErrorResponse error = new ErrorResponse( APIErrors.UNAUTHORIZED.getCode(), APIErrors.UNAUTHORIZED.getMessage(), request.getContextPath() );
        return new Response( error );
    }

    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( AuthenticationException.class )
    public Response handleAuthenticationException( AuthenticationException authenticationException, WebRequest request ) {
        final ErrorResponse error = new ErrorResponse( APIErrors.UNAUTHORIZED.getCode(), APIErrors.UNAUTHORIZED.getMessage(), authenticationException.getMessage() );
        return new Response( error );
    }
}
