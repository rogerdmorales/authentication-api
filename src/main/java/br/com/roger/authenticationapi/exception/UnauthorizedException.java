package br.com.roger.authenticationapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final HttpStatus httpStatus;

    public UnauthorizedException() {
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
