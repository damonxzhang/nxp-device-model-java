package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * SWC互查问题对象 nxp_swc_check
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
public class NxpSwcCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 被检查人 */
    @Excel(name = "被检查人")
    private Long checkedUserId;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /**  */
    private Date createdAt;

    /**  */
    private Date updatedAt;

    /** 工序 */
    @Excel(name = "工序")
    private Long processId;

    /** 检查组 */
    @Excel(name = "检查组")
    private Long programGroupId;

    /** 被检查人姓名 */
    private String checkedUserName;

    /** 工序名称 */
    private String processName;

    /** 检查组名称 */
    private String programGroupName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCheckedUserId(Long checkedUserId) 
    {
        this.checkedUserId = checkedUserId;
    }

    public Long getCheckedUserId() 
    {
        return checkedUserId;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setProgramGroupId(Long programGroupId) 
    {
        this.programGroupId = programGroupId;
    }

    public Long getProgramGroupId() 
    {
        return programGroupId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkedUserId", getCheckedUserId())
            .append("checkTime", getCheckTime())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .append("processId", getProcessId())
            .append("programGroupId", getProgramGroupId())
            .toString();
    }

    public String getCheckedUserName() {
        return checkedUserName;
    }

    public void setCheckedUserName(String checkedUserName) {
        this.checkedUserName = checkedUserName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProgramGroupName() {
        return programGroupName;
    }

    public void setProgramGroupName(String programGroupName) {
        this.programGroupName = programGroupName;
    }
}
