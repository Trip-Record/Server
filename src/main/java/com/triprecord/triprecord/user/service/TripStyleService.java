package com.triprecord.triprecord.user.service;

import com.triprecord.triprecord.user.dto.response.TripStyleInfo;
import com.triprecord.triprecord.user.entity.TripStyle;
import com.triprecord.triprecord.user.repository.TripStyleRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripStyleService {

    private final TripStyleRepository tripStyleRepository;

    public List<TripStyleInfo> getTripStyleInfo(){
        List<TripStyle> tripStyleList = tripStyleRepository.findAll();
        List<TripStyleInfo> tripStyleInfoList = new ArrayList<>();

        for(TripStyle tripStyle : tripStyleList){
            tripStyleInfoList.add(TripStyleInfo.of(tripStyle));
        }

        return tripStyleInfoList;
    }
}
