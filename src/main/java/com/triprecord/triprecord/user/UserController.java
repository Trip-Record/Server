package com.triprecord.triprecord.user;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            URI uri = URI.create("/users/" + userService.signup(userCreateRequest));
            return ResponseEntity.created(uri).build();
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            return ResponseEntity.ok().body(Map.of("Authorization", userService.login(userLoginRequest))); // 토큰 body에 담아서 보냄
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
