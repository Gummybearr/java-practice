package com.ververica.demo.backend.users.controller;

import com.ververica.demo.backend.users.dto.UserDto.UserReq;
import com.ververica.demo.backend.users.exception.UserJpaException.DuplicateEmailException;
import com.ververica.demo.backend.users.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @PostMapping("/users")
    @ApiOperation(value = "유저 등록")
    private ResponseEntity<String> postUser(@RequestBody UserReq userRequest) throws DuplicateEmailException {
        return new ResponseEntity<>(userService.postUser(userRequest), HttpStatus.CREATED);
    }
}
