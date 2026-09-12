package com.ruoyi.common.enums;

/**
 * @author Adminstrator
 * @create 2020-05-08 18:22
 */
public enum ResultCodeEnum {

    SUCCESS("true","SUCCESS"),
    ERROR("false","ERROR");
    private final String status;
    private final String desc;

    ResultCodeEnum(String status, String desc) {
        this.status = status;
        this.desc =desc;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
