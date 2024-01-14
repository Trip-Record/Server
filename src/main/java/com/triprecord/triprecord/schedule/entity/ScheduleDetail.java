package com.triprecord.triprecord.schedule.entity;

import com.triprecord.triprecord.schedule.entity.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    public ScheduleDetail(LocalDate localDate, String content, Schedule schedule) {
        this.scheduleDetailDate = localDate;
        this.scheduleContent = content;
        this.linkedSchedule = schedule;
    }
}
