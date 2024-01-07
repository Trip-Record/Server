package com.triprecord.triprecord.user;

import com.triprecord.triprecord.comment.Comment;
import com.triprecord.triprecord.like.Like;
import com.triprecord.triprecord.record.Record;
import com.triprecord.triprecord.tripstyle.TripStyle;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userEmail;

    private String userPassword;

    private String userNickName;

    private LocalDate userAge;

    private String userProfileImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_style_id")
    private TripStyle userTripStyle;

    @OneToMany(mappedBy = "createdUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "commentedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "likedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Builder
    public User(String userEmail, String userPassword, String userNickName, LocalDate userAge, String userProfileImg,
                TripStyle tripStyles) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickName = userNickName;
        this.userAge = userAge;
        this.userProfileImg = userProfileImg;
        this.userTripStyle = tripStyles;
    }
}
