package com.cms.world.domain.vo;


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
    private String chatId;

    private String asker; //신청자명
    private String title;
    private String text; // 내용이 text여야 한다.
    private String cmsName;

}
