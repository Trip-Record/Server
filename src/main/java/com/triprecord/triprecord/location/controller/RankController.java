package com.triprecord.triprecord.location.controller;

import com.triprecord.triprecord.location.PlaceService;
import com.triprecord.triprecord.record.controller.response.RecordPlaceRankGetResponse;
import com.triprecord.triprecord.record.service.RecordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranks")
public class RankController {

    private final PlaceService placeService;

    @GetMapping("/months")
    public ResponseEntity<List<RecordPlaceRankGetResponse>> monthRank(@RequestParam("year") String year, @RequestParam("month") String month){
        return ResponseEntity.status(HttpStatus.OK).body(placeService.getMonthlyRank(String.format("%s-%s", year, month)));
    }
}
