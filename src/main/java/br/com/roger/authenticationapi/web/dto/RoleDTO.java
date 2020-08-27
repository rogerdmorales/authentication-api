package br.com.roger.authenticationapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String code;
    private String name;
}
