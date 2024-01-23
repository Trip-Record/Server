package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.PlaceRepository;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.record.repository.RecordPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecordPlaceService {

    private final PlaceRepository placeRepository;
    private final RecordPlaceRepository recordPlaceRepository;

    @Transactional
    public void uploadRecordPlace(Record record, Long placeId){
        Place linkedPlace = getPlaceOrException(placeId);
        RecordPlace recordPlace = RecordPlace.builder()
                .recordPlace(linkedPlace)
                .linkedRecord(record)
                .build();
        recordPlaceRepository.save(recordPlace);
    }

    private Place getPlaceOrException(Long placeId) {
        return placeRepository.findByPlaceId(placeId).orElseThrow(() ->
                new TripRecordException(ErrorCode.PLACE_NOT_FOUNT));
    }
}
