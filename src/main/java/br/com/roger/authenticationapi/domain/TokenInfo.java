package br.com.roger.authenticationapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TokenInfo {
    private String username;
    private List< String > roles;
    private Long expirationTime;
    private String secretKey;
}
