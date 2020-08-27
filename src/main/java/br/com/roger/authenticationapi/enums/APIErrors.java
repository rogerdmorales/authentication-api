package br.com.roger.authenticationapi.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.ResourceBundle;

@Getter
public enum APIErrors {
    UNAUTHORIZED("0001", "unauthorized"),
    EMAIL_ALREADY_EXISTS( "0002", "email.already.exists" );

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle( "Messages" );

    private final String code;
    private final String message;

    APIErrors( final String code, final String message ) {
        this.code = code;
        this.message = message;
    }

    public static APIErrors getByCode( String code ) {
        return Arrays.stream( APIErrors.values() )
                .filter( error -> error.code.equals( code ) )
                .findAny()
                .orElse( null );
    }

    public String getMessage() {
        return String.format( resourceBundle.getString( message ) );
    }
}
