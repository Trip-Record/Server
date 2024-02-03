package com.triprecord.triprecord.record.service;


import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.dto.PlaceBasicData;
import com.triprecord.triprecord.location.PlaceService;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.entity.RecordPlace;
import com.triprecord.triprecord.record.repository.RecordPlaceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecordPlaceService {

    private  final PlaceService placeService;
    private final RecordPlaceRepository recordPlaceRepository;
    private final long PLACE_MAX_SIZE = 3;

    public void checkPlaceSizeValid(Record record, List<Long> deleteRequestIds, List<Long> uploadRequestIds){
        long existingSize = recordPlaceRepository.countByLinkedRecord(record);
        long deleteSize = (deleteRequestIds==null)?0:deleteRequestIds.size();
        long addSize = (uploadRequestIds==null)?0:uploadRequestIds.size();
        long changingSize = existingSize - deleteSize + addSize;
        if(changingSize > PLACE_MAX_SIZE){
            throw new TripRecordException(ErrorCode.INVALID_RECORD_PLACE_SIZE);
        }
    }

    @Transactional
    public void createRecordPlaces(Record record, List<Long> uploadRequestIds){
        if(uploadRequestIds==null || uploadRequestIds.isEmpty()) return;
        for(Long placeId : uploadRequestIds){
            Place place = placeService.getPlaceOrException(placeId);
            RecordPlace recordPlace = RecordPlace.builder()
                    .recordPlace(place)
                    .linkedRecord(record)
                    .build();
            recordPlaceRepository.save(recordPlace);
        }
    }

    @Transactional(readOnly = true)
    public List<PlaceBasicData> getRecordPlaceBasicData(Record record) {
        List<Place> linkedPlaceList = recordPlaceRepository.findAllPlaceByLinkedRecord(record);
        return linkedPlaceList.stream().map(PlaceBasicData::fromPlace).toList();
    }

    @Transactional
    public void deleteRecordPlaces(Record record, List<Long> deleteRequestIds){
        if(deleteRequestIds==null || deleteRequestIds.isEmpty()) return;
        for(Long placeId : deleteRequestIds){
            Place linkedPlace = placeService.getPlaceOrException(placeId);
            recordPlaceRepository.deleteByLinkedRecordAndRecordPlace(record, linkedPlace);
        }
    }

}
