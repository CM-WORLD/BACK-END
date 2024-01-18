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

    /* service 데이터를 map에 담기 */
    public static Map<String, Object> renderResultByMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            if(obj == null) throw new Exception("renderResultByMap: data not found");
            map.put("status" , GlobalStatus.SUCCESS.getStatus());
            map.put("msg", GlobalStatus.SUCCESS.getMsg());
            map.put("data", obj);

        } catch (Exception e) {
            map.put("status" , GlobalStatus.INTERNAL_SERVER_ERR.getStatus());
            map.put("msg", GlobalStatus.INTERNAL_SERVER_ERR.getMsg());
        }
        return map;
    }

}
