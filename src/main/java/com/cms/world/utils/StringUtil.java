package com.cms.world.utils;


import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class StringUtil {

    public static final String DEFAULT_CHAR_SET = "UTF-8";

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isBlank(String str) { // 공백 또는 빈 내용 체크
        return (isEmpty(str) || str.isBlank());
    }

    public static String repeat(String str, int times) {
        if(isBlank(str) || times <= 0) return "";
        return str.repeat(times);
    }

    public static String lPad(String str, int length, String padStr) { //좌측을 특정 문자로 채운다.
        int repeatTimes = length - str.length();
        return repeat(padStr, repeatTimes) + str;
    }
    
    // 난수 생성
    public static synchronized String generate()
    {
        int leftLimit = 48; // letter 'A'
        int rightLimit = 90; // letter 'Z'
        int targetStringLength = 18;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}
