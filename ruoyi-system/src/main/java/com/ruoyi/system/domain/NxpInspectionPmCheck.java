package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 周PM复查对象 nxp_inspection_pm_check
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public class NxpInspectionPmCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** pm表id */
    @Excel(name = "pm表id")
    private Long pm_id;

    /** 设备检查项id */
    @Excel(name = "设备检查项id")
    private Long group_item_id;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date check_time;

    /** 复查确认 */
    @Excel(name = "复查确认")
    private Integer review_type;

    /** 检查人或者复查人id */
    @Excel(name = "检查人或者复查人id")
    private Long user_info_id;

    /** 类型 */
    @Excel(name = "类型")
    private Long type;

    /** 检查项结果：1：正常；2：异常 */
    @Excel(name = "检查项结果：1：正常；2：异常")
    private Long group_item_result;

    private NxpInspectionPm nxpInspectionPm;

    private NxpInspectionPmGroupItem nxpInspectionPmGroupItem;

    /** 图片id */
    private String file_id;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPm_id(Long pm_id) 
    {
        this.pm_id = pm_id;
    }

    public Long getPm_id() 
    {
        return pm_id;
    }
    public void setGroup_item_id(Long group_item_id) 
    {
        this.group_item_id = group_item_id;
    }

    public Long getGroup_item_id() 
    {
        return group_item_id;
    }
    public void setCheck_time(Date check_time) 
    {
        this.check_time = check_time;
    }

    public Date getCheck_time() 
    {
        return check_time;
    }
    public void setReview_type(Integer review_type) 
    {
        this.review_type = review_type;
    }

    public Integer getReview_type() 
    {
        return review_type;
    }
    public void setUser_info_id(Long user_info_id) 
    {
        this.user_info_id = user_info_id;
    }

    public Long getUser_info_id() 
    {
        return user_info_id;
    }
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
    }
    public void setGroup_item_result(Long group_item_result) 
    {
        this.group_item_result = group_item_result;
    }

    public Long getGroup_item_result() 
    {
        return group_item_result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pm_id", getPm_id())
            .append("group_item_id", getGroup_item_id())
            .append("check_time", getCheck_time())
            .append("review_type", getReview_type())
            .append("user_info_id", getUser_info_id())
            .append("type", getType())
            .append("group_item_result", getGroup_item_result())
            .toString();
    }

    public NxpInspectionPm getNxpInspectionPm() {
        return nxpInspectionPm;
    }

    public void setNxpInspectionPm(NxpInspectionPm nxpInspectionPm) {
        this.nxpInspectionPm = nxpInspectionPm;
    }

    public NxpInspectionPmGroupItem getNxpInspectionPmGroupItem() {
        return nxpInspectionPmGroupItem;
    }

    public void setNxpInspectionPmGroupItem(NxpInspectionPmGroupItem nxpInspectionPmGroupItem) {
        this.nxpInspectionPmGroupItem = nxpInspectionPmGroupItem;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
}
