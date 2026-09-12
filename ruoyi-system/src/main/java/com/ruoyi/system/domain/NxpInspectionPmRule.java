package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 周PM检查规则对象 nxp_inspection_pm_rule
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
public class NxpInspectionPmRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    private Long group_id;

    public Long getProcessOwner() {
        return processOwner;
    }

    public void setProcessOwner(Long processOwner) {
        this.processOwner = processOwner;
    }

    private Long processOwner;

    /** 检查人 */
    @Excel(name = "检查人")
    private Long user_id;

    public Long getReview_user_id() {
        return review_user_id;
    }

    public void setReview_user_id(Long review_user_id) {
        this.review_user_id = review_user_id;
    }

    private Long review_user_id;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    private Long type;

    /** 设备 */
    @Excel(name = "设备")
    private String facility_id;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    private String process;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private String groupName;

    /** 检查周期 */
    @Excel(name = "检查周期")
    private BigDecimal cycle;

    public Float getCycleShow() {
        return cycleShow;
    }

    public void setCycleShow(Float cycleShow) {
        this.cycleShow = cycleShow;
    }

    private Float cycleShow;

    /** 规则开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "规则开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;
    /**
     * 设备名称
     */
    private String facilityNumber;
    /**
     * 检查人姓名
     */
    private String userName;

    public String getReviewUserName() {
        return ReviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        ReviewUserName = reviewUserName;
    }

    private String ReviewUserName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUser_id(Long user_id)
    {
        this.user_id = user_id;
    }

    public Long getUser_id()
    {
        return user_id;
    }
    public void setFacility_id(String facility_id)
    {
        this.facility_id = facility_id;
    }

    public String getFacility_id()
    {
        return facility_id;
    }
    public void setCycle(BigDecimal cycle) 
    {
        this.cycle = cycle;
    }

    public BigDecimal getCycle() 
    {
        return cycle;
    }
    public void setBeginTime(Date beginTime) 
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime() 
    {
        return beginTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("user_id", getUser_id())
            .append("group_id", getGroup_id())
            .append("userName", getUserName())
            .append("process", getProcess())
            .append("groupName", getGroupName())
            .append("ReviewUserName", getReviewUserName())
            .append("facility_id", getFacility_id())
            .append("cycle", getCycle())
            .append("cycleShow", getCycleShow())
            .append("beginTime", getBeginTime())
            .append("processOwner", getProcessOwner())
            .toString();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFacilityNumber() {
        return facilityNumber;
    }

    public void setFacilityNumber(String facilityNumber) {
        this.facilityNumber = facilityNumber;
    }
}
