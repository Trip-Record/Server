package com.triprecord.triprecord.user;

import com.triprecord.triprecord.global.util.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        userService.signup(userCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("회원가입에 성공했습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("Authorization", token));
    }
}