package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 周PM检查对象 nxp_inspection_pm
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public class NxpInspectionPm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    private Long type;

    /** 设备表id */
    @Excel(name = "设备表id")
    private Long facility_id;

    /** 检查人id */
    @Excel(name = "检查人id")
    private Long userInfo_id;

    /** 复查人ID */
    @Excel(name = "复查人ID")
    private Long review_user_info_id;

    /** 检查时间 (排班检查时间) */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "检查时间 (排班检查时间)", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date check_time;

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    private String checkTime;

    public Date getReal_check_time() {
        return real_check_time;
    }

    public void setReal_check_time(Date real_check_time) {
        this.real_check_time = real_check_time;
    }

    /** 实际检查时间  */
    private Date real_check_time;

    public String getRealCheckTime() {
        return realCheckTime;
    }

    public void setRealCheckTime(String realCheckTime) {
        this.realCheckTime = realCheckTime;
    }

    private String realCheckTime;

    /** 检查状态 */
    @Excel(name = "检查状态")
    private Integer status;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long created_at;

    /** 修改时间 */
    @Excel(name = "修改时间")
    private Long updated_at;

    /** 待处理人ID */
    @Excel(name = "待处理人ID")
    private Long deal_user_id;

    private String facility_name;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    private String type_name;

    public String getFacility_number() {
        return facility_number;
    }

    public void setFacility_number(String facility_number) {
        this.facility_number = facility_number;
    }

    private String facility_number;

    private String head_user;
    /**
     * 工序ID
     */
    private String process;

    private String status_name;

    private String reviewUserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    private String UserName;

    private String dealUserName;

    private String name;

    private Integer pageNum;
    private Integer pageSize;

    private List<NxpInspectionPmGroupItem> nxpInspectionPmGroupItemList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFacility_id(Long facility_id) 
    {
        this.facility_id = facility_id;
    }

    public Long getFacility_id() 
    {
        return facility_id;
    }
    public void setUserInfo_id(Long userInfo_id) 
    {
        this.userInfo_id = userInfo_id;
    }

    public Long getUserInfo_id() 
    {
        return userInfo_id;
    }
    public void setReview_user_info_id(Long review_user_info_id) 
    {
        this.review_user_info_id = review_user_info_id;
    }

    public Long getReview_user_info_id() 
    {
        return review_user_info_id;
    }
    public void setCheck_time(Date check_time) 
    {
        this.check_time = check_time;
    }

    public Date getCheck_time() 
    {
        return check_time;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setCreated_at(Long created_at) 
    {
        this.created_at = created_at;
    }

    public Long getCreated_at() 
    {
        return created_at;
    }
    public void setUpdated_at(Long updated_at) 
    {
        this.updated_at = updated_at;
    }

    public Long getUpdated_at() 
    {
        return updated_at;
    }
    public void setDeal_user_id(Long deal_user_id) 
    {
        this.deal_user_id = deal_user_id;
    }

    public Long getDeal_user_id() 
    {
        return deal_user_id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("facility_id", getFacility_id())
            .append("userInfo_id", getUserInfo_id())
            .append("review_user_info_id", getReview_user_info_id())
            .append("check_time", getCheck_time())
            .append("status", getStatus())
            .append("process", getProcess())
            .append("type", getType())
            .append("type_name", getType_name())
            .append("userName", getUserName())
            .append("created_at", getCreated_at())
            .append("updated_at", getUpdated_at())
            .append("deal_user_id", getDeal_user_id())
            .append("real_check_time", getRealCheckTime())
            .toString();
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getHead_user() {
        return head_user;
    }

    public void setHead_user(String head_user) {
        this.head_user = head_user;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getDealUserName() {
        return dealUserName;
    }

    public void setDealUserName(String dealUserName) {
        this.dealUserName = dealUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<NxpInspectionPmGroupItem> getNxpInspectionPmGroupItemList() {
        return nxpInspectionPmGroupItemList;
    }

    public void setNxpInspectionPmGroupItemList(List<NxpInspectionPmGroupItem> nxpInspectionPmGroupItemList) {
        this.nxpInspectionPmGroupItemList = nxpInspectionPmGroupItemList;
    }
}
