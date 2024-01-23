package com.triprecord.triprecord.schedule.entity;

import com.triprecord.triprecord.schedule.dto.request.ScheduleDetailUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleDetailId;

    private LocalDate scheduleDetailDate;

    private String scheduleContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule linkedSchedule;


    @Builder
    public ScheduleDetail(LocalDate scheduleDetailDate, String content, Schedule schedule) {
        this.scheduleDetailDate = scheduleDetailDate;
        this.scheduleContent = content;
        this.linkedSchedule = schedule;
    }

    public void updateScheduleDetail(ScheduleDetailUpdateRequest updateRequest) {
        if (!updateRequest.scheduleDetailContent().isBlank()) {
            this.scheduleContent = updateRequest.scheduleDetailContent();
        }
    }
}
