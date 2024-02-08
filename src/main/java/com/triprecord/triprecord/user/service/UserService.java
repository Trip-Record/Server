package com.triprecord.triprecord.user.service;

import com.triprecord.triprecord.basicproflie.BasicProfileRepository;
import com.triprecord.triprecord.global.config.jwt.JwtProvider;
import com.triprecord.triprecord.global.config.jwt.UserAuthentication;
import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.location.dto.PlaceBasicData;
import com.triprecord.triprecord.record.controller.response.RecordPageResponse;
import com.triprecord.triprecord.record.controller.response.RecordResponse;
import com.triprecord.triprecord.record.dto.RecordImageData;
import com.triprecord.triprecord.record.entity.Record;
import com.triprecord.triprecord.record.repository.RecordLikeRepository;
import com.triprecord.triprecord.record.repository.RecordPlaceRepository;
import com.triprecord.triprecord.record.repository.RecordRepository;
import com.triprecord.triprecord.record.service.RecordCommentService;
import com.triprecord.triprecord.record.service.RecordImageService;
import com.triprecord.triprecord.record.service.RecordLikeService;
import com.triprecord.triprecord.record.service.RecordPlaceService;
import com.triprecord.triprecord.schedule.repository.ScheduleLikeRepository;
import com.triprecord.triprecord.schedule.repository.ScheduleRepository;
import com.triprecord.triprecord.user.dto.request.UserCreateRequest;
import com.triprecord.triprecord.user.dto.request.UserLoginRequest;
import com.triprecord.triprecord.user.dto.response.UserInfoGetResponse;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.repository.UserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final RecordPlaceRepository recordPlaceRepository;
    private final RecordLikeRepository recordLikeRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleLikeRepository scheduleLikeRepository;
    private final BasicProfileRepository basicProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecordLikeService recordLikeService;
    private final RecordCommentService recordCommentService;
    private final RecordPlaceService recordPlaceService;
    private final RecordImageService recordImageService;

    @Transactional
    public String signup(UserCreateRequest userCreateRequest) {
        Optional<User> user = userRepository.findByUserEmail(userCreateRequest.userEmail());

        if (user.isPresent()) {
            throw new TripRecordException(ErrorCode.DUPLICATE_EMAIL);
        }

        User newUser = User.builder()
                .userEmail(userCreateRequest.userEmail())
                .userPassword(passwordEncoder.encode(userCreateRequest.userPassword()))
                .userAge(userCreateRequest.userAge())
                .userProfileImg(basicProfileRepository.findById(userCreateRequest.userBasicProfileId()).get().getBasicProfileImg())
                .userNickname(userCreateRequest.userNickname())
                .build();

        userRepository.save(newUser);

        return newUser.getUserId().toString();
    }

    public String login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByUserEmail(userLoginRequest.userEmail())
                .orElseThrow(() -> new TripRecordException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(userLoginRequest.userPassword(), user.getUserPassword()))
            throw new TripRecordException(ErrorCode.INVALID_PASSWORD);
        UserAuthentication userAuthentication = new UserAuthentication(user.getUserId(), null, null);

        return jwtProvider.generateToken(userAuthentication);
    }

    public UserInfoGetResponse getUserInfo(Long userId){
        User user = getUserOrException(userId);
        Long recordTotal = getRecordsCount(user);
        Long scheduleTotal = getSchedulesCount(user);
        Long placeTotal = getPlacesCount(user);
        Long likeTotal = getLikesCount(user);

        return UserInfoGetResponse.of(user, recordTotal, scheduleTotal, placeTotal, likeTotal);
    }

    public RecordPageResponse getUserRecords(Long userId, Pageable pageable){
        Page<Record> records = recordRepository.findAllByCreatedUser(userId, pageable);
        List<RecordResponse> userRecordGetResponse = new ArrayList<>();

        for(Record record : records.getContent()){
            List<PlaceBasicData> userRecordPlaceData = recordPlaceService.getRecordPlaceBasicData(record);
            List<RecordImageData> userRecordImageData = recordImageService.findRecordImageData(record);

            Long recordLikeCount = recordLikeService.getRecordLikeCount(record);
            Long recordCommentCount = recordCommentService.getRecordCommentCount(record);

            userRecordGetResponse.add(RecordResponse.fromRecordData(record, userRecordPlaceData, userRecordImageData, recordLikeCount, recordCommentCount));
        }

        return RecordPageResponse.builder()
                .totalPages(records.getTotalPages())
                .pageNumber(records.getNumber() + 1)
                .recordList(userRecordGetResponse)
                .build();
    }

    public Long getLikesCount(User user){
        return recordLikeRepository.countRecordLikeByLikedUser(user) + scheduleLikeRepository.countScheduleLikeByLikedUser(user);
    }

    public Long getPlacesCount (User user){
        return recordPlaceRepository.placeCount(user);
    }

    public Long getRecordsCount (User user){
        return recordRepository.countRecordByCreatedUser(user);
    }

    public Long getSchedulesCount(User user){
        return scheduleRepository.countScheduleByCreatedUser(user);
    }

    public User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new TripRecordException(ErrorCode.USER_NOT_FOUND));
    }

}