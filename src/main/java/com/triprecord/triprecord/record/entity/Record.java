package com.triprecord.triprecord.record.entity;


import com.triprecord.triprecord.record.dto.RecordUpdateData;
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
public class Record {

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


    @OneToMany(mappedBy = "linkedRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordPlace> recordPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "likedRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "linkedRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecordImage> recordImages = new ArrayList<>();

    @Builder
    public Record(String recordTitle, String recordContent, LocalDate tripStartDate, LocalDate tripEndDate, User createdUser) {
        this.recordTitle = recordTitle;
        this.recordContent = recordContent;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.createdUser = createdUser;
    }

    public void updateRecord(RecordUpdateData updateData){
        this.recordTitle = updateData.recordTitle();
        this.recordContent = updateData.recordContent();
        this.tripStartDate = updateData.startDate();
        this.tripEndDate = updateData.endDate();
    }

}
