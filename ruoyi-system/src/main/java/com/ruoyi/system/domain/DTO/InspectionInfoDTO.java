package com.ruoyi.system.domain.DTO;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-19 18:55
 */
public class InspectionInfoDTO {

    private String id;

    private String facility_id;

    private String facility_name;

    private String part_no;

    private String explanation;

    private String head_user;

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    private String create_user;

    private String repair_time;

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    private String submit_time;

    private List<String> repair_file_id;

    private String file_ids;

    public String getQuestion_type_name() {
        return question_type_name;
    }

    public void setQuestion_type_name(String question_type_name) {
        this.question_type_name = question_type_name;
    }

    private String question_type_name;

    public Long getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Long question_type) {
        this.question_type = question_type;
    }

    private Long question_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(String facility_id) {
        this.facility_id = facility_id;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getPart_no() {
        return part_no;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHead_user() {
        return head_user;
    }

    public void setHead_user(String head_user) {
        this.head_user = head_user;
    }

    public String getRepair_time() {
        return repair_time;
    }

    public void setRepair_time(String repair_time) {
        this.repair_time = repair_time;
    }

    public List<String> getRepair_file_id() {
        return repair_file_id;
    }

    public void setRepair_file_id(List<String> repair_file_id) {
        this.repair_file_id = repair_file_id;
    }

    @Override
    public String toString() {
        return "InspectionInfoDTO{" +
                "id=" + id +
                ", facility_id=" + facility_id +
                ", facility_name='" + facility_name + '\'' +
                ", part_no='" + part_no + '\'' +
                ", explanation='" + explanation + '\'' +
                ", head_user='" + head_user + '\'' +
                ", repair_time='" + repair_time + '\'' +
                ", submit_time='" + submit_time + '\'' +
                ", repair_file_id=" + repair_file_id +
                '}';
    }

    public String getFile_ids() {
        return file_ids;
    }

    public void setFile_ids(String file_ids) {
        this.file_ids = file_ids;
    }
}
