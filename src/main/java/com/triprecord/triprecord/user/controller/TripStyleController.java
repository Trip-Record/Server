package com.triprecord.triprecord.user.controller;

import com.triprecord.triprecord.user.dto.response.TripStyleInfo;
import com.triprecord.triprecord.user.service.TripStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TripStyleController {

    private final TripStyleService tripStyleService;
    @GetMapping("/trip-styles")
    public ResponseEntity<List<TripStyleInfo>> tripStyleInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(tripStyleService.getTripStyleInfo());
    }
}
