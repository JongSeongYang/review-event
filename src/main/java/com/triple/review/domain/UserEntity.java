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
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;
  private String email;

  @OneToOne
  @JoinColumn(name = "pointId")
  private PointEntity pointEntity;
  private LocalDateTime deletedTime;
}
