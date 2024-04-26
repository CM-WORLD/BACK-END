package com.cms.world.api;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {

    @Value("${cms.api.url}")
    private String cmsUrl;

    @GetMapping("/system")
    public String getApiUrls() {
        return cmsUrl;
    }
}
