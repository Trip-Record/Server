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
import com.triprecord.triprecord.location.dto.PlaceRankGetResponse;
import com.triprecord.triprecord.record.repository.RecordPlaceRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ContinentRepository continentRepository;
    private final CountryRepository countryRepository;
    private final RecordPlaceRepository recordPlaceRepository;


    public Place getPlaceOrException(Long placeId) {
        return placeRepository.findByPlaceId(placeId).orElseThrow(() ->
                new TripRecordException(ErrorCode.PLACE_NOT_FOUND));
    }

    public List<LocationInfoGetResponse> getLocationInfo() {

        List<Continent> continentList = continentRepository.findAll();

        List<LocationInfoGetResponse> locationInfoGetResponseList = new ArrayList<>();

        for (Continent continent : continentList) {
            List<Country> countryList = countryRepository.findALLByContinent(continent);
            for (Country country : countryList) {
                List<Place> placeList = placeRepository.findAllByPlaceCountry(country);
                locationInfoGetResponseList.add(LocationInfoGetResponse.of(continent, placeList));
            }
        }

        return locationInfoGetResponseList;
    }

    public List<PlaceRankGetResponse> getMonthlyRank(String date) {
        List<PlaceRankGetResponse> placeRankGetResponseList = new ArrayList<>();
        List<Place> placeList = placeRepository.findAll();

        for (Place place : placeList) {
            Integer visitCount = recordPlaceRepository.getCount(place.getPlaceId(), date).orElseGet(() -> 0);
            Integer rank = recordPlaceRepository.getRank(place.getPlaceId(), date).orElseGet(() -> 0);
            if (rank <= 7 && rank != 0) {
                placeRankGetResponseList.add(PlaceRankGetResponse.of(place, visitCount, rank));
            }
        }
        Collections.sort(
                placeRankGetResponseList, Comparator.comparing(PlaceRankGetResponse::visitCount).reversed());

        return placeRankGetResponseList;
    }

    public List<PlaceRankGetResponse> getSeasonRank(String year, String season) {
        List<PlaceRankGetResponse> placeRankGetResponseList = new ArrayList<>();
        List<Place> placeList = placeRepository.findAll();
        List<String> dates = getSeasonMonth(year, season);

        for (Place place : placeList) {
            Integer visitCount = recordPlaceRepository.getCountsBySeason(dates.get(0), dates.get(1), dates.get(2),
                    place.getPlaceId()).orElseGet(() -> 0);
            Integer rank = recordPlaceRepository.getRanksBySeason(dates.get(0), dates.get(1), dates.get(2),
                    place.getPlaceId()).orElseGet(() -> 0);
            if (rank <= 7 && rank != 0) {
                placeRankGetResponseList.add(PlaceRankGetResponse.of(place, visitCount, rank));
            }
        }
        Collections.sort(
                placeRankGetResponseList, Comparator.comparing(PlaceRankGetResponse::rank));

        return placeRankGetResponseList;
    }

    private List<String> getSeasonMonth (String year, String season){

        if (season.equals("봄")) {
            String startMonth = "03";
            String middleMonth = "04";
            String endMonth = "05";
            List<String> dateList = getDateFormat(year, startMonth, middleMonth, endMonth);

            return dateList;

        } else if (season.equals("여름")) {
            String startMonth = "06";
            String middleMonth = "07";
            String endMonth = "08";
            List<String> dateList = getDateFormat(year, startMonth, middleMonth, endMonth);

            return dateList;

        } else if (season.equals("가을")) {
            String startMonth = "09";
            String middleMonth = "10";
            String endMonth = "11";
            List<String> dateList = getDateFormat(year, startMonth, middleMonth, endMonth);

            return dateList;

        } else if (season.equals("겨울")) {
            String startMonth = "01";
            String middleMonth = "02";
            String endMonth = "12";

            List<String> dateList = getDateFormat(year, startMonth, middleMonth, endMonth);

            return dateList;
        }
        return null;
    }

    private List<String> getDateFormat (String year, String startMonth, String middleMonth, String endMonth){
        List<String> dateList = new ArrayList<>();

        String startDate = String.format("%s-%s", year, startMonth);
        String middleDate = String.format("%s-%s", year, middleMonth);
        String endDate = String.format("%s-%s", year, endMonth);

        dateList.add(startDate);
        dateList.add(middleDate);
        dateList.add(endDate);

        return dateList;
    }
}

