package com.cms.world.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {

    @Value("${cms.api.url}")
    private String cmsUrl;
    @GetMapping("/annotation")
    public String getSystem() {
        return cmsUrl;
    }

    @Autowired
    private Environment environment;

    @GetMapping("/system")
    public String getApiUrls() {
        String cmsApiUrl = environment.getProperty("cms.api.url");
        String cmiApiUrl = environment.getProperty("cmi.api.url");
        return "CMS API URL: " + cmsApiUrl + ", CMI API URL: " + cmiApiUrl;
    }
}
