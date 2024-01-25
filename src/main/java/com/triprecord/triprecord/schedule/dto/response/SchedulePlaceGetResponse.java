package com.triprecord.triprecord.schedule.dto.response;

import com.triprecord.triprecord.schedule.entity.SchedulePlace;

public record SchedulePlaceGetResponse(
        String placeCountry,
        String placeName
) {
    public static SchedulePlaceGetResponse of(SchedulePlace schedulePlace) {
        return new SchedulePlaceGetResponse(
                schedulePlace.getSchedulePlace().getPlaceCountry().getCountryName(),
                schedulePlace.getSchedulePlace().getPlaceName()
        );
    }
}
