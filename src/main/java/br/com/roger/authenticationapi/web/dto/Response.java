package br.com.roger.authenticationapi.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Response< T > {

    private T body;
    private List< ErrorResponse > errorMessages;

    public Response( T body ) {
        this( body, null );
    }

    public Response( List< ErrorResponse > errorMessages ) {
        this( null, errorMessages );
    }

    public Response( ErrorResponse errorMessage ) {
        this( null, Collections.singletonList( errorMessage ) );
    }

    protected Response( T body, List< ErrorResponse > errorMessages ) {
        this.body = body;
        this.errorMessages = errorMessages;
    }

}
