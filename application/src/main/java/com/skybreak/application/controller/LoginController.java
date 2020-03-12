package com.skybreak.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybreak.application.domain.entity.User;
import com.skybreak.application.exception.UsernameNotFoundException;
import com.skybreak.application.service.UserService;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private UserService userService;
    private ObjectMapper objectMapper;

    public LoginController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User login(@RequestBody String userDTO) throws IOException {
        final User user = objectMapper.readValue(userDTO, User.class);
        User existingUser = userService.userAttemptLogin(user);
        if (existingUser != null) {
            existingUser.setPassword(null);
        }
        return existingUser;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createNewUser(@RequestBody String userDTO) throws IOException {
        try {
            final User user = objectMapper.readValue(userDTO, User.class);
            return userService.registerNewUser(user) != null;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

}
