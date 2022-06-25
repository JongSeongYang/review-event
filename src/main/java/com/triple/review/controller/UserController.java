package com.triple.review.controller;

import com.triple.review.dto.Common;
import com.triple.review.dto.Event;
import com.triple.review.dto.User;
import com.triple.review.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "create user", notes = "create user")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Common.CommonResponse> createUser(@RequestBody User.UserRequest userRequest) {
        UUID user = userService.createUser(userRequest);
        Common.CommonResponse response = Common.CommonResponse.builder()
                .result(true)
                .message(user.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "get user points", notes = "get user points")
    @GetMapping(value = "/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User.PointResponse> getPoints(@RequestParam String userId) {
        User.PointResponse response = userService.getUserPoints(userId);
        return ResponseEntity.ok(response);
    }
}
