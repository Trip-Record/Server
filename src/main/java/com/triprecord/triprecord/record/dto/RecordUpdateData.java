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

    public static RecordUpdateData fromRequest(Record record, RecordModifyRequest request){
        return RecordUpdateData.builder()
                .recordTitle((request.changedRecordTitle()==null)? record.getRecordTitle(): request.changedRecordTitle())
                .recordContent((request.changedRecordContent()==null)? record.getRecordContent(): request.changedRecordContent())
                .startDate((request.changedStartDate()==null)? record.getTripStartDate(): request.changedStartDate())
                .endDate((request.changedEndDate()==null)? record.getTripEndDate(): request.changedEndDate())
                .build();
    }

}
