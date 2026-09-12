package com.ruoyi.system.domain.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.system.domain.NxpInspectionPmGroupItem;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-21 19:10
 */
public class PmCheckDTO {

    private String pm_id;

    private String check_time;

    private String user_info;

    private String check_id;

    private List<NxpInspectionPmGroupItem> group_item;

    @JsonIgnore
    private String group_item_id;

    @JsonIgnore
    private String group_item_result;

    public String getPm_id() {
        return pm_id;
    }

    public void setPm_id(String pm_id) {
        this.pm_id = pm_id;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public List<NxpInspectionPmGroupItem> getGroup_item() {
        return group_item;
    }

    public void setGroup_item(List<NxpInspectionPmGroupItem> group_item) {
        this.group_item = group_item;
    }

    @Override
    public String toString() {
        return "PmCheckDTO{" +
                "pm_id='" + pm_id + '\'' +
                ", check_time='" + check_time + '\'' +
                ", user_info='" + user_info + '\'' +
                ", check_id='" + check_id + '\'' +
                ", group_item=" + group_item +
                '}';
    }

    public String getGroup_item_id() {
        return group_item_id;
    }

    public void setGroup_item_id(String group_item_id) {
        this.group_item_id = group_item_id;
    }

    public String getGroup_item_result() {
        return group_item_result;
    }

    public void setGroup_item_result(String group_item_result) {
        this.group_item_result = group_item_result;
    }
}
