package com.triprecord.triprecord.schedule.controller;

import com.triprecord.triprecord.global.util.ResponseMessage;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCommentContentRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCreateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
import com.triprecord.triprecord.schedule.dto.response.ScheduleGetResponse;
import com.triprecord.triprecord.schedule.dto.response.SchedulePageGetResponse;
import com.triprecord.triprecord.schedule.service.ScheduleCommentService;
import com.triprecord.triprecord.schedule.service.ScheduleService;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<ResponseMessage> createSchedule(Authentication authentication,
                                                          @RequestBody @Valid ScheduleCreateRequest request) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleService.createSchedule(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("일정 생성에 성공했습니다."));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ResponseMessage> updateSchedule(Authentication authentication, @PathVariable Long scheduleId,
                                                          @RequestBody ScheduleUpdateRequest request) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleService.updateSchedule(userId, scheduleId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("일정 수정에 성공했습니다."));
    }

    @GetMapping
    public ResponseEntity<SchedulePageGetResponse> getSchedules(@PageableDefault(size = 5) Pageable pageable) {
        SchedulePageGetResponse response = scheduleService.getSchedules(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> getSchedule(@PathVariable Long scheduleId) {
        ScheduleGetResponse response = scheduleService.getSchedule(scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ResponseMessage> deleteSchedule(Authentication authentication,
                                                          @PathVariable Long scheduleId) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleService.deleteSchedule(userId, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("일정 삭제에 성공했습니다."));
    }

    @DeleteMapping("{scheduleId}/likes")
    public ResponseEntity<ResponseMessage> deleteScheduleLike(Authentication authentication,
                                                              @PathVariable Long scheduleId) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleService.deleteScheduleLike(userId, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("일정 좋아요 취소에 성공했습니다."));
    }

    @PostMapping("{scheduleId}/likes")
    public ResponseEntity<ResponseMessage> createScheduleLike(Authentication authentication,
                                                              @PathVariable Long scheduleId) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleService.createScheduleLike(userId, scheduleId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("좋아요 등록에 성공했습니다."));
    }

    @PatchMapping("comments/{scheduleCommentId}")
    public ResponseEntity<ResponseMessage> updateScheduleComment(Authentication authentication,
                                                                 @PathVariable Long scheduleCommentId,
                                                                 @RequestBody @Valid ScheduleCommentContentRequest request) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleCommentService.updateScheduleComment(userId, scheduleCommentId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 수정에 성공했습니다."));
    }

    @DeleteMapping("comments/{scheduleCommentId}")
    public ResponseEntity<ResponseMessage> deleteScheduleComment(Authentication authentication,
                                                                 @PathVariable Long scheduleCommentId) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleCommentService.deleteScheduleComment(userId, scheduleCommentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 삭제에 성공했습니다."));
    }

}
