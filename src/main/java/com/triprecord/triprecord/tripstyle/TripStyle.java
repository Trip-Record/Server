package com.triprecord.triprecord.tripstyle;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripStyleId;

    private String tripStyleName;

    private String tripStyleImg;

    private String tripStyleDescription;
}
