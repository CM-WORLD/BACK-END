package com.cms.world.domain.social;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class KakaoMsgBo {

    public KakaoMsgBo() {

    }

    private String objType; //text
    private String username;
    private String text; //content
    private String webUrl;
    private String mobileUrl;
    private String btnTitle;

}
