package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.schedule.dto.request.ScheduleDetailUpdateRequest;
import com.triprecord.triprecord.schedule.dto.request.ScheduleUpdateRequest;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import com.triprecord.triprecord.schedule.repository.ScheduleDetailRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updateScheduleDetail(Schedule schedule, ScheduleUpdateRequest scheduleRequest) {
        if (scheduleRequest.scheduleDetails() == null || scheduleRequest.scheduleDetails().isEmpty()) {
            return;
        }

        List<LocalDate> registeredScheduleDetailDates = scheduleDetailRepository.findScheduleDetailDatesBySchedule(
                schedule);

        if (registeredScheduleDetailDates.isEmpty()) { // 기존 저장된 세부 일정이 없는 경우
            scheduleRequest.scheduleDetails().stream()
                    .forEach(scheduleDetailRequest -> createScheduleDetail(schedule,
                            scheduleDetailRequest.scheduleDetailDate(), scheduleDetailRequest.scheduleDetailContent()));
        } else {
            // 수정된 일정 기간 내에 포함되지 않는 기존 세부 일정 삭제
            deleteNonIncludedScheduleDetails(schedule, scheduleRequest, registeredScheduleDetailDates);

            for (ScheduleDetailUpdateRequest scheduleDetailRequest : scheduleRequest.scheduleDetails()) {
                if (registeredScheduleDetailDates.contains(
                        scheduleDetailRequest.scheduleDetailDate())) { // 해당 일자 세부 일정이 이미 존재하는 경우 수정
                    ScheduleDetail scheduleDetail = scheduleDetailRepository.findByScheduleDetailDateAndLinkedSchedule(
                            scheduleDetailRequest.scheduleDetailDate(), schedule);
                    if (scheduleDetailRequest.scheduleDetailContent().isBlank()) { // 세부 일정 내용이 빈 문자열일 경우 삭제
                        scheduleDetailRepository.delete(scheduleDetail);
                    } else {
                        scheduleDetail.updateScheduleDetail(scheduleDetailRequest);
                    }
                } else { // 해당 일자 세부 일정이 존재하지 않는 경우 새로 생성
                    createScheduleDetail(schedule, scheduleDetailRequest.scheduleDetailDate(),
                            scheduleDetailRequest.scheduleDetailContent());
                }
            }
        }
    }

    private boolean isNotBetweenInclusive(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !(dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate))
                || !(dateToCheck.isEqual(endDate) || dateToCheck.isBefore(endDate));
    }

    private void deleteNonIncludedScheduleDetails(Schedule schedule, ScheduleUpdateRequest scheduleRequest,
                                                  List<LocalDate> registeredScheduleDetailDates) {
        registeredScheduleDetailDates.stream()
                .filter(date -> isNotBetweenInclusive(
                        date,
                        scheduleRequest.scheduleStartDate() == null ? schedule.getScheduleStartDate()
                                : scheduleRequest.scheduleStartDate(),
                        scheduleRequest.scheduleStartDate() == null ? schedule.getScheduleEndDate()
                                : scheduleRequest.scheduleEndDate()
                ))
                .forEach(date -> scheduleDetailRepository.deleteByScheduleDetailDateAndLinkedSchedule(date, schedule));
    }

}
