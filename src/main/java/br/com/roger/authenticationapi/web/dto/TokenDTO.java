package br.com.roger.authenticationapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;
}
