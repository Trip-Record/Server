package com.triprecord.triprecord.user.controller;

import com.triprecord.triprecord.user.controller.dto.request.UserCreateRequest;
import com.triprecord.triprecord.user.controller.dto.response.UserInfoResponse;
import com.triprecord.triprecord.user.controller.dto.request.UserLoginRequest;
import com.triprecord.triprecord.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> signup(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        URI uri = URI.create("/users/" + userService.signup(userCreateRequest));
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest);
        return ResponseEntity.ok().body(Map.of("Authorization", token));
    }

    @GetMapping("/informations")
    public ResponseEntity<UserInfoResponse> userInfo(Authentication authentication){
        return ResponseEntity.ok().body(userService.findUserData(Long.valueOf(authentication.getName())));
    }
}
