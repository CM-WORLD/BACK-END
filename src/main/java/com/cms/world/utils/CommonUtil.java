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
}
