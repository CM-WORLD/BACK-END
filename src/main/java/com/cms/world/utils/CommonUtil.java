package com.cms.world.utils;

import java.util.HashMap;
import java.util.Map;

public class CommonUtil {


    /* 결괏값 map에 저장 */
    public static Map<Integer, String> getResultMap(int statusCode) {
        Map<Integer, String> map = new HashMap<>();
        if (statusCode == GlobalStatus.EXECUTE_SUCCESS.getStatus()) {
            map.put(GlobalStatus.SUCCESS.getStatus(), GlobalStatus.SUCCESS.getMsg());
        } else map.put(GlobalStatus.INTERNAL_SERVER_ERR.getStatus(), GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        return map;
    }

    public static Map<String, Object> getResultMapTest2(int statusCode) {
        Map<String, Object> map = new HashMap<>();
        if (statusCode == GlobalStatus.EXECUTE_SUCCESS.getStatus()) {
            map.put("status" , GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
        } else {
            map.put("status" , GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }
}
