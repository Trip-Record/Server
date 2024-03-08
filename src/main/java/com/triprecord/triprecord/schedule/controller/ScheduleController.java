package com.triprecord.triprecord.schedule.controller;

import com.triprecord.triprecord.global.util.ResponseMessage;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCommentContentRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCreateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
import com.triprecord.triprecord.schedule.dto.response.ScheduleCommentPageGetResponse;
import com.triprecord.triprecord.schedule.dto.response.ScheduleGetResponse;
import com.triprecord.triprecord.schedule.dto.response.SchedulePageGetResponse;
import com.triprecord.triprecord.schedule.service.ScheduleCommentService;
import com.triprecord.triprecord.schedule.service.ScheduleService;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.service.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleCommentService scheduleCommentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createSchedule(Authentication authentication,
                                                          @RequestBody @Valid ScheduleCreateRequest request) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleService.createSchedule(user, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("일정 생성에 성공했습니다."));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ResponseMessage> updateSchedule(Authentication authentication, @PathVariable Long scheduleId,
                                                          @RequestBody ScheduleUpdateRequest request) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleService.updateSchedule(user, scheduleId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("일정 수정에 성공했습니다."));
    }

    @GetMapping
    public ResponseEntity<SchedulePageGetResponse> getSchedules(Authentication authentication,
                                                                @PageableDefault(size = 5) Pageable pageable) {
        Optional<User> user = Optional.ofNullable(
                (authentication == null) ? null
                        : userService.getUserOrException(Long.parseLong(authentication.getName())));
        SchedulePageGetResponse response = scheduleService.getSchedules(user, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> getSchedule(Authentication authentication,
                                                           @PathVariable Long scheduleId) {
        Optional<User> user = Optional.ofNullable(
                (authentication == null) ? null
                        : userService.getUserOrException(Long.parseLong(authentication.getName())));
        ScheduleGetResponse response = scheduleService.getSchedule(user, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ResponseMessage> deleteSchedule(Authentication authentication,
                                                          @PathVariable Long scheduleId) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleService.deleteSchedule(user, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("일정 삭제에 성공했습니다."));
    }

    @DeleteMapping("{scheduleId}/likes")
    public ResponseEntity<ResponseMessage> deleteScheduleLike(Authentication authentication,
                                                              @PathVariable Long scheduleId) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleService.deleteScheduleLike(user, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("일정 좋아요 취소에 성공했습니다."));
    }

    @PostMapping("{scheduleId}/likes")
    public ResponseEntity<ResponseMessage> createScheduleLike(Authentication authentication,
                                                              @PathVariable Long scheduleId) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleService.createScheduleLike(user, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("좋아요 등록에 성공했습니다."));
    }

    @GetMapping("{scheduleId}/comments")
    public ResponseEntity<ScheduleCommentPageGetResponse> getScheduleComments(Authentication authentication,
                                                                              @PathVariable Long scheduleId,
                                                                              @PageableDefault(size = 5) Pageable pageable) {
        Optional<User> user = Optional.ofNullable(
                (authentication == null) ? null
                        : userService.getUserOrException(Long.parseLong(authentication.getName())));
        ScheduleCommentPageGetResponse response = scheduleService.getScheduleComments(user, scheduleId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("{scheduleId}/comments")
    public ResponseEntity<ResponseMessage> createScheduleComment(Authentication authentication,
                                                                 @PathVariable Long scheduleId,
                                                                 @RequestBody @Valid ScheduleCommentContentRequest request) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleService.createScheduleComment(user, scheduleId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("댓글 등록에 성공했습니다."));
    }

    @PatchMapping("comments/{scheduleCommentId}")
    public ResponseEntity<ResponseMessage> updateScheduleComment(Authentication authentication,
                                                                 @PathVariable Long scheduleCommentId,
                                                                 @RequestBody @Valid ScheduleCommentContentRequest request) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleCommentService.updateScheduleComment(user, scheduleCommentId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 수정에 성공했습니다."));
    }

    @DeleteMapping("comments/{scheduleCommentId}")
    public ResponseEntity<ResponseMessage> deleteScheduleComment(Authentication authentication,
                                                                 @PathVariable Long scheduleCommentId) {
        User user = userService.getUserOrException(Long.valueOf(authentication.getName()));
        scheduleCommentService.deleteScheduleComment(user, scheduleCommentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 삭제에 성공했습니다."));
    }

}
