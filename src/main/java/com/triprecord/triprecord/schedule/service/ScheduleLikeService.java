package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.repository.ScheduleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleLikeService {

    private final ScheduleLikeRepository scheduleLikeRepository;

    public Long getScheduleLikeCount(Schedule schedule) {
        return scheduleLikeRepository.countByLikedSchedule(schedule);
    }
}
