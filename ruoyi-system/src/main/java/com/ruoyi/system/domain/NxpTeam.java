package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * GEMBA小组对象 nxp_team
 * 
 * @author DamonZhang
 * @date 2021-03-19
 */
public class NxpTeam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 是否开启1，开启2未开启 */
    @Excel(name = "是否开启1，开启2未开启")
    private Long status;

    /** 团队名称 */
    @Excel(name = "团队名称")
    private String teamName;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long createdAt;

    /**  */
    private Long id;

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setTeamName(String teamName) 
    {
        this.teamName = teamName;
    }

    public String getTeamName() 
    {
        return teamName;
    }
    public void setCreatedAt(Long createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Long getCreatedAt() 
    {
        return createdAt;
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
            .append("status", getStatus())
            .append("teamName", getTeamName())
            .append("createdAt", getCreatedAt())
            .append("id", getId())
            .toString();
    }
}
