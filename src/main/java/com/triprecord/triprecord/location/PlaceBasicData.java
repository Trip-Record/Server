package com.triprecord.triprecord.location;

import com.triprecord.triprecord.location.entity.Place;

public record PlaceBasicData(
        Long placeId,
        String countryName,
        String placeName
) {
    public static PlaceBasicData fromPlace(Place place){
        return new PlaceBasicData(
                place.getPlaceId(),
                place.getPlaceCountry().getCountryName(),
                place.getPlaceName()
        );
    }
}
