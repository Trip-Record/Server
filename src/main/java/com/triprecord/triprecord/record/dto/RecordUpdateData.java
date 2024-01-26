package com.triprecord.triprecord.record.dto;

import com.triprecord.triprecord.record.controller.request.RecordModifyRequest;
import com.triprecord.triprecord.record.entity.Record;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RecordUpdateData(
        String recordTitle,
        String recordContent,
        LocalDate startDate,
        LocalDate endDate
) {

    public static RecordUpdateData fromRequest(RecordModifyRequest request){
        return RecordUpdateData.builder()
                .recordTitle(request.changedRecordTitle())
                .recordContent(request.changedRecordContent())
                .startDate(request.changedStartDate())
                .endDate(request.changedEndDate())
                .build();
    }

}
