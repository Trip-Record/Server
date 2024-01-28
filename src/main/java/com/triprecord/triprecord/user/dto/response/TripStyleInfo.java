package com.triprecord.triprecord.user.dto.response;

import com.triprecord.triprecord.user.entity.TripStyle;

public record TripStyleInfo(
        Long tripStyleId,
        String tripStyleName,
        String tripStyleImg,
        String tripStyleDescription
) {
    public static TripStyleInfo of(TripStyle tripStyle){
        return new TripStyleInfo(
                tripStyle.getTripStyleId(),
                tripStyle.getTripStyleName(),
                tripStyle.getTripStyleImg(),
                tripStyle.getTripStyleDescription()
        );
    }
}
