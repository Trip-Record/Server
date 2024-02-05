package com.triprecord.triprecord.schedule.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.schedule.entity.ScheduleLike;
import com.triprecord.triprecord.schedule.repository.ScheduleLikeRepository;
import com.triprecord.triprecord.user.entity.User;
import java.util.Optional;
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

    @Transactional
    public void createScheduleLike(User user, Schedule schedule) {
        Optional<ScheduleLike> scheduleLike = scheduleLikeRepository.findByLikedUserAndLikedSchedule(user, schedule);
        if (scheduleLike.isPresent()) {
            throw new TripRecordException(ErrorCode.SCHEDULE_ALREADY_LIKED);
        }

        ScheduleLike newScheduleLike = ScheduleLike.builder()
                .schedule(schedule)
                .user(user)
                .build();
        scheduleLikeRepository.save(newScheduleLike);
    }
}
