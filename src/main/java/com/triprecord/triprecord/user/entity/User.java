package com.triprecord.triprecord.user.entity;

import com.triprecord.triprecord.record.entity.RecordComment;
import com.triprecord.triprecord.record.entity.RecordLike;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.user.entity.TripStyle;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userEmail;

    private String userPassword;

    private String userNickname;

    private LocalDate userAge;

    private String userProfileImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_style_id")
    private TripStyle userTripStyle;

    @OneToMany(mappedBy = "createdUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "commentedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "likedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordLike> likes = new ArrayList<>();

    @Builder
    public User(String userEmail, String userPassword, String userNickname, LocalDate userAge, String userProfileImg,
                TripStyle tripStyle) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userAge = userAge;
        this.userProfileImg = userProfileImg;
        this.userTripStyle = tripStyle;
    }
}
