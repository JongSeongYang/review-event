package com.triple.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class User {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointResponse {
        private String userId;
        private Integer points;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointRequest {
        private String userId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRequest {
        private String email;
    }
}
