package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.PlaceService;
import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.schedule.dto.request.ScheduleCreateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
import com.triprecord.triprecord.schedule.dto.response.ScheduleGetResponse;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import com.triprecord.triprecord.schedule.repository.ScheduleRepository;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserService userService;
    private final PlaceService placeService;
    private final SchedulePlaceService schedulePlaceService;
    private final ScheduleDetailService scheduleDetailService;
    private final ScheduleRepository scheduleRepository;

    public Schedule getScheduleOrException(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new TripRecordException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    @Transactional
    public void createSchedule(Long userId, ScheduleCreateRequest request) {
        User user = userService.getUserOrException(userId);

        List<Place> places = new ArrayList<>();
        for (Long placeId : request.placeIds()) {
            places.add(placeService.getPlaceOrException(placeId));
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
                .forEach(place -> schedulePlaceService.createSchedulePlace(schedule, place));

        if (request.scheduleDetails().isEmpty()) return;

        request.scheduleDetails().stream()
                .forEach(scheduleDetail -> scheduleDetailService.createScheduleDetail(schedule, scheduleDetail.scheduleDetailDate(), scheduleDetail.scheduleDetailContent()));
    }

    public ScheduleGetResponse getSchedule(Long scheduleId) {
        Schedule schedule = getScheduleOrException(scheduleId);
        User createdUser = schedule.getCreatedUser();
        List<SchedulePlace> schedulePlaces = schedule.getSchedulePlaces();

        List<ScheduleDetail> scheduleDetails = schedule.getScheduleDetails();
        Collections.sort(scheduleDetails, Comparator.comparing(ScheduleDetail::getScheduleDetailDate));

        Long scheduleLikeCount = scheduleLikeService.getScheduleLikeCount(schedule);
        Long scheduleCommentCount = scheduleCommentService.getScheduleCommentCount(schedule);

        return ScheduleGetResponse.of(
                createdUser,
                schedule,
                schedulePlaces,
                scheduleDetails,
                scheduleLikeCount,
                scheduleCommentCount
        );
    }

    @Transactional
    public void updateSchedule(Long userId, Long scheduleId, ScheduleUpdateRequest ScheduleRequest) {
        User user = userService.getUserOrException(userId);
        Schedule schedule = getScheduleOrException(scheduleId);
        checkSameUser(schedule.getCreatedUser(), user);

        schedule.updateSchedule(ScheduleRequest);

        updateSchedulePlace(schedule, ScheduleRequest);

        updateScheduleDetail(schedule, ScheduleRequest);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId) {
        User user = userService.getUserOrException(userId);
        Schedule schedule = getScheduleOrException(scheduleId);
        checkSameUser(schedule.getCreatedUser(), user);

        scheduleRepository.delete(schedule);
    }

    private void updateSchedulePlace(Schedule schedule, ScheduleUpdateRequest ScheduleRequest) {
        if (ScheduleRequest.placeIds() == null || ScheduleRequest.placeIds().isEmpty()) return;

        schedulePlaceService.updateSchedulePlace(schedule, ScheduleRequest);
    }

    private void updateScheduleDetail(Schedule schedule, ScheduleUpdateRequest ScheduleRequest) {
        if (ScheduleRequest.scheduleDetails() == null || ScheduleRequest.scheduleDetails().isEmpty()) return;

        scheduleDetailService.updateScheduleDetail(schedule, ScheduleRequest);
    }

    private void checkSameUser(User createdUser, User user) {
        if (createdUser != user) {
            throw new TripRecordException(ErrorCode.INVALID_PERMISSION);
        }
    }

}
