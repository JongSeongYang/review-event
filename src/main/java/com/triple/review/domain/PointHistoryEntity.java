package com.triple.review.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PointHistory", indexes =@Index(name = "ph_index", columnList = "reviewId, placeId") )
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PointHistoryEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id; // 포인트 지갑 주소
  private String type; // 이벤트 타입
  @ManyToOne
  @JoinColumn(name = "pointId")
  private PointEntity pointEntity; // 포인트 지갑 주소
  private UUID reviewId; // 작성한 리뷰 ID
  private UUID placeId; // 장소 ID
  private Integer change; // 포인트 변동량
  private Integer imageNum;
  private LocalDateTime deletedTime;
}
