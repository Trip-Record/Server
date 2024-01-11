package com.triprecord.triprecord.schedule;


import com.triprecord.triprecord.global.util.EntityBaseTime;
import com.triprecord.triprecord.schedulecomment.ScheduleComment;
import com.triprecord.triprecord.scheduledetail.ScheduleDetail;
import com.triprecord.triprecord.schedulelike.ScheduleLike;
import com.triprecord.triprecord.scheduleplace.SchedulePlace;
import com.triprecord.triprecord.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends EntityBaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private String scheduleTitle;

    private LocalDate scheduleStartDate;

    private LocalDate scheduleEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdUser;


    @Builder
    public Schedule(String scheduleTitle, LocalDate startDate, LocalDate endDate, User user) {
        this.scheduleTitle = scheduleTitle;
        this.scheduleStartDate = startDate;
        this.scheduleEndDate = endDate;
        this.createdUser = user;
    }

}
