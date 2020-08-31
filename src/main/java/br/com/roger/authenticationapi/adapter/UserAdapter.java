package br.com.roger.authenticationapi.adapter;

import br.com.roger.authenticationapi.domain.Role;
import br.com.roger.authenticationapi.domain.User;
import br.com.roger.authenticationapi.web.dto.RoleDTO;
import br.com.roger.authenticationapi.web.dto.UserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class UserAdapter {

    public static UserDTO fromUser( User user ) {
        return UserDTO.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .roles( fromRoles( user.getRoles() ) )
                .build();
    }

    public static User toUser( UserDTO userDTO ) {
        return User.builder()
                .id( userDTO.getId() )
                .name( userDTO.getName() )
                .email( userDTO.getEmail() )
                .password( userDTO.getPassword() )
                .roles( toRoles( userDTO.getRoles() ) )
                .build();
    }

    public static List< RoleDTO > fromRoles( List< Role > roles ) {
        return Optional.ofNullable( roles )
                .orElse( new ArrayList<>() )
                .stream()
                .map( role -> RoleDTO.builder()
                        .id( role.getId() )
                        .code( role.getCode() )
                        .name( role.getName() )
                        .build() )
                .collect( Collectors.toList() );
    }

    public static List< Role > toRoles( List< RoleDTO > roleDTOs ) {
        return Optional.ofNullable( roleDTOs )
                .orElse( new ArrayList<>() )
                .stream()
                .map( roleDTO -> Role.builder()
                        .id( roleDTO.getId() )
                        .code( roleDTO.getCode() )
                        .name( roleDTO.getName() )
                        .build() )
                .collect( Collectors.toList() );
    }
}
