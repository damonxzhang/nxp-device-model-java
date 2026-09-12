package com.ruoyi.framework.shiro.token;

/**
 * @author Yancheng Liu
 * @create 2021-03-09 10:39
 */
public enum LoginType {
    /**
     * 密码登录
     */
    PASSWORD("password"),
    /**
     * 免密码登录
     */
    NOPASSWD("nopasswd");

    private String desc;

    LoginType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
