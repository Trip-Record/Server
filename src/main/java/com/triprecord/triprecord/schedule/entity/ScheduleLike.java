package com.triprecord.triprecord.schedule.entity;


import com.triprecord.triprecord.schedule.entity.Schedule;
import com.triprecord.triprecord.user.User;
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
public class ScheduleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule likedSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User likedUser;


    @Builder
    public ScheduleLike(Schedule schedule, User user) {
        this.likedSchedule = schedule;
        this.likedUser = user;
    }
}
