package com.lyht.business.pub.enums;

/**
 * @Description: 指标类型枚举
 * @Author: xzp
 * @Date: 2020/8/4 15:48
 **/
public enum IndexTypeEnums {

    INDEX_TYPE_ENUMS_FAMILY("family","家庭成员"),
    INDEX_TYPE_ENUMS_HOUSES("houses","房屋"),
    INDEX_TYPE_ENUMS_DECORATION("decoration","房屋装修"),
    INDEX_TYPE_ENUMS_BUILDING("building","附属设施"),
    INDEX_TYPE_ENUMS_TREES("trees","零星树木"),
    INDEX_TYPE_ENUMS_LAND("land","土地"),

    ;
    private String key;
    private String value;

    IndexTypeEnums(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
