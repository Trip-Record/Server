package com.triprecord.triprecord.location;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.dto.LocationInfoGetResponse;
import com.triprecord.triprecord.location.entity.Continent;
import com.triprecord.triprecord.location.entity.Country;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.location.repository.ContinentRepository;
import com.triprecord.triprecord.location.repository.CountryRepository;
import com.triprecord.triprecord.location.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ContinentRepository continentRepository;
    private final CountryRepository countryRepository;

    public Place getPlaceOrException(Long placeId) {
        return placeRepository.findByPlaceId(placeId).orElseThrow(() ->
                new TripRecordException(ErrorCode.PLACE_NOT_FOUND));
    }

    public List<LocationInfoGetResponse> getLocationInfo(){

        List<Continent> continentList = continentRepository.findAll();

        List<LocationInfoGetResponse> locationInfoGetResponseList = new ArrayList<>();

        for(Continent continent : continentList){
            List<Country> countryList = countryRepository.findCountryByContinent(continent);
            for(Country country : countryList){
                List<Place> placeList = placeRepository.findAllByPlaceCountry(country);
                locationInfoGetResponseList.add(LocationInfoGetResponse.of(continent, placeList));
            }
        }

        return locationInfoGetResponseList;
    }
}
