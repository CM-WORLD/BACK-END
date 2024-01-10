package com.cms.world.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalCode { //타입 등의 코드

    //커미션 신청 타입
    TYPE_SINGLE("TY01", "1인 기본"),
//    TYPE_SINGLE_BG("TY02", "1인 배경"),
    TYPE_COUP("TY03", "2인 기본"),
//    TYPE_COUP_BG("TY04", "2인 배경"),
    TYPE_MULTI("TY05", "단체 (3인 이상)"),

    //커미션 프로세스
    CMS_RESERVE("CM00", "예약 접수"),
    CMS_PENDING("CM01", "신청 접수"),
    PAY_PENDING("CM02", "결제 요청"), //신청 완료, 결제 대기
    PAY_COMPLETE("CM03", "결제 완료"),
//    CMS_START("CM04", "작업 시작"),
    CMS_PROCESS("CM05", "작업중"),
//    CMS_COMPLETE("CM06", "작업물 제출"),
    CMS_REQ_EDITING("CM07", "요청사항 수정중"),
    CMS_CONFIRM("CM08", "작업 완료"),
    CMS_REVIEW("CM09", "리뷰 작성 완료"),

    //커미션 열/닫
    CMS_CLOSED("RP01", "커미션 닫힘"),
    CMS_OPENED("RP02", "커미션 열림"),

    //게시판 타입
    BBS_APLY ("BS01", "커미션 신청 게시판"),
    BBS_INQUIRY ("BS02", "문의 게시판"),
    BBS_REVIEW ("BS03", "커미션 후기 게시판"),

    //회원 상태
    USER_ACTIVE("US01", "정상 회원"),
    USER_BLOCKED("US02", "차단 회원");

    private final String code;
    private final String desc;

    public static String getDescByCode(String status) {
        for (GlobalCode code : GlobalCode.values()) {
            if (code.getCode().equals(status)) {
                return code.getDesc();
            }
        }
        return null;
    }
}
