package com.ruoyi.system.domain;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 18:21
 */
public class PmParams {

    private Integer pm_id;

    private Integer user_id;

    private String fileIds;

    private List<Integer> review_type;

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

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public List<Integer> getReview_type() {
        return review_type;
    }

    public void setReview_type(List<Integer> review_type) {
        this.review_type = review_type;
    }
}
