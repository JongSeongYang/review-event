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
@Table(name = "Point")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PointEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id; // 포인트 지갑 주소

  @OneToOne(mappedBy = "pointEntity")
  private UserEntity user;
  private Integer point; // 포인트 양
  private LocalDateTime deletedTime;
}
