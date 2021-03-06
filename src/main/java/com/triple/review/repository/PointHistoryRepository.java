package com.triple.review.repository;

import com.triple.review.domain.PointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PointHistoryRepository extends JpaRepository<PointHistoryEntity, UUID> {

    List<PointHistoryEntity> findByReviewIdAndPointEntity_IdAndDeletedTimeIsNullOrderByCreatedTimeDesc(UUID reviewId, UUID pointId);

    List<PointHistoryEntity> findByPlaceIdAndDeletedTimeIsNull(UUID placeId);
}
