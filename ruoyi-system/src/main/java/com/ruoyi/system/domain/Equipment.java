package com.ruoyi.system.domain;

/**
 * @author Yancheng Liu
 * @create 2021-02-23 9:29
 */
public class Equipment {
    private Integer ID;

    private String e_model;

    private String e_brand;

    private String r_process;

    private String e_short;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    private String e_id;

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
        return "Equipment{" +
                "ID=" + ID +
                ", e_model='" + e_model + '\'' +
                ", e_brand='" + e_brand + '\'' +
                ", r_process='" + r_process + '\'' +
                ", e_short='" + e_short + '\'' +
                ", e_id='" + e_short + '\'' +
                '}';
    }
}
