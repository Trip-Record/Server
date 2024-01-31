package com.triprecord.triprecord.location.dto;

import com.triprecord.triprecord.location.entity.Continent;
import com.triprecord.triprecord.location.entity.Place;

import java.util.List;

public record LocationInfoGetResponse(
        String continentName,
        List<PlaceBasicData> placeBasicDataList

) {
    public static LocationInfoGetResponse of(Continent continent,List<Place> placeList){
        return new LocationInfoGetResponse(
                continent.getContinentName(),
                placeList.stream()
                        .map(PlaceBasicData::fromPlace)
                        .toList()
        );
    }
}
