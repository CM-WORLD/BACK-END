package com.cms.world.api;


import org.springframework.stereotype.Component;

@Component
public class ApiUrl {

    private Profile profile;

    private String appNo;

    private String envAppSystemId = "cms";

    public String getUrl() {
        return "";
    }

//    public ApiUrl(Profile profile) {
//        this.profile = profile;
//        this.appNo = "001";
//        System.setProperty("application.system.id", this.envAppSystemId);
//    }

    public static class ApiUrlBuilder {
        private Profile profile;


        ApiUrlBuilder() {

        }

        public ApiUrlBuilder profile (final Profile profile){
            this.profile = profile;
            return this;
        }

    }

    private String getProfile() {
        String profile = System.getProperty("spring.profiles.active");
        return ApiUrl.Profile.valueOf(profile).value();
    }

    public static enum Profile {
        local("dev"),
        dev("dev"),
        qa("qa"),
        prd("");

        String profile;

        private Profile(String profile) {
            this.profile = profile;
        }

        String value() {
            return this.profile;
        }
    }

    public static enum AppSystem {
        CMS("cms", true),
        CMI("cmi", false); // cms application admin

        String app;
        boolean isUserSystem;

        private AppSystem(String app, boolean isUserSystem) {
            this.app = app;
            this.isUserSystem = isUserSystem;
        }

        String value() {
            return this.app;
        }

        boolean isUserSystem() {
            return this.isUserSystem;
        }
    }
}
