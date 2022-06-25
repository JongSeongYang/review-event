package com.triple.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    // AUTH 10000
    EMAIL_DUPLICATED(10001, "이미 존재하는 이메일입니다."),
    USER_CREATE_FAIL(10002, "계정 생성에 실패했습니다."),
    USER_NOT_FOUND(10003, "사용자를 찾을 수 없습니다."),

    // HISTORY 20000
    HISTORY_NOT_FOUND(20001, "히스토리를 찾을 수 없습니다."),
    HISTORY_EXIST(20002, "히스토리가 이미 존재합니다."),
    WRONG_ACTION(20003, "잘못된 ACTION 입니다."),

    /* 90000 Network(External Error) */
    INTERNAL_ERROR(500, "Internal server error");

    private final int code;
    private final String message;
    private final HttpStatus status = OK;
}
