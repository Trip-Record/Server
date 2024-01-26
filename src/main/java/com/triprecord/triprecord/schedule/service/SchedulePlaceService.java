package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.location.PlaceService;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import com.triprecord.triprecord.schedule.repository.SchedulePlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchedulePlaceService {

    private final PlaceService placeService;
    private final SchedulePlaceRepository schedulePlaceRepository;

    @Transactional
    public void createSchedulePlace(Schedule schedule, Place place) {
        SchedulePlace schedulePlace = SchedulePlace.builder()
                .schedule(schedule)
                .place(place)
                .build();
        schedulePlaceRepository.save(schedulePlace);
    }

    @Transactional
    public void updateSchedulePlace(Schedule schedule, ScheduleUpdateRequest ScheduleRequest) {
        List<Place> newPlaces = new ArrayList<>();

        List<Long> registeredPlaceIds = schedulePlaceRepository.findPlaceIdsBySchedule(schedule);

        for (Long newPlaceId : ScheduleRequest.placeIds()) {
            if (registeredPlaceIds.contains(newPlaceId)) {
                registeredPlaceIds.remove(newPlaceId);
                continue;
            }
            newPlaces.add(placeService.getPlaceOrException(newPlaceId));
        }

        for (Long placeId : registeredPlaceIds) {
            schedulePlaceRepository.deleteByLinkedScheduleAndPlace(schedule, placeService.getPlaceOrException(placeId));
        }

        newPlaces.stream()
                .forEach(place -> createSchedulePlace(schedule, place));
    }
}
