package com.triprecord.triprecord.user.controller;

<<<<<<< HEAD:src/main/java/com/triprecord/triprecord/user/UserController.java
import com.triprecord.triprecord.global.util.ResponseMessage;
=======
import com.triprecord.triprecord.user.controller.dto.request.UserCreateRequest;
import com.triprecord.triprecord.user.controller.dto.response.UserInfoResponse;
import com.triprecord.triprecord.user.controller.dto.request.UserLoginRequest;
import com.triprecord.triprecord.user.service.UserService;
>>>>>>> a4a5d55 ([REFACTOR] User 관련 패키지 정리):src/main/java/com/triprecord/triprecord/user/controller/UserController.java
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/informations")
    public ResponseEntity<UserInfoResponse> userInfo(Authentication authentication){
        return ResponseEntity.ok().body(userService.findUserData(Long.valueOf(authentication.getName())));
    }
}