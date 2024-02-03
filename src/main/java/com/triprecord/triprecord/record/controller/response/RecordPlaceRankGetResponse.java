package com.triprecord.triprecord.record.controller.response;

import com.triprecord.triprecord.location.PlaceBasicData;
import com.triprecord.triprecord.location.entity.Place;

public record RecordPlaceRankGetResponse(
        PlaceBasicData placeBasicData,
        Integer visitCount,
        Integer rank
) {
    public static RecordPlaceRankGetResponse of(Place place, Integer visitCount, Integer rank){
        return new RecordPlaceRankGetResponse(
                PlaceBasicData.fromPlace(place),
                visitCount,
                rank
        );
    }
}
