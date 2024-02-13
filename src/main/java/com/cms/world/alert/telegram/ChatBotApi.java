package com.cms.world.alert.telegram;


import com.cms.world.alert.AlertMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="telegram", url="https://api.telegram.org")
public interface ChatBotApi {

    @GetMapping(value = "/bot{token}/sendmessage")
    Object sendMessage(@PathVariable("token")String token,
                       @RequestParam("text") String text,
                       @RequestParam("chat_id") String chatId);
}
