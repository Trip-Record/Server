package com.triprecord.triprecord.schedule.repository;

import com.triprecord.triprecord.location.entity.Place;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulePlaceRepository extends JpaRepository<SchedulePlace, Long> {

    @Query("SELECT sp.place.placeId FROM SchedulePlace sp WHERE sp.linkedSchedule = :schedule")
    List<Long> findPlaceIdsBySchedule(Schedule schedule);

    void deleteByLinkedScheduleAndPlace(Schedule schedule, Place place);

}
