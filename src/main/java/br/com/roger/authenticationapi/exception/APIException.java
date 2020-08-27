package br.com.roger.authenticationapi.exception;

import br.com.roger.authenticationapi.enums.APIErrors;
import lombok.Getter;

@Getter
public class APIException extends RuntimeException {

    private final String code;
    private final transient Object detail;

    public APIException( final APIErrors error ) {
        this(error.getCode(), error.getMessage(), null, null);
    }

    public APIException( final APIErrors error, final Object detail) {
        this(error.getCode(), error.getMessage(), detail, null);
    }

    public APIException( final APIErrors error, Throwable cause ) {
        this(error.getCode(), error.getMessage(), null, cause);
    }

    public APIException( final APIErrors error, final Object detail, Throwable cause ) {
        this(error.getCode(), error.getMessage(), detail, cause);
    }

    protected APIException( final String code, String message, Object detail, Throwable cause ) {
        super(message, cause);
        this.code = code;
        this.detail = detail;
    }
}
