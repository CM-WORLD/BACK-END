package com.cms.world.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalStatus {


    SUCCESS(200, "성공"),
    INVALID_PARAMETER(400, "잘못된 요청"),
    INTERNAL_SERVER_ERR(500, "서버 에러"),

    //카카오
    KAK_CRT_TOKEN_FAILED( 510, "카카오 토큰 발급 실패"),
    KAK_SEND_MSG_SUCCESS(0, "카카오 메세지 전송 성공"),
    KAK_SEND_MSG_FAILED(511, "카카오 메세지 전송 실패"),


    EXECUTE_SUCCESS(1, "실행 성공"),
    EXECUTE_FAILED(0, "실행 실패");


    private final int status;
    private final String msg;

}
