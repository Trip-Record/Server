package com.triprecord.triprecord.record.entity;


import com.triprecord.triprecord.record.entity.Record;
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
public class RecordImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordImgId;

    private String recordImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record linkedRecord;

    @Builder
    public RecordImage(String imageURL, Record linkedRecord) {
        this.recordImgUrl = imageURL;
        this.linkedRecord = linkedRecord;
    }
}
