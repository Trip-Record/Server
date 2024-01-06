package com.triprecord.triprecord.recordimage;


import com.triprecord.triprecord.record.Record;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RecordImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordImgId;

    private String recordImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record recordId;

    @Builder
    public RecordImage(String recordImg, Record recordId) {
        this.recordImg = recordImg;
        this.recordId = recordId;
    }
}
