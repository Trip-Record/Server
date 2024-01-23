package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.PlaceRepository;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCreateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleDetailUpdateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
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
import java.util.stream.Collectors;

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
        for (Long placeId : request.placeIds()) {
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

        places.stream()
                .forEach(place -> createSchedulePlace(schedule, place));

        if (request.scheduleDetails().isEmpty()) return;

        request.scheduleDetails().stream()
                .forEach(scheduleDetail -> createScheduleDetail(schedule, scheduleDetail.scheduleDetailDate(), scheduleDetail.scheduleDetailContent()));
    }

    @Transactional
    public void updateSchedule(Long userId, Long scheduleId, ScheduleUpdateRequest ScheduleRequest) {
        User user = getUserOrException(userId);
        Schedule schedule = getScheduleOrException(scheduleId);
        if (schedule.getCreatedUser() != user) {
            throw new TripRecordException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        schedule.updateSchedule(ScheduleRequest);

        updateSchedulePlace(schedule, ScheduleRequest);

        updateScheduleDetail(schedule, ScheduleRequest);
    }

    private User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new TripRecordException(ErrorCode.USER_NOT_FOUND));
    }

    private Place getPlaceOrException(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(() ->
                new TripRecordException(ErrorCode.PLACE_NOT_FOUND));
    }

    private Schedule getScheduleOrException(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new TripRecordException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    private boolean isNotBetweenInclusive(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !(dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate))
                || !(dateToCheck.isEqual(endDate) || dateToCheck.isBefore(endDate));
    }

    private void createSchedulePlace(Schedule schedule, Place place) {
        SchedulePlace schedulePlace = SchedulePlace.builder()
                .schedule(schedule)
                .place(place)
                .build();
        schedulePlaceRepository.save(schedulePlace);
    }

    private void createScheduleDetail(Schedule schedule, LocalDate scheduleDetailDate, String scheduleDetailContent) {
        if (isNotBetweenInclusive(scheduleDetailDate, schedule.getScheduleStartDate(), schedule.getScheduleEndDate())) {
            throw new TripRecordException(ErrorCode.SCHEDULE_DETAIL_DATE_INVALID);
        }

        ScheduleDetail scheduleDetail = ScheduleDetail.builder()
                .schedule(schedule)
                .scheduleDetailDate(scheduleDetailDate)
                .content(scheduleDetailContent)
                .build();
        scheduleDetailRepository.save(scheduleDetail);
    }

    private void updateSchedulePlace(Schedule schedule, ScheduleUpdateRequest ScheduleRequest) {
        if (ScheduleRequest.placeIds() == null || ScheduleRequest.placeIds().isEmpty()) return;

        List<Place> newPlaces = new ArrayList<>();

        List<Long> registeredPlaceIds = schedulePlaceRepository.findPlaceIdsBySchedule(schedule);

        for (Long newPlaceId : ScheduleRequest.placeIds()) {
            if (registeredPlaceIds.contains(newPlaceId)) {
                registeredPlaceIds.remove(newPlaceId);
                continue;
            }
            newPlaces.add(getPlaceOrException(newPlaceId));
        }

        for (Long placeId : registeredPlaceIds) {
            schedulePlaceRepository.deleteByLinkedScheduleAndPlace(schedule, getPlaceOrException(placeId));
        }

        List<SchedulePlace> schedulePlaces = newPlaces.stream()
                .map(place -> SchedulePlace.builder()
                        .schedule(schedule)
                        .place(place)
                        .build())
                .collect(Collectors.toList());

        schedulePlaceRepository.saveAll(schedulePlaces);
    }

    private void updateScheduleDetail(Schedule schedule, ScheduleUpdateRequest ScheduleRequest) {
        if (ScheduleRequest.scheduleDetails() == null || ScheduleRequest.scheduleDetails().isEmpty()) return;

        List<LocalDate> registeredScheduleDetailDates = scheduleDetailRepository.findScheduleDetailDatesBySchedule(schedule);

        if (registeredScheduleDetailDates.isEmpty()) {
            for (ScheduleDetailUpdateRequest scheduleDetailUpdateRequest : ScheduleRequest.scheduleDetails()) {
                createScheduleDetail(schedule, scheduleDetailUpdateRequest.scheduleDetailDate(), scheduleDetailUpdateRequest.scheduleDetailContent());
            }
        } else {
            // 수정된 일정 기간 내에 포함되지 않는 세부 일정 삭제
            registeredScheduleDetailDates.stream()
                    .filter(date -> isNotBetweenInclusive(date, ScheduleRequest.scheduleStartDate(), ScheduleRequest.scheduleEndDate()))
                    .forEach(date -> scheduleDetailRepository.deleteByScheduleDetailDateAndLinkedSchedule(date, schedule));

            for (ScheduleDetailUpdateRequest scheduleDetailRequest : ScheduleRequest.scheduleDetails()) {
                if (registeredScheduleDetailDates.contains(scheduleDetailRequest.scheduleDetailDate())) {
                    ScheduleDetail scheduleDetail = scheduleDetailRepository.findByScheduleDetailDateAndLinkedSchedule(scheduleDetailRequest.scheduleDetailDate(), schedule);
                    scheduleDetail.updateScheduleDetail(scheduleDetailRequest);
                } else {
                    createScheduleDetail(schedule, scheduleDetailRequest.scheduleDetailDate(), scheduleDetailRequest.scheduleDetailContent());
                }
            }
        }
    }

}
