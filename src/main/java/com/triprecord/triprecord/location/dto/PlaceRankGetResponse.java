package com.triprecord.triprecord.location.dto;

import com.triprecord.triprecord.location.entity.Place;

public record PlaceRankGetResponse(
        PlaceBasicData placeBasicData,
        Integer visitCount,
        Integer rank
) {
    public static PlaceRankGetResponse of(Place place, Integer visitCount, Integer rank){
        return new PlaceRankGetResponse(
                PlaceBasicData.fromPlace(place),
                visitCount,
                rank
        );
    }
}
