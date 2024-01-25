package com.triprecord.triprecord.schedule.repository;

import com.triprecord.triprecord.schedule.entity.SchedulePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePlaceRepository extends JpaRepository<SchedulePlace, Long> {
}
