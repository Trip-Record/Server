package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.PlaceRepository;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCreateRequest;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import com.triprecord.triprecord.schedule.repository.ScheduleDetailRepository;
import com.triprecord.triprecord.schedule.repository.SchedulePlaceRepository;
import com.triprecord.triprecord.schedule.repository.ScheduleRepository;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final SchedulePlaceRepository schedulePlaceRepository;
    private final ScheduleDetailRepository scheduleDetailRepository;

    @Transactional
    public void createSchedule(Long userId, ScheduleCreateRequest request) {
        User user = getUserOrException(userId);

        List<Place> places = new ArrayList<>();
        for (Long placeId : request.schedulePlaceIds()) {
            places.add(getPlaceOrException(placeId));
        }

        if (request.scheduleStartDate().isAfter(request.scheduleEndDate())) {
            throw new TripRecordException(ErrorCode.SCHEDULE_DATE_INVALID);
        }

        Schedule schedule = Schedule.builder()
                .scheduleTitle(request.scheduleTitle())
                .startDate(request.scheduleStartDate())
                .endDate(request.scheduleEndDate())
                .user(user)
                .build();
        scheduleRepository.save(schedule);

        for (Place place : places) {
            SchedulePlace schedulePlace = SchedulePlace.builder()
                    .schedule(schedule)
                    .place(place)
                    .build();
            schedulePlaceRepository.save(schedulePlace);
        }

        if (request.scheduleDetails().isEmpty()) return;

        request.scheduleDetails().stream()
                .filter(scheduleDetail -> isNotBetweenInclusive(scheduleDetail.scheduleDetailDate(), request.scheduleStartDate(), request.scheduleEndDate()))
                .findAny()
                .ifPresent(scheduleDetail -> {
                    throw new TripRecordException(ErrorCode.SCHEDULE_DETAIL_DATE_INVALID);
                });

        request.scheduleDetails().stream()
                .forEach(scheduleDetail -> createScheduleDetail(schedule, scheduleDetail.scheduleDetailDate(), scheduleDetail.scheduleDetailContent()));
    }

    private User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new TripRecordException(ErrorCode.USER_NOT_FOUND));
    }

    private Place getPlaceOrException(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(() ->
                new TripRecordException(ErrorCode.PLACE_NOT_FOUNT));
    }

    private static boolean isNotBetweenInclusive(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !(dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate))
                || !(dateToCheck.isEqual(endDate) || dateToCheck.isBefore(endDate));
    }

    private void createScheduleDetail(Schedule schedule, LocalDate scheduleDetailDate, String scheduleDetailContent) {
        ScheduleDetail scheduleDetail = ScheduleDetail.builder()
                .schedule(schedule)
                .scheduleDetailDate(scheduleDetailDate)
                .content(scheduleDetailContent)
                .build();
        scheduleDetailRepository.save(scheduleDetail);
    }

}
