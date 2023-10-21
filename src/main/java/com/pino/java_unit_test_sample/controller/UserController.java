package com.pino.java_unit_test_sample.controller;

import com.pino.java_unit_test_sample.model.model.CreateUserDTO;
import com.pino.java_unit_test_sample.model.model.UserDTO;
import com.pino.java_unit_test_sample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> findAllUser() {
        return userService.findAll();
    }

    @PostMapping
    public void createUser(@RequestBody CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
    }

    @DeleteMapping("{identityNumber}")
    public void deleteUser(@PathVariable String identityNumber) {
        userService.deleteUser(identityNumber);
    }
}