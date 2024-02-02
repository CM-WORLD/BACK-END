package com.cms.world.domain.vo;


import com.cms.world.utils.GlobalCode;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AlertMsg {

    @Value("${telegram.notification.chat.id}")
    @SerializedName("chat_id")
    private String adminChatId; //어드민 텔레그램 챗봇 id

    private String memberPhoneNum; //회원 전화번호

    private String title;
    private String intro;
    private String text; // 내용이 text여야 한다.

    private String memberName;
    private String commissionName;
    private String commissionId;
    private String commissionStatus;

    public String createMemberIntroText() {
        StringBuilder sb = new StringBuilder();
        sb.append("안녕하세요,").append(memberName).append("님").append("\n");
        return sb.toString();
    }

    public String createStatusText(GlobalCode statusCode) {
        StringBuilder sb = new StringBuilder();
        switch (statusCode) {
            case CMS_RESERVE:
                sb.append("[커미션 예약 완료 안내]").append("\n");
                sb.append("신규 커미션 예약이 접수되었습니다.").append("\n");
                break;
            case CMS_COMPLETE:
                sb.append("[커미션 작업 완료 안내]").append("\n");
                sb.append("커미션 작업이 완료되었습니다.").append("\n");
                break;
            case CMS_CANCEL:
                sb.append("[커미션 취소 안내]").append("\n");
                sb.append("커미션이 취소되었습니다.").append("\n");
                break;
            case CMS_REJECT:
                sb.append("[커미션 거절 안내]").append("\n");
                sb.append("커미션 예약이 거절되었습니다.").append("\n");
                break;

                // 문의 메세지 도착도 GlobalCode 상태 추가 및 작성할 것...
        }
        return sb.toString();
    }

    public String createCmsStatusText() {
        StringBuilder sb = new StringBuilder();
        sb.append("신청자: ").append(commissionName).append("\n");
        sb.append("커미션 ID: ").append(commissionId).append("\n");
        sb.append("커미션 상태: ").append(commissionStatus).append("\n");
        return sb.toString();
    }




}
