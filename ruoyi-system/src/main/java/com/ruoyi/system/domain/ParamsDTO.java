package com.ruoyi.system.domain;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 18:19
 */
public class ParamsDTO {

    private List<NxpInspectionPmGroupItem> group_item;

    private Integer pm_id;

    private Integer user_id;

    public List<NxpInspectionPmGroupItem> getGroup_item() {
        return group_item;
    }

    public void setGroup_item(List<NxpInspectionPmGroupItem> group_item) {
        this.group_item = group_item;
    }

    public Integer getPm_id() {
        return pm_id;
    }

    public void setPm_id(Integer pm_id) {
        this.pm_id = pm_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
