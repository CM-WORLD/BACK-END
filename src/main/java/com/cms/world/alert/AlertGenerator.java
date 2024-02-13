package com.cms.world.alert;


import com.cms.world.utils.GlobalCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertGenerator {


//    public AlertMsg sendAlertToMember (GlobalCode statusCode, AlertMsg alertMsg) {
//        AlertMsg message = new AlertMsg();
//        switch (statusCode) {
//            case CMS_RESERVE:
//                message.setTitle("[커미션 예약 알림]");
//                message.setIntro(alertMsg.createMemberIntroText());
//                message.setText(alertMsg.createCmsStatusText());
//                break;
//        }
//
//        return message;
//    }
//
//    public AlertMsg sendAlertToAdmin (GlobalCode statusCode, AlertMsg alertMsg) {
//        AlertMsg message = new AlertMsg();
//        switch (statusCode) {
//            case CMS_RESERVE:
//                message.setTitle("[커미션 예약 알림]");
//                message.setText("신규 커미션 예약이 접수되었습니다.");
//                message.setCommissionName(alertMsg.getCommissionName());
//                break;
//        }
//
//        return message;
//    }


}
