package com.triprecord.triprecord.record.dto;

import com.triprecord.triprecord.record.entity.RecordImage;

public record RecordImageData(
        Long recordImageId,
        String recordImageURL
) {
    public static RecordImageData fromImage(RecordImage recordImage){
        return new RecordImageData(
                recordImage.getRecordImgId(),
                recordImage.getRecordImgUrl()
        );
    }
}
