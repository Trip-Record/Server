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
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Record extends EntityBaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private String recordTitle;

    private String recordContent;

    private Date tripStartDate;

    private Date tripEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place placeId;

    @Builder
    public Record(String recordTitle, String recordContent, Date tripStartDate, Date tripEndDate, User userId,
                  Place placeId) {
        this.recordTitle = recordTitle;
        this.recordContent = recordContent;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.userId = userId;
        this.placeId = placeId;
    }

    @OneToMany(mappedBy = "likedRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "recordId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordImage> recordImages = new ArrayList<>();

}
