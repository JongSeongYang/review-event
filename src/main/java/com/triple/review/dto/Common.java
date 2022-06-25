package com.triple.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class Common {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommonResponse {
        private String message;
        private Boolean result;
    }
}
