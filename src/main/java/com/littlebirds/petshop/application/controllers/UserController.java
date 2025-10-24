package com.littlebirds.petshop.application.controllers;

import com.littlebirds.petshop.application.services.TokenService;
import com.littlebirds.petshop.application.services.UserService;
import com.littlebirds.petshop.domain.dtos.user.*;
import com.littlebirds.petshop.domain.enums.UserRole;
import com.littlebirds.petshop.domain.models.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")

public class UserController {


    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    private UserService userService;

    public UserController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/adminRegister")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<AdminListDto> adminRegister(@RequestBody @Valid AdminRegisterDto register) {

        User response = userService.createUserAdmin(register);

        return ResponseEntity.ok().body(new AdminListDto(response));
    }
    @PostMapping("/register")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<AdminListDto> register(@RequestBody @Valid UserRegisterDto register) {

        User response = userService.createCommonUser(register);

        return ResponseEntity.ok().body(new AdminListDto(response));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody @Valid LoginDto login) {

        var authToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());

        var authentication = authenticationManager.authenticate(authToken);

        var user = (User) authentication.getPrincipal();

        var tokenJwt = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new JwtDto(tokenJwt, user.getRole().name())
        );
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<UserListDto>> getAllUsers(
            @PageableDefault(sort = "email", size = 10) Pageable pagination,
            @RequestParam(name = "role", required = false) UserRole role) {

        Page<UserListDto> response = userService.findAllUsers(pagination, role);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserListDto> getUserById(@PathVariable UUID id) {

        var response = userService.findUserById(id);

        return new ResponseEntity<>(new UserListDto(response), HttpStatus.OK);
    }


    @PutMapping("/reactive/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity reactiveUser(@PathVariable UUID id) {

        userService.reactiveUser(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")

    public ResponseEntity inactiveUser(@PathVariable UUID id) {
        userService.inactiveUser(id);

        return ResponseEntity.noContent().build();
    }


}
