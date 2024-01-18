package com.cms.world.domain.vo;


import com.cms.world.utils.GlobalCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertMsgVo {

    private String title;
    private String content;

    public AlertMsgVo crtMsgToAdmin (GlobalCode statusCode) {
        AlertMsgVo msgVo = new AlertMsgVo();
        switch (statusCode) {
            case CMS_RESERVE:
                msgVo.setTitle("커미션 예약 신청");
                msgVo.setContent("쿠키님으로부터 신규 예약 신청이 들어왔습니다.");
                break;
            case PAY_PENDING:
                break;
            case PAY_COMPLETE:
                msgVo.setTitle("커미션 결제 완료");
                msgVo.setContent("커미션 아이디: dhflsfhwet, 신청자: 쿠키");
                break;
            case CMS_REVIEW:
                msgVo.setTitle("커미션 리뷰 작성 완료");
                msgVo.setContent("쿠키님이 커미션 리뷰를 작성했습니다.");
                break;
        }

        return msgVo;
    }

    //커미션 신청자에게 보낼 메시지
    public AlertMsgVo crtMsgToMember (GlobalCode statusCode) {
        AlertMsgVo msgVo = new AlertMsgVo();
        switch (statusCode) {
            case PAY_PENDING:
                msgVo.setTitle("커미션 결제 대기중");
                msgVo.setContent("신청하신 커미션 결제 요청서가 도착했습니다.");
                break;
            case CMS_PROCESS:
                msgVo.setTitle("커미션 작업중");
                msgVo.setContent("현재 쿠키님의 커미션 작업이 시작되었습니다.");
                break;
            case CMS_CONFIRM:
                msgVo.setTitle("커미션 작업 완료");
                msgVo.setContent("쿠키님의 커미션이 완료되었습니다!. " +
                        "커미션 이름: " + "저가고퀄CM" +
                        "");
                break;
        }

        return msgVo;
    }

}
