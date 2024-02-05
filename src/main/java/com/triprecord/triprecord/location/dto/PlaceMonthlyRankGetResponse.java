package com.triprecord.triprecord.location.dto;

import com.triprecord.triprecord.location.entity.Place;

public record PlaceMonthlyRankGetResponse(
        PlaceBasicData placeBasicData,
        Integer visitCount,
        Integer rank
) {
    public static PlaceMonthlyRankGetResponse of(Place place, Integer visitCount, Integer rank){
        return new PlaceMonthlyRankGetResponse(
                PlaceBasicData.fromPlace(place),
                visitCount,
                rank
        );
    }
}
