package com.triprecord.triprecord.record.dto;

import com.triprecord.triprecord.location.dto.PlaceBasicData;
import com.triprecord.triprecord.record.entity.Record;
import java.time.LocalDate;
import java.util.List;

public record RecordInfo(
        List<PlaceBasicData> recordPlaces,
        Long recordId,
        String recordTitle,
        String recordContent,
        LocalDate tripStartDate,
        LocalDate tripEndDate,
        List<RecordImageData> recordImages,
        Long likeCount,
        Long commentCount
) {

    public static RecordInfo of(Record record,
                                List<PlaceBasicData> recordPlaces,
                                List<RecordImageData> images,
                                Long likeCount,
                                Long commentCount){
        return new RecordInfo(
                recordPlaces,
                record.getRecordId(),
                record.getRecordTitle(),
                record.getRecordContent(),
                record.getTripStartDate(),
                record.getTripEndDate(),
                images,
                likeCount,
                commentCount
        );
    }
}
