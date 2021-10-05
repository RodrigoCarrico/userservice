package com.demo.userservice.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.userservice.api.dto.RoleToUserDto;
import com.demo.userservice.api.dto.UserDto;
import com.demo.userservice.domain.Role;
import com.demo.userservice.domain.User;
import com.demo.userservice.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.demo.userservice.utils.ConstantUtil.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserController.PATH)
@RequiredArgsConstructor
public class UserController {
    public static final String PATH = "/api";

    private final UserServiceImpl userService;

    @GetMapping("/users")
    @Operation(summary = "Usuários", description = "Retorna uma lista de usuários")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    @Operation(summary = "user", description = "Cria um usuário")
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                                                        .path(UserController.PATH + "/user/save")
                                                        .toString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    @Operation(summary = "Role", description = "Cria uma regra")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                                                        .path(UserController.PATH + "/role/save")
                                                        .toString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    @Operation(summary = "", description = "Adiciona uma regra a um usuário")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserDto dto) {
        userService.addRoleToUser(dto.getUsername(), dto.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    @Operation(summary = "", description = "Refresh Token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_MORE_SPACE)) {
            try {
                String refresh_token = getToken(authorizationHeader);
                DecodedJWT decodedJWT = decodedJWT(authorizationHeader);
                String username = decodedJWT.getSubject();

                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(TIME_SESSION_30_MIN)
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(ROLES, user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(getAlgorithm());

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else {
            throw  new RuntimeException("Refresh token is missing");
        }
    }

}
