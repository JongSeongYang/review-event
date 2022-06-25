package com.triple.review.controller;

import com.triple.review.dto.Common;
import com.triple.review.dto.Event;
import com.triple.review.service.PointService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final PointService pointService;

    @ApiOperation(value = "event", notes = "event")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Common.CommonResponse> signUp(@RequestBody Event.EventRequest eventRequest) {
        Common.CommonResponse response = pointService.updatePoint(eventRequest);
        return ResponseEntity.ok(response);
    }
}
