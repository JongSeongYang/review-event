package com.triple.review.repository;

import com.triple.review.domain.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, UUID> {
    PointEntity findByUser_IdAndDeletedTimeIsNull(UUID userId);
}
