package com.ruoyi.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 15:02
 */
public class NxpSparePartSparePart implements Serializable {
    private String s_id;

    private String s_brand;

    private String s_model;

    private String s_description;

    private String k_kind;

    private String s_vendor;

    private BigDecimal s_price;

    private String s_picture;

    private Integer s_safeamount;

    private Integer s_amount;

    private String w_id;

    private String warehouse_id;

    private String p_name;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    private String p_id;

    public String getHead_user() {
        return head_user;
    }

    public void setHead_user(String head_user) {
        this.head_user = head_user;
    }

    private String head_user;

    private String number;

    private String process;

    private Integer ID;

    private String e_model;

    private String e_brand;

    private String r_process;

    private String e_short;

    private Integer page;

    private Integer default_page;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getE_model() {
        return e_model;
    }

    public void setE_model(String e_model) {
        this.e_model = e_model;
    }

    public String getE_brand() {
        return e_brand;
    }

    public void setE_brand(String e_brand) {
        this.e_brand = e_brand;
    }

    public String getR_process() {
        return r_process;
    }

    public void setR_process(String r_process) {
        this.r_process = r_process;
    }

    public String getE_short() {
        return e_short;
    }

    public void setE_short(String e_short) {
        this.e_short = e_short;
    }

    @Override
    public String toString() {
        return "NxpSparePartSparePart{" +
                "s_id='" + s_id + '\'' +
                ", s_brand='" + s_brand + '\'' +
                ", s_model='" + s_model + '\'' +
                ", s_description='" + s_description + '\'' +
                ", k_kind='" + k_kind + '\'' +
                ", s_vendor='" + s_vendor + '\'' +
                ", s_price=" + s_price +
                ", s_picture='" + s_picture + '\'' +
                ", s_safeamount=" + s_safeamount +
                ", s_amount=" + s_amount +
                ", w_id='" + w_id + '\'' +
                ", warehouse_id='" + warehouse_id + '\'' +
                '}';
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_brand() {
        return s_brand;
    }

    public void setS_brand(String s_brand) {
        this.s_brand = s_brand;
    }

    public String getS_model() {
        return s_model;
    }

    public void setS_model(String s_model) {
        this.s_model = s_model;
    }

    public String getS_description() {
        return s_description;
    }

    public void setS_description(String s_description) {
        this.s_description = s_description;
    }

    public String getK_kind() {
        return k_kind;
    }

    public void setK_kind(String k_kind) {
        this.k_kind = k_kind;
    }

    public String getS_vendor() {
        return s_vendor;
    }

    public void setS_vendor(String s_vendor) {
        this.s_vendor = s_vendor;
    }

    public BigDecimal getS_price() {
        return s_price;
    }

    public void setS_price(BigDecimal s_price) {
        this.s_price = s_price;
    }

    public String getS_picture() {
        return s_picture;
    }

    public void setS_picture(String s_picture) {
        this.s_picture = s_picture;
    }

    public Integer getS_safeamount() {
        return s_safeamount;
    }

    public void setS_safeamount(Integer s_safeamount) {
        this.s_safeamount = s_safeamount;
    }

    public Integer getS_amount() {
        return s_amount;
    }

    public void setS_amount(Integer s_amount) {
        this.s_amount = s_amount;
    }

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getDefault_page() {
        return default_page;
    }

    public void setDefault_page(Integer default_page) {
        this.default_page = default_page;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }
}
