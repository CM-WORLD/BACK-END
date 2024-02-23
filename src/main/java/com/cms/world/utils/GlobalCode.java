package com.cms.world.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalCode { //타입 등의 코드

    // 커미션 샘플 타입
    TYPE_SINGLE("TY01", "1인 기본"),
//    TYPE_SINGLE_BG("TY02", "1인 배경"),
    TYPE_COUP("TY03", "2인 기본"),
//    TYPE_COUP_BG("TY04", "2인 배경"),
    TYPE_MULTI("TY05", "단체 (3인 이상)"),


    /* 커미션 신청 이미지 상태 */
    APPLIED_IMG("CG01", "신청 이미지"),
    COMPLETE_IMG("CG02", "완성 이미지"),

    /* 커미션 신청 프로세스 */
    CMS_RESERVE("CM00", "예약 접수"),
    CMS_PENDING("CM01", "신청 접수"), // 신청자가 신청서 신규 생성
    PAY_PENDING("CM02", "결제 요청"), // 관리자가 결제 확인하고 결제 요청서 보냄
    PAY_COMPLETE("CM03", "결제 완료"),
    CMS_PROCESS("CM04", "작업중"),
    CMS_MODIFYING("CM05", "요청사항 수정중"),
    CMS_COMPLETE("CM06", "작업 완료"),
    CMS_REVIEW("CM07", "리뷰 작성 완료"),
    CMS_CANCEL("CM08", "취소"),
    CMS_REJECT("CM09", "거절"),

    //커미션 열/닫 상태
    CMS_CLOSED("RP01", "커미션 닫힘"),
    CMS_OPENED("RP02", "커미션 열림"),

    //게시판 타입
    BBS_APLY ("BS01", "커미션 신청 게시판"),
    BBS_INQUIRY ("BS02", "문의 게시판"),

    //회원 로그인 타입
    OAUTH_NAVER("NAVER", "네이버 로그인"),
    OAUTH_KAKAO("KAKAO", "카카오 로그인"),
    OAUTH_TWITTER("TWITTER", "트위터 로그인"),
    
    //회원 상태
    USER_ACTIVE("US01", "정상 회원"),
    USER_BLOCKED("US02", "차단 회원"),

    /* 결제 상태 */
    PAYMENT_PENDING("PM01", "결제 대기"),
    PAYMENT_COMPLETE("PM02", "결제 완료"),
    PAYMENT_FAIL("PM03", "결제 실패"),
    PAYMENT_CANCEL("PM04", "결제 취소"),
    PAYMENT_REFUND("PM05", "환불 완료"),
    PAYMENT_BANK_PROCESS("PM06", "처리 대기중(계좌이체)"),

    /* 결제, 결제수단 타입 */
    PAYMENT_TOSS("PY01", "TOSS"),
    PAYMENT_BANK("PY02", "계좌이체"),
    PAY_BASE_AMT("PD01", "기본금"),
    PAY_ADD_AMT("PD02", "추가금");

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
