package com.triprecord.triprecord.record.entity;


import com.triprecord.triprecord.location.entity.Place;
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
public class RecordPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordPlaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record linkedRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place recordPlace;

    @Builder
    public RecordPlace(Record linkedRecord, Place recordPlace) {
        this.linkedRecord = linkedRecord;
        this.recordPlace = recordPlace;
    }
}
