package com.cms.world.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalCode { //타입 등의 코드

    TYPE_SINGLE("TY01", "1인 기본"),
    TYPE_SINGLE_BG("TY02", "1인 배경"),
    TYPE_COUP("TY03", "2인 기본"),
    TYPE_COUP_BG("TY04", "2인 배경"),
    TYPE_MULTI_BG("TY05", "3인 이상 배경");

    private final String code;
    private final String desc;
}
