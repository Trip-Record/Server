package com.triprecord.triprecord.scheduleplace;

import com.triprecord.triprecord.place.Place;
import com.triprecord.triprecord.schedule.Schedule;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchedulePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulePlaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule linkedSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place schedulePlace;


    @Builder
    public SchedulePlace(Schedule schedule, Place place) {
        this.linkedSchedule = schedule;
        this.schedulePlace = place;
    }
}
