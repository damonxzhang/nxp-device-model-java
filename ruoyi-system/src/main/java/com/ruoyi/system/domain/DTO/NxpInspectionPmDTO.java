package com.ruoyi.system.domain.DTO;

import com.ruoyi.system.domain.NxpInspectionPmGroupItem;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-21 16:58
 */
public class NxpInspectionPmDTO {

    private String pm_id;

    private String facility_id;

    private String facility_name;

    private String process;

    private String head_user;

    private String pm_status;

    List<NxpInspectionPmGroupItem> pm_name_list;

    public String getPm_id() {
        return pm_id;
    }

    public void setPm_id(String pm_id) {
        this.pm_id = pm_id;
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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getHead_user() {
        return head_user;
    }

    public void setHead_user(String head_user) {
        this.head_user = head_user;
    }

    public List<NxpInspectionPmGroupItem> getPm_name_list() {
        return pm_name_list;
    }

    public void setPm_name_list(List<NxpInspectionPmGroupItem> pm_name_list) {
        this.pm_name_list = pm_name_list;
    }

    @Override
    public String toString() {
        return "NxpInspectionPmDTO{" +
                "pm_id='" + pm_id + '\'' +
                ", facility_id='" + facility_id + '\'' +
                ", facility_name='" + facility_name + '\'' +
                ", process='" + process + '\'' +
                ", head_user='" + head_user + '\'' +
                ", pm_name_list=" + pm_name_list +
                '}';
    }

    public String getPm_status() {
        return pm_status;
    }

    public void setPm_status(String pm_status) {
        this.pm_status = pm_status;
    }
}
