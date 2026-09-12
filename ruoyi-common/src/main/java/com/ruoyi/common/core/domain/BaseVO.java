package com.ruoyi.common.core.domain;

import com.ruoyi.common.enums.ResultCodeEnum;

import java.io.Serializable;

/**
 * @author Yancheng Liu
 * @create 2021-02-05 16:44
 */
public class BaseVO<T> implements Serializable {

    private String status;

    private String message;

    private T data;

    public BaseVO() {
        this.status = ResultCodeEnum.SUCCESS.getStatus();
        this.message = ResultCodeEnum.SUCCESS.getDesc();
    }

    private BaseVO(String status) {
        this.status = status;
    }

    private BaseVO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private BaseVO(String status, T data) {
        this.status = status;
        this.data = data;
    }

    private BaseVO(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    //返回成功的BaseVO对象.
    public static <T> BaseVO<T> success() {
        return new BaseVO<>(ResultCodeEnum.SUCCESS.getStatus());
    }

    public static <T> BaseVO<T> success(String message) {
        return new BaseVO<>(ResultCodeEnum.SUCCESS.getStatus(), message);
    }

    public static <T> BaseVO<T> success(T data) {
        return new BaseVO<>(ResultCodeEnum.SUCCESS.getStatus(), data);
    }

    public static <T> BaseVO<T> success(String message, T data) {
        return new BaseVO<>(ResultCodeEnum.SUCCESS.getStatus(), message, data);
    }

    //返回失败的BaseVO对象.
    public static <T> BaseVO<T> error() {
        return new BaseVO<>(ResultCodeEnum.ERROR.getStatus(), ResultCodeEnum.ERROR.getDesc());
    }

    public static <T> BaseVO<T> error(String msg) {
        return new BaseVO<>(ResultCodeEnum.ERROR.getStatus(), msg);
    }

    public static <T> BaseVO<T> error(String status, String msg) {
        return new BaseVO<>(status, msg);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
