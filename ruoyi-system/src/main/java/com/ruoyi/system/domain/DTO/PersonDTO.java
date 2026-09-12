package com.ruoyi.system.domain.DTO;

/**
 * @author Yancheng Liu
 * @create 2021-03-11 10:09
 */
public class PersonDTO {

    private Integer a_id;

    private String p_id;

    private String p_name;

    private String p_password;

    private String p_process;

    private String p_mail;

    public Integer getA_id() {
        return a_id;
    }

    public void setA_id(Integer a_id) {
        this.a_id = a_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_password() {
        return p_password;
    }

    public void setP_password(String p_password) {
        this.p_password = p_password;
    }

    public String getP_process() {
        return p_process;
    }

    public void setP_process(String p_process) {
        this.p_process = p_process;
    }

    public String getP_mail() {
        return p_mail;
    }

    public void setP_mail(String p_mail) {
        this.p_mail = p_mail;
    }
}
