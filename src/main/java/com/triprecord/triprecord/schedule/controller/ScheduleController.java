package com.triprecord.triprecord.schedule.controller;

import com.triprecord.triprecord.schedule.dto.request.ScheduleCreateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
import com.triprecord.triprecord.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createSchedule(Authentication authentication, @RequestBody @Valid ScheduleCreateRequest request) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleService.createSchedule(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "일정 생성에 성공했습니다."));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Map<String, String>> updateSchedule(Authentication authentication, @PathVariable @Valid Long scheduleId, @RequestBody ScheduleUpdateRequest request) {
        Long userId = Long.valueOf(authentication.getName());
        scheduleService.updateSchedule(userId, scheduleId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", "일정 수정에 성공했습니다."));
    }

}
