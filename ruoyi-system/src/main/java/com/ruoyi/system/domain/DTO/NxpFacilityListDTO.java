package com.ruoyi.system.domain.DTO;

/**
 * @author Yancheng Liu
 * @create 2021-02-20 14:57
 */
public class NxpFacilityListDTO {
    private String name;

    private String number;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private String model;

    private String status;

    private String is_ok;

    private String id;

    private String process;

    private String i_status;

    private String i_repair_time;

    private String i_question_type;

    private String head_user;

    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
        this.vendor_id = vendor_id;
    }

    private Long vendor_id;

    public String getRelation_people() {
        return relation_people;
    }

    public void setRelation_people(String relation_people) {
        this.relation_people = relation_people;
    }

    private String relation_people;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_ok() {
        return is_ok;
    }

    public void setIs_ok(String is_ok) {
        this.is_ok = is_ok;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getI_status() {
        return i_status;
    }

    public void setI_status(String i_status) {
        this.i_status = i_status;
    }

    public String getI_repair_time() {
        return i_repair_time;
    }

    public void setI_repair_time(String i_repair_time) {
        this.i_repair_time = i_repair_time;
    }

    public String getI_question_type() {
        return i_question_type;
    }

    public void setI_question_type(String i_question_type) {
        this.i_question_type = i_question_type;
    }

    @Override
    public String toString() {
        return "NxpFacilityListDTO{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", status=" + status +
                ", is_ok=" + is_ok +
                ", id=" + id +
                ", process='" + process + '\'' +
                ", i_status='" + i_status + '\'' +
                ", i_repair_time='" + i_repair_time + '\'' +
                ", i_question_type='" + i_question_type + '\'' +
                ", vendor_id='" + vendor_id + '\'' +
                '}';
    }

    public String getHead_user() {
        return head_user;
    }

    public void setHead_user(String head_user) {
        this.head_user = head_user;
    }
}
