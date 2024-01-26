package com.triprecord.triprecord.user.service;

import com.triprecord.triprecord.basicproflie.BasicProfileRepository;
import com.triprecord.triprecord.global.config.jwt.JwtProvider;
import com.triprecord.triprecord.global.config.jwt.UserAuthentication;
import com.triprecord.triprecord.global.exception.ErrorCode;
import com.triprecord.triprecord.global.exception.TripRecordException;
import com.triprecord.triprecord.user.controller.dto.request.UserCreateRequest;
import com.triprecord.triprecord.user.controller.dto.request.UserLoginRequest;
import com.triprecord.triprecord.user.controller.dto.response.UserInfoResponse;
import com.triprecord.triprecord.user.repository.TripStyleRepository;
import com.triprecord.triprecord.user.entity.User;
import com.triprecord.triprecord.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final BasicProfileRepository basicProfileRepository;
    private final PasswordEncoder passwordEncoder;

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

    public UserInfoResponse findUserData(Long userId){
        User user = getUserOrException(userId);
        Long recordTotal = getRecordsCount(user);
        Long scheduleTotal = getSchedulesCount(user);
        Long placeTotal = getPlacesCount(user);
        Long likeTotal = getLikesCount(user);

        return UserInfoResponse.of(user, recordTotal, scheduleTotal, placeTotal, likeTotal);
    }

    public Long getLikesCount(User user){
        return userRepository.recordLikes(user) + userRepository.scheduleLikes(user);
    }

    public Long getPlacesCount (User user){
        return userRepository.placeCount(user);
    }

    public Long getRecordsCount (User user){
        return userRepository.recordCount(user);
    }

    public Long getSchedulesCount(User user){
        return userRepository.scheduleCount(user);
    }

    public User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new TripRecordException(ErrorCode.USER_NOT_FOUND));
    }

}