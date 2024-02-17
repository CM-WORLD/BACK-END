package com.cms.world.alert;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;

import static com.cms.world.utils.GlobalCode.*;


@Component
@Getter
@Setter
@NoArgsConstructor // Builder 사용시 필수
@AllArgsConstructor
@Builder
public class AlertMsg {

    @Schema(description = "알림 내용", example = "커미션 진행에 관에 {이브}님에게 1:1 문의가 도착했어요. 확인 부탁드려요!")
    private String text;

    @Schema(description = "알림 수신받은 닉네임", example = "걍진")
    private String receiverNickName;

    @Schema(description = "알림 전송한 닉네임", example = "이브")
    private String senderNickName;

    @Schema(description = "커미션 이름", example = "저가고퀄 커미션")
    private String cmsName;

    @Schema(description = "커미션 신청 제목", example = "저가고퀄 커미션")
    private String applyTitle;

    @Schema(description = "커미션 신청 ID", example = "1234")
    private String applyId;

    @Schema(description = "커미션 신청 상태", example = "신청 완료")
    private String cmsApplyStatus;

    @Schema(description = "커미션 결제 상태", example = "")
    private String paymentStatus;

    @Schema(description = "알림 전송 전화번호", example = "010-1234-5678", required = false)
    private String phoneNum;

    /* 첫 인사말 */
    public String getIntro() {
        StringBuilder sb = new StringBuilder();
        sb.append("안녕하세요,").append(getReceiverNickName()).append("님!").append("\n");
        return sb.toString();
    }

    /* 커미션 신청 정보 얻기, 신청 id는 보안상 제외 */
    public String getCommssionApplyInfo () {
        StringBuilder sb = new StringBuilder();
        sb.append("커미션 이름: ").append(getCmsName()).append("\n");
        sb.append("커미션 상태: ").append(getCmsApplyStatus()).append("\n");
        return sb.toString();
    }

    /* 마지막 인사말 */
    public String getOutTro () {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("확인 부탁드립니다. 감사합니다.").append("\n");
        return sb.toString();
    }

    public String createAlertMsg () {
        StringBuilder sb = new StringBuilder();
        String code = cmsApplyStatus; // 커미션 신청 알림을 위한 상태 코드 조회

        if (code.equals(CMS_RESERVE.getCode())) {
            sb.append("[커미션 예약 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("신규 커미션 예약이 접수되었습니다.").append("\n");
            getCommssionApplyInfo();
        } else if (code.equals(CMS_PENDING.getCode())) {
            sb.append("[커미션 신청 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("신규 커미션 신청이 접수되었습니다.").append("\n");
            getCommssionApplyInfo();
        } else if (code.equals(PAY_PENDING.getCode())) {
            sb.append("[커미션 결제 요청 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("커미션 결제 요청서가 도착했습니다.").append("\n");
            getCommssionApplyInfo();
        } else if (code.equals(PAY_COMPLETE.getCode())) {
            sb.append("[커미션 결제 완료 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("커미션 결제가 완료되었습니다.").append("\n");
            getCommssionApplyInfo();
        } else if (code.equals(CMS_PROCESS.getCode())) {
            sb.append("[커미션 작업 시작 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("요청하신 커미션 작업이 진행 중입니다.").append("\n");
            getCommssionApplyInfo();
//        } else if (code.equals(CMS_MODIFYING.getCode())) {
//            sb.append("[커미션 요청사항 수정 중 알림]").append("\n\n");
//            sb.append(alertMsg.getIntro()).append("\n");
//            sb.append("요청하신 커미션 작업의 요청사항이 수정 중입니다.").append("\n");
//            getCommssionApplyInfo();
        } else if (code.equals(CMS_COMPLETE.getCode())) {
            sb.append("[커미션 작업 완료 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("요청하신 커미션 작업이 완료되었습니다.").append("\n");
            getCommssionApplyInfo();
        } else if (code.equals(CMS_REVIEW.getCode())) {
            sb.append("[커미션 리뷰 작성 완료 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("신규 커미션 리뷰가 작성되었습니다.").append("\n");
            getCommssionApplyInfo();
        } else if (code.equals(CMS_CANCEL.getCode())) {
            sb.append("[커미션 신청 취소 알림]").append("\n\n");
            sb.append(getIntro());
            sb.append("커미션이 취소되었습니다.").append("\n");
        } else if (code.equals(CMS_REJECT.getCode())) {
            sb.append(getIntro());
            sb.append("커미션 신청이 거절되었습니다.").append("\n");
        }

        sb.append(getOutTro()); // 마지막 인사 처리

        return sb.toString();
    }

    public String createBankTransferAlertMsg () {
        StringBuilder sb = new StringBuilder();
        sb.append("[계좌이체 알림]").append("\n\n");
        sb.append(getIntro());
        sb.append("계좌이체가 완료되었습니다.").append("\n");
        sb.append("신청서 제목: ").append(getApplyTitle()).append("\n");
        sb.append("신청서 아이디: ").append(getApplyId()).append("\n");
        sb.append("상태: ").append(getPaymentStatus()).append("\n");
        sb.append(getOutTro());
        return sb.toString();
    }



}
