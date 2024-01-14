package com.triprecord.triprecord.schedule.entity;

import com.triprecord.triprecord.global.util.EntityBaseTime;
import com.triprecord.triprecord.user.entity.User;
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
public class ScheduleComment extends EntityBaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleCommentId;

    private String scheduleCommentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule commentedSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User commentedUser;

    @Builder
    public ScheduleComment(String content, Schedule schedule, User user) {
        this.scheduleCommentContent = content;
        this.commentedSchedule = schedule;
        this.commentedUser = user;
    }
}
