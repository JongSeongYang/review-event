package com.triple.review.service;

import com.triple.review.domain.PointEntity;
import com.triple.review.domain.PointHistoryEntity;
import com.triple.review.dto.Common;
import com.triple.review.dto.Event;
import com.triple.review.exception.CustomResponseStatusException;
import com.triple.review.exception.ExceptionCode;
import com.triple.review.repository.PointHistoryRepository;
import com.triple.review.repository.PointRepository;
import com.triple.review.utils.ActionType;
import com.triple.review.utils.UUIDFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {

    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public PointEntity createPointEntity() {
        PointEntity pointEntity = PointEntity.builder()
                .point(0)
                .build();
        pointRepository.save(pointEntity);
        return pointEntity;
    }

    @Transactional
    public Common.CommonResponse updatePoint(Event.EventRequest request) {
        try {
            // point 지갑 조회
            PointEntity pointEntity = pointRepository.findByUser_IdAndDeletedTimeIsNull(UUIDFormatter.check(request.getUserId()));
            if (request.getAction().equals(ActionType.ADD.name())) {
                pointEntity.setPoint(pointEntity.getPoint() + addPoint(pointEntity, request.getReviewId(), request.getPlaceId(),request.getContent(), request.getAttachedPhotoIds()));
            } else if (request.getAction().equals(ActionType.MOD.name())) {
                pointEntity.setPoint(pointEntity.getPoint() + modReview(request.getReviewId(),pointEntity, request.getPlaceId(),request.getAttachedPhotoIds()));
            } else if (request.getAction().equals(ActionType.DELETE.name())) {
                pointEntity.setPoint(pointEntity.getPoint() + cancelPoint(request.getReviewId(), pointEntity, request.getPlaceId()));
            }
            else{
                throw new CustomResponseStatusException(ExceptionCode.WRONG_ACTION, "");
            }
            pointRepository.save(pointEntity);
        } catch (CustomResponseStatusException e) {
            throw e;
        }
        return Common.CommonResponse.builder()
                .result(true)
                .message(request.getAction())
                .build();
    }

    @Transactional
    public Integer cancelPoint(String reviewId, PointEntity pointEntity, String placeId) {
        List<PointHistoryEntity> pointHistoryEntity = pointHistoryRepository
                .findByReviewIdAndPointEntity_IdAndDeletedTimeIsNullOrderByCreatedTimeDesc(UUIDFormatter.check(reviewId), pointEntity.getId());
        if(pointHistoryEntity.size() == 0)
            throw new CustomResponseStatusException(ExceptionCode.HISTORY_NOT_FOUND, "");
        if(pointHistoryEntity.get(0).getAction().equals(ActionType.DELETE.name()))
            throw new CustomResponseStatusException(ExceptionCode.HISTORY_NOT_FOUND, "");
        int pointSum = 0;
        pointHistoryEntity.stream().map(
                p -> {
                    p.setDeletedTime(LocalDateTime.now(ZoneOffset.UTC));
                    pointHistoryRepository.save(p);
                    return p;
                }).collect(Collectors.toList());

        // pointHistory 생성
        PointHistoryEntity review = PointHistoryEntity.builder()
                .pointEntity(pointEntity)
                .change(pointHistoryEntity.get(0).getEventPoint() * -1)
                .eventPoint(0)
                .reviewId(UUIDFormatter.check(reviewId))
                .placeId(UUIDFormatter.check(placeId))
                .imageNum(pointHistoryEntity.get(0).getImageNum())
                .type("Review")
                .action(ActionType.DELETE.name())
                .deletedTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        pointHistoryRepository.save(review);
        return review.getChange();
    }

    public Integer addPoint(PointEntity pointEntity, String reviewId, String placeId, String content, List<String> imageList) {
        int point = 0;
        // 해당 장소에 처음 남기는 리뷰인지 체크
        List<PointHistoryEntity> placeReviewList = pointHistoryRepository.findByPlaceIdAndDeletedTimeIsNull(UUIDFormatter.check(placeId));
        if(placeReviewList.size() == 0) {
            point++;
            log.info("case3");
        }
        // 사용자가 해당 장소에 이미 리뷰를 남겼는지 체크
        List<PointHistoryEntity> filteredList = placeReviewList.stream()
                .filter(p -> p.getPointEntity().getId().equals(pointEntity.getId()))
                .collect(Collectors.toList());
        if(filteredList.size()>0)
            throw new CustomResponseStatusException(ExceptionCode.HISTORY_EXIST, "");
        // 글자수 하나 이상이면 +1
        if (!content.equals(" ") && content.length()>0) {
            point++;
            log.info("case1");
        }
        // 리뷰에 사진이 있는지 체크 +1
        int imageSize = 0;
        if(null != imageList && imageList.size()>0) {
            point++;
            imageSize = imageList.size();
            log.info("case2");
        }

        // pointHistory 생성
        PointHistoryEntity review = PointHistoryEntity.builder()
                .pointEntity(pointEntity)
                .change(point)
                .eventPoint(point)
                .reviewId(UUIDFormatter.check(reviewId))
                .placeId(UUIDFormatter.check(placeId))
                .imageNum(imageSize)
                .type("Review")
                .action(ActionType.ADD.name())
                .build();
        pointHistoryRepository.save(review);
        return review.getChange();
    }

    @Transactional
    public Integer modReview(String reviewId, PointEntity pointEntity, String placeId, List<String> imageList) {
        // 사용자가 남긴 리뷰가 존재하는지 체크
        List<PointHistoryEntity> pointHistoryEntity = pointHistoryRepository
                .findByReviewIdAndPointEntity_IdAndDeletedTimeIsNullOrderByCreatedTimeDesc(UUIDFormatter.check(reviewId), pointEntity.getId());
        if(pointHistoryEntity.size() == 0)
            throw new CustomResponseStatusException(ExceptionCode.HISTORY_NOT_FOUND, "");
        int imageSize = 0;
        int change = 0;
        // 사진이 삭제됐다면 -1
        if(pointHistoryEntity.get(0).getImageNum() != 0 && (imageList == null || imageList.size() == 0))
//            pointHistoryEntity.setChange(pointHistoryEntity.getChange() - 1);
            change = -1;
        // 사진이 추가됐다면 +1
        else if(pointHistoryEntity.get(0).getImageNum() == 0 && imageList.size() != 0) {
//            pointHistoryEntity.setChange(pointHistoryEntity.getChange() + 1);
            change = 1;
            imageSize = imageList.size();
        }
        // pointHistory 생성
        PointHistoryEntity review = PointHistoryEntity.builder()
                .pointEntity(pointEntity)
                .change(change)
                .eventPoint(pointHistoryEntity.get(0).getEventPoint()+change)
                .reviewId(UUIDFormatter.check(reviewId))
                .placeId(UUIDFormatter.check(placeId))
                .imageNum(imageSize)
                .type("Review")
                .action(ActionType.MOD.name())
                .build();
        pointHistoryRepository.save(review);
        return review.getChange();
    }
}
