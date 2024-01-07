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

    private String userName;

    private LocalDate userAge;

    private String userProfileImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_style_id")
    private TripStyle userTripStyle;

    @Builder
    public User(String userEmail, String userPassword, String userName, LocalDate userAge, String userProfileImg,
                TripStyle styleId) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userAge = userAge;
        this.userProfileImg = userProfileImg;
        this.userTripStyle = styleId;
    }
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();
    @OneToMany(mappedBy = "commentedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "likedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();
}
