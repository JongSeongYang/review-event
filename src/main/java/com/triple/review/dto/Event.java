package com.triple.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class Event {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventRequest {
        private String type;
        private String action;
        private String reviewId;
        private String content;
        private List<String> attachedPhotoIds;
        private String userId;
        private String placeId;
    }
}
