package br.com.roger.authenticationapi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Base64;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class Converter {

    public static String convertToBase64( String value ) {
        return Base64.getEncoder().encodeToString( value.getBytes() );
    }

    public static String convertFromBase64( String value ) {
        byte[] result = Base64.getDecoder().decode( value.getBytes() );
        return  new String( result );
    }
}
