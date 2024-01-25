package com.triprecord.triprecord.location;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place getPlaceOrException(Long placeId) {
        return placeRepository.findByPlaceId(placeId).orElseThrow(() ->
                new TripRecordException(ErrorCode.PLACE_NOT_FOUNT));
    }
}
