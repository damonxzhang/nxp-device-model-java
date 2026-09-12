package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * SWC自查问题对象 nxp_swc_check_self
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
public class NxpSwcCheckSelf extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 检查人 */
    @Excel(name = "检查人")
    private Long userId;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String facilityId;

    /** 批号 */
    @Excel(name = "批号")
    private String partNo;

    /** 备注 */
    @Excel(name = "备注")
    private String comment;

    /** 问题类型 */
    @Excel(name = "问题类型")
    private Long type;

    public String getFacilityNumber() {
        return facilityNumber;
    }

    public void setFacilityNumber(String facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    private String facilityNumber;

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public int getUserShift() {
        return userShift;
    }

    public void setUserShift(int userShift) {
        this.userShift = userShift;
    }

    private int userShift;
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setFacilityId(String facilityId) 
    {
        this.facilityId = facilityId;
    }

    public String getFacilityId() 
    {
        return facilityId;
    }
    public void setPartNo(String partNo) 
    {
        this.partNo = partNo;
    }

    public String getPartNo() 
    {
        return partNo;
    }
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("checkTime", getCheckTime())
            .append("facilityId", getFacilityId())
            .append("partNo", getPartNo())
            .append("comment", getComment())
            .append("type", getType())
            .toString();
    }
}
