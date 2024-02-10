package com.triprecord.triprecord.user.service;

import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.user.dto.response.TripStyleInfo;
import com.triprecord.triprecord.user.entity.TripStyle;
import com.triprecord.triprecord.user.repository.TripStyleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripStyleService {

    private final TripStyleRepository tripStyleRepository;

    public List<TripStyleInfo> getTripStyleInfo() {
        List<TripStyle> tripStyleList = tripStyleRepository.findAll();
        return tripStyleList.stream().map(TripStyleInfo::of).toList();
    }

    public TripStyle getTripStyle(Long tripStyleId) {
        return tripStyleRepository.findById(tripStyleId).orElseThrow(() ->
                new TripRecordException(ErrorCode.TRIP_STYLE_NOT_FOUND));
    }
}