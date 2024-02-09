package com.triprecord.triprecord.location.dto;

import com.triprecord.triprecord.location.entity.Place;

public record PlaceRankGetResponse(
        Integer rank,
        Integer visitCount,
        PlaceBasicData placeBasicData

) {
    public static PlaceRankGetResponse of(Integer rank, Integer visitCount, Place place){
        return new PlaceRankGetResponse(
                rank,
                visitCount,
                PlaceBasicData.fromPlace(place)
        );
    }
}
