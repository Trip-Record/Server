package com.triprecord.triprecord.record.controller;


import com.triprecord.triprecord.record.controller.request.RecordCreateRequest;
import com.triprecord.triprecord.record.service.RecordService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    @PostMapping()
    public ResponseEntity<Map<String, String>> createRecord(Authentication authentication, @Valid RecordCreateRequest request){
        recordService.createRecord(Long.valueOf(authentication.getName()), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","기록 생성에 성공했습니다."));
    }

}
