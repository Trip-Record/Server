package com.triprecord.triprecord.user.controller;

import com.triprecord.triprecord.global.util.ResponseMessage;
import com.triprecord.triprecord.user.dto.request.UserCreateRequest;
import com.triprecord.triprecord.user.dto.request.UserLoginRequest;
import com.triprecord.triprecord.user.dto.response.UserInfoGetResponse;
import com.triprecord.triprecord.user.dto.response.UserRecordPageResponse;
import com.triprecord.triprecord.user.dto.response.UserSchedulePageResponse;
import com.triprecord.triprecord.user.service.UserService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserInfoGetResponse> userInfo(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserInfo(Long.valueOf(authentication.getName())));
    }

    @PostMapping("/trip-styles/{tripStyleId}")
    public ResponseEntity<ResponseMessage> setUserTripStyle(Authentication authentication,
                                                            @PathVariable Long tripStyleId) {
        Long userId = Long.valueOf(authentication.getName());
        userService.setUserTripStyle(userId, tripStyleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("여행 스타일 등록에 성공했습니다."));
    }

    @GetMapping("/records")
    public ResponseEntity<UserRecordPageResponse> getUserRecords(Authentication authentication,
                                                                 @PageableDefault(size = 5) Pageable pageable) {
        UserRecordPageResponse response = userService.getUserRecords(Long.valueOf(authentication.getName()), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules")
    public ResponseEntity<UserSchedulePageResponse> userSchedules(Authentication authentication,
                                                                  @PageableDefault(size = 5) Pageable pageable) {
        UserSchedulePageResponse response = userService.getUserSchedules(Long.valueOf(authentication.getName()),
                pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
