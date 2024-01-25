package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.schedule.dto.request.ScheduleDetailUpdateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import com.triprecord.triprecord.schedule.repository.ScheduleDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleDetailService {

    private final ScheduleDetailRepository scheduleDetailRepository;

    @Transactional
    public void createScheduleDetail(Schedule schedule, LocalDate scheduleDetailDate, String scheduleDetailContent) {
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

    @Transactional
    public void updateScheduleDetail(Schedule schedule, ScheduleUpdateRequest ScheduleRequest) {
        if (ScheduleRequest.scheduleDetails() == null || ScheduleRequest.scheduleDetails().isEmpty()) return;

        List<LocalDate> registeredScheduleDetailDates = scheduleDetailRepository.findScheduleDetailDatesBySchedule(schedule);

        if (registeredScheduleDetailDates.isEmpty()) { // 기존 저장된 세부 일정이 없는 경우
            for (ScheduleDetailUpdateRequest scheduleDetailUpdateRequest : ScheduleRequest.scheduleDetails()) {
                createScheduleDetail(schedule, scheduleDetailUpdateRequest.scheduleDetailDate(), scheduleDetailUpdateRequest.scheduleDetailContent());
            }
        } else {
            // 수정된 일정 기간 내에 포함되지 않는 세부 일정 삭제
            registeredScheduleDetailDates.stream()
                    .filter(date -> isNotBetweenInclusive(date, ScheduleRequest.scheduleStartDate(), ScheduleRequest.scheduleEndDate()))
                    .forEach(date -> scheduleDetailRepository.deleteByScheduleDetailDateAndLinkedSchedule(date, schedule));

            for (ScheduleDetailUpdateRequest scheduleDetailRequest : ScheduleRequest.scheduleDetails()) {
                if (registeredScheduleDetailDates.contains(scheduleDetailRequest.scheduleDetailDate())) { // 해당 일자 세부 일정이 이미 존재하는 경우 수정
                    ScheduleDetail scheduleDetail = scheduleDetailRepository.findByScheduleDetailDateAndLinkedSchedule(scheduleDetailRequest.scheduleDetailDate(), schedule);
                    scheduleDetail.updateScheduleDetail(scheduleDetailRequest);
                } else { // 해당 일자 세부 일정이 존재하지 않는 경우 새로 생성
                    createScheduleDetail(schedule, scheduleDetailRequest.scheduleDetailDate(), scheduleDetailRequest.scheduleDetailContent());
                }
            }
        }
    }

    private boolean isNotBetweenInclusive(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !(dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate))
                || !(dateToCheck.isEqual(endDate) || dateToCheck.isBefore(endDate));
    }

}
