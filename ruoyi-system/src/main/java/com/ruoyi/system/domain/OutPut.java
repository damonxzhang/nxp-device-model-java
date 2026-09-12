package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yancheng Liu
 * @create 2021-02-07 14:59
 */
public class OutPut implements Serializable {
    private String s_id;

    private Integer o_amount;

    private String e_id;

    private Integer o_id;

    private Date o_time;

    private String p_id;

    @Override
    public String toString() {
        return "OutPut{" +
                "s_id='" + s_id + '\'' +
                ", o_amount=" + o_amount +
                ", e_id='" + e_id + '\'' +
                ", o_id=" + o_id +
                ", o_time=" + o_time +
                ", p_id='" + p_id + '\'' +
                '}';
    }

    public Integer getO_id() {
        return o_id;
    }

    public void setO_id(Integer o_id) {
        this.o_id = o_id;
    }

    public Date getO_time() {
        return o_time;
    }

    public void setO_time(Date o_time) {
        this.o_time = o_time;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public Integer getO_amount() {
        return o_amount;
    }

    public void setO_amount(Integer o_amount) {
        this.o_amount = o_amount;
    }

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }
}
