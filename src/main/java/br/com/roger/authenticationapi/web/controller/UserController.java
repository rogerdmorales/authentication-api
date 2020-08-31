package br.com.roger.authenticationapi.web.controller;

import br.com.roger.authenticationapi.adapter.UserAdapter;
import br.com.roger.authenticationapi.service.UserService;
import br.com.roger.authenticationapi.web.dto.Response;
import br.com.roger.authenticationapi.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping( "${application.path}/users" )
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Response< UserDTO > createUser( @RequestBody @Valid UserDTO userDTO ) {
        userDTO = UserAdapter.fromUser( userService.createUser( UserAdapter.toUser( userDTO ) ) );
        return new Response<>( userDTO );
    }
}
