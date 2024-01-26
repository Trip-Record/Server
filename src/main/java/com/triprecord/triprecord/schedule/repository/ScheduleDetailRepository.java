package com.triprecord.triprecord.schedule.repository;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Long> {

    @Query("SELECT sd.scheduleDetailDate FROM ScheduleDetail sd WHERE sd.linkedSchedule = :schedule")
    List<LocalDate> findScheduleDetailDatesBySchedule(Schedule schedule);

    ScheduleDetail findByScheduleDetailDateAndLinkedSchedule(LocalDate scheduleDetailDate, Schedule schedule);

    void deleteByScheduleDetailDateAndLinkedSchedule(LocalDate scheduleDetailDate, Schedule schedule);
}