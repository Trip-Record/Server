package com.triprecord.triprecord.schedule.entity;


import com.triprecord.triprecord.global.util.EntityBaseTime;
import com.triprecord.triprecord.user.entity.User;
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
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private String scheduleTitle;

    private LocalDate scheduleStartDate;

    private LocalDate scheduleEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdUser;

    @OneToMany(mappedBy = "linkedSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SchedulePlace> schedulePlaces = new ArrayList<>();

    @OneToMany(mappedBy = "linkedSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleDetail> scheduleDetails = new ArrayList<>();

    @OneToMany(mappedBy = "likedSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "commentedSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleComment> comments = new ArrayList<>();

    @Builder
    public Schedule(String scheduleTitle, LocalDate startDate, LocalDate endDate, User user) {
        this.scheduleTitle = scheduleTitle;
        this.scheduleStartDate = startDate;
        this.scheduleEndDate = endDate;
        this.createdUser = user;
    }

}
