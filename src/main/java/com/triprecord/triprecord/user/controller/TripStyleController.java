package com.triprecord.triprecord.user.controller;

import com.triprecord.triprecord.user.dto.response.TripStyleInfo;
import com.triprecord.triprecord.user.service.TripStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TripStyleController {

    private final TripStyleService tripStyleService;
    @PostMapping("/trip-styles")
    public ResponseEntity<List<TripStyleInfo>> tripStyleInfo(){
        return ResponseEntity.ok().body(tripStyleService.getTripStyleInfo());
    }
}
