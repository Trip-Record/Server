package com.triprecord.triprecord.record.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@NotNull
public record RecordModifyRequest(
        @NotBlank String changedRecordTitle,
        @NotBlank String changedRecordContent,
        LocalDate changedStartDate,
        LocalDate changedEndDate,
        @Size(max = 3) List<Long> deletePlaceIds,
        @Size(max = 3) List<Long> addPlaceIds,
        @Size(max = 10) List<String> deleteImages,
        @Size(max = 10) List<MultipartFile> addImages
) {
}
