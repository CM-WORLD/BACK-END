package com.cms.world.common.code;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalStatus {


    SUCCESS(200, "성공"),
    BAD_REQUEST(400, "잘못된 요청"),
    ATK_VALID(200, "액세스 토큰 유효"),
    ATK_REISSUED (205, "액세스 토큰 재발급"),
    NOT_FOUND(404, "찾을 수 없음"),
    LOGIN_REQUIRED(410, "인가 정보 없음"),
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
