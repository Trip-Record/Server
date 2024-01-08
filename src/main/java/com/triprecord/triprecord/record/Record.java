package com.triprecord.triprecord.record;


import com.triprecord.triprecord.global.util.EntityBaseTime;
import com.triprecord.triprecord.like.Like;
import com.triprecord.triprecord.place.Place;
import com.triprecord.triprecord.recordimage.RecordImage;
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
import java.sql.Date;
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
public class Record extends EntityBaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private String recordTitle;

    private String recordContent;

    private LocalDate tripStartDate;

    private LocalDate tripEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place recordPlace;

    @OneToMany(mappedBy = "likedRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "linkedRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordImage> recordImages = new ArrayList<>();

    @Builder
    public Record(String recordTitle, String recordContent, LocalDate tripStartDate, LocalDate tripEndDate, User user,
                  Place place) {
        this.recordTitle = recordTitle;
        this.recordContent = recordContent;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.createdUser = user;
        this.recordPlace = place;
    }

}
