package com.cms.world.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalStatus {


    SUCCESS(200, "성공"),
    INVALID_PARAMETER(400, "잘못된 요청"),
    INTERNAL_SERVER_ERR(500, "서버 에러"),


    EXECUTE_SUCCESS(1, "실행 성공"),
    EXECUTE_FAILED(0, "실행 실패");


    private final int status;
    private final String msg;

}
