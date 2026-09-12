package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 外部关联对象 nxp_external_authority
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
public class NxpExternalAuthority extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系统id */
    @Excel(name = "系统id")
    private Long systemId;

    /** 权限名称 */
    @Excel(name = "权限名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /**  */
    private Long id;

    public void setSystemId(Long systemId) 
    {
        this.systemId = systemId;
    }

    public Long getSystemId() 
    {
        return systemId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("systemId", getSystemId())
            .append("name", getName())
            .append("status", getStatus())
            .append("id", getId())
            .toString();
    }
}
