package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 周PM检查项对象 nxp_inspection_pm_group_item
 * 
 * @author ruoyi
 * @date 2021-02-21
 */
public class NxpInspectionPmGroupItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    private Long item_id;

    /** 分组 */
    @Excel(name = "分组")
    private Long group_id;

    /** 检查项 */
    @Excel(name = "检查项")
    private String item_name;

    /** 状态 */
    @Excel(name = "状态")
    private Integer item_status;

    private String file_ids;

    private List<String> group_item_pic;
    /**
     * 检查项结果：1：正常；2：异常
     */
    private String group_item_result;
    /**
     * 复查结果 1 是 2 否
     */
    private String review_type;

    private String filePath;
    /**
     * 是否检查 0-是 1否
     */
    private String is_check;

    /**
     * 检查组id
     */
    private Long check_group_id;

    private Long check_process_id;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGroup_id(Long group_id) 
    {
        this.group_id = group_id;
    }

    public Long getGroup_id() 
    {
        return group_id;
    }
    public void setItem_name(String item_name) 
    {
        this.item_name = item_name;
    }

    public String getItem_name() 
    {
        return item_name;
    }
    public void setItem_status(Integer item_status) 
    {
        this.item_status = item_status;
    }

    public Integer getItem_status() 
    {
        return item_status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("item_id", getItem_id())
            .append("group_id", getGroup_id())
            .append("item_name", getItem_name())
            .append("item_status", getItem_status())
            .toString();
    }

    public List<String> getGroup_item_pic() {
        return group_item_pic;
    }

    public void setGroup_item_pic(List<String> group_item_pic) {
        this.group_item_pic = group_item_pic;
    }

    public String getGroup_item_result() {
        return group_item_result;
    }

    public void setGroup_item_result(String group_item_result) {
        this.group_item_result = group_item_result;
    }

    public String getFile_ids() {
        return file_ids;
    }

    public void setFile_ids(String file_ids) {
        this.file_ids = file_ids;
    }

    public String getReview_type() {
        return review_type;
    }

    public void setReview_type(String review_type) {
        this.review_type = review_type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    public Long getCheck_group_id() {
        return check_group_id;
    }

    public void setCheck_group_id(Long check_group_id) {
        this.check_group_id = check_group_id;
    }

    public Long getCheck_process_id() {
        return check_process_id;
    }

    public void setCheck_process_id(Long check_process_id) {
        this.check_process_id = check_process_id;
    }
}
