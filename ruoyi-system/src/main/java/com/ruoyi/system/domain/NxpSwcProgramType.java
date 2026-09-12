package com.ruoyi.system.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检查问题类型对象 nxp_swc_program_type
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
public class NxpSwcProgramType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 问题分类 */
    @Excel(name = "问题分类")
    private String typeName;

    /** 状态：1：启用；2：禁用 */
    @Excel(name = "状态：1：启用；2：禁用")
    private Long status;

    /**  */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /**  */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 所属分组ID */
    @Excel(name = "所属分组ID")
    private Long groupId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /** 所属分组名称 */
    @Excel(name = "所属分组名称")
    private String groupName;

    /** $table.subTable.functionName信息 */
    private List<NxpSwcProgram> nxpSwcProgramList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
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
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }

    public List<NxpSwcProgram> getNxpSwcProgramList()
    {
        return nxpSwcProgramList;
    }

    public void setNxpSwcProgramList(List<NxpSwcProgram> nxpSwcProgramList)
    {
        this.nxpSwcProgramList = nxpSwcProgramList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeName", getTypeName())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .append("groupId", getGroupId())
            .append("nxpSwcProgramList", getNxpSwcProgramList())
            .toString();
    }
}
