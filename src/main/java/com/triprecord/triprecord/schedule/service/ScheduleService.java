package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.PlaceRepository;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCreateRequest;
import com.triprecord.triprecord.schedule.dto.response.ScheduleGetResponse;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import com.triprecord.triprecord.schedule.repository.ScheduleDetailRepository;
import com.triprecord.triprecord.schedule.repository.SchedulePlaceRepository;
import com.triprecord.triprecord.schedule.repository.ScheduleRepository;
import com.triprecord.triprecord.user.entity.TripStyle;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleLikeService scheduleLikeService;
    private final ScheduleCommentService scheduleCommentService;
    private final ScheduleRepository scheduleRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final SchedulePlaceRepository schedulePlaceRepository;
    private final ScheduleDetailRepository scheduleDetailRepository;

    public Schedule getScheduleOrException(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new TripRecordException(ErrorCode.DUPLICATE_EMAIL));
    }

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

        places.stream()
                .forEach(place -> createSchedulePlace(schedule, place));

        if (request.scheduleDetails().isEmpty()) return;

        request.scheduleDetails().stream()
                .forEach(scheduleDetail -> createScheduleDetail(schedule, scheduleDetail.scheduleDetailDate(), scheduleDetail.scheduleDetailContent()));
    }

    public ScheduleGetResponse getSchedule(Long scheduleId) {
        Schedule schedule = getScheduleOrException(scheduleId);
        User createdUser = schedule.getCreatedUser();
        TripStyle createdUserTripStyle = schedule.getCreatedUser().getUserTripStyle();
        List<SchedulePlace> schedulePlaces = schedule.getSchedulePlaces();

        List<ScheduleDetail> scheduleDetails = schedule.getScheduleDetails();
        Collections.sort(scheduleDetails, Comparator.comparing(ScheduleDetail::getScheduleDetailDate));

        Long scheduleLikeCount = scheduleLikeService.getScheduleLikeCount(schedule);
        Long scheduleCommentCount = scheduleCommentService.getScheduleCommentCount(schedule);

        return ScheduleGetResponse.of(
                createdUser,
                createdUserTripStyle,
                schedule,
                schedulePlaces,
                scheduleDetails,
                scheduleLikeCount,
                scheduleCommentCount
        );
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

}
