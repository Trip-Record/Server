package com.triprecord.triprecord.record.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record RecordCreateRequest(
        @NotNull String recordTitle,
        @NotNull String recordContent,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull @Size(min = 1, max = 3) List<Long> placeIds,
        @Size(max = 10) List<MultipartFile> recordImages
        ) {
}
