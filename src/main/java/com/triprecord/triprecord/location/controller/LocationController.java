package com.triprecord.triprecord.location.controller;

import com.triprecord.triprecord.location.PlaceService;
import com.triprecord.triprecord.location.dto.LocationInfoGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<LocationInfoGetResponse>> locationInfo(){
        return ResponseEntity.ok().body(placeService.getLocationInfo());
    }
}
