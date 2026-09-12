package com.ruoyi.system.domain;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 14:04
 */
public class NxpSparePartProcess {

    private String r_process;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private String p_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    @Override
    public String toString() {
        return "NxpSparePartProcess{" +
                "r_process='" + r_process + '\'' +
                ", p_id='" + p_id + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getR_process() {
        return r_process;
    }

    public void setR_process(String r_process) {
        this.r_process = r_process;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
}
