package com.ruoyi.system.domain;

/**
 * @author Yancheng Liu
 * @create 2021-02-21 22:14
 */
public class WareHouse {

    private String w_id;

    private String w_area;

    private double w_capacity;

    private String p_id;

    private String w_lock;

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public String getW_area() {
        return w_area;
    }

    public void setW_area(String w_area) {
        this.w_area = w_area;
    }

    public double getW_capacity() {
        return w_capacity;
    }

    public void setW_capacity(double w_capacity) {
        this.w_capacity = w_capacity;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getW_lock() {
        return w_lock;
    }

    public void setW_lock(String w_lock) {
        this.w_lock = w_lock;
    }
}
