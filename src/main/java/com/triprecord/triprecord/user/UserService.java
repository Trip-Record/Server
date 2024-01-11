package com.triprecord.triprecord.user;

import com.triprecord.triprecord.basicproflie.BasicProfileRepository;
import com.triprecord.triprecord.global.config.jwt.JwtProvider;
import com.triprecord.triprecord.global.config.jwt.UserAuthentication;
import com.triprecord.triprecord.tripstyle.TripStyleRepository;
import jakarta.security.auth.message.AuthException;
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
    private final TripStyleRepository tripStyleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signup(UserCreateRequest userCreateRequest) {
        Optional<User> user = userRepository.findByUserEmail(userCreateRequest.userEmail());

        if (!user.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        User newUser = User.builder()
                .userEmail(userCreateRequest.userEmail())
                .userPassword(passwordEncoder.encode(userCreateRequest.userPassword()))
                .userAge(userCreateRequest.userAge())
                .userProfileImg(basicProfileRepository.findById(userCreateRequest.userBasicProfileId()).get().getBasicProfileImg())
                .userNickname(userCreateRequest.userNickname())
                .tripStyle(tripStyleRepository.findById(userCreateRequest.userTripStyleId()).get())
                .build();

        userRepository.save(newUser);

        return newUser.getUserId().toString();
    }

    public String login(UserLoginRequest userLoginRequest) throws AuthException {
        User user = userRepository.findByUserEmail(userLoginRequest.userEmail())
                .orElseThrow(() -> new AuthException("해당하는 사용자가 없습니다."));

        if (!passwordEncoder.matches(userLoginRequest.userPassword(), user.getUserPassword()))
            throw new AuthException("비밀번호가 일치하지 않습니다.");

        UserAuthentication userAuthentication = new UserAuthentication(user.getUserId(), null, null);

        return jwtProvider.generateToken(userAuthentication);
    }
}