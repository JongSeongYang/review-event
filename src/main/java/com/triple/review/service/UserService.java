package com.triple.review.service;

import com.triple.review.domain.PointEntity;
import com.triple.review.domain.UserEntity;
import com.triple.review.dto.User;
import com.triple.review.exception.CustomResponseStatusException;
import com.triple.review.exception.ExceptionCode;
import com.triple.review.repository.UserRepository;
import com.triple.review.utils.UUIDFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PointService pointService;

    @Transactional
    public UUID createUser(User.UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent())
            throw new CustomResponseStatusException(ExceptionCode.EMAIL_DUPLICATED, "");
        UserEntity userEntity = null;
        try {
            PointEntity pointEntity = pointService.createPointEntity();
            userEntity = buildUserEntity(userRequest, pointEntity);
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new CustomResponseStatusException(ExceptionCode.USER_CREATE_FAIL, "");
        }
        return userEntity.getId();
    }

    private UserEntity buildUserEntity(User.UserRequest userRequest, PointEntity pointEntity) {
        return UserEntity.builder()
                .email(userRequest.getEmail())
                .pointEntity(pointEntity)
                .build();
    }

    public User.PointResponse getUserPoints(String userId) {
        UserEntity userEntity = userRepository.findById(UUIDFormatter.check(userId))
                .orElseThrow(() -> new CustomResponseStatusException(ExceptionCode.USER_NOT_FOUND, ""));
        return User.PointResponse.builder()
                .userId(userEntity.getId().toString())
                .points(userEntity.getPointEntity().getPoint()).build();
    }
}
