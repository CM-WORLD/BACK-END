package com.cms.world.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    // {status: , msg: } 리턴
    public static Map<String, Object> resultMap(int statusCode) {
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

    public static Map<String, Object> resultMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj != null) {
            map.put("status" , GlobalStatus.SUCCESS.getStatus());
            map.put("data", obj);
        } else {
            map.put("status" , GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("data", obj);
        }
        return map;
    }

    public static Map<String, Object> resultMap(List list) {
        Map<String, Object> map = new HashMap<>();
        if (list != null) {
            map.put("status" , GlobalStatus.SUCCESS.getStatus());
            map.put("data", list);
        } else {
            map.put("status" , GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("data", list);
        }
        return map;
    }
}
