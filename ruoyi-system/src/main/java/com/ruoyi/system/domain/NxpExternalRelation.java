package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 外部权限对象 nxp_external_relation
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
public class NxpExternalRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 系统id */
    @Excel(name = "系统id")
    private Long systemId;

    /** 权限id */
    @Excel(name = "权限id")
    private Long authorityId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 1启用 2 禁用 */
    @Excel(name = "1启用 2 禁用")
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
    public void setAuthorityId(Long authorityId) 
    {
        this.authorityId = authorityId;
    }

    public Long getAuthorityId() 
    {
        return authorityId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
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
            .append("authorityId", getAuthorityId())
            .append("userId", getUserId())
            .append("status", getStatus())
            .append("id", getId())
            .toString();
    }
}
