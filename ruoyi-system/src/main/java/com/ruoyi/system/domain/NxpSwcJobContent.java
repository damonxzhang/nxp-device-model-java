package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作业内容管理对象 nxp_swc_job_content
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
public class NxpSwcJobContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 作业内容 */
    @Excel(name = "作业内容")
    private String jobContent;

    /** Production */
    @Excel(name = "Production")
    private String production;

    /** 状态 */
    @Excel(name = "状态")
    private Integer state;

    /** 检查组id */
    private Long checkGroupId;

    /** 检查组工序id */
    private Long checkProcessId;

    private String checkGroupName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setJobContent(String jobContent) 
    {
        this.jobContent = jobContent;
    }

    public String getJobContent() 
    {
        return jobContent;
    }
    public void setProduction(String production) 
    {
        this.production = production;
    }

    public String getProduction() 
    {
        return production;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jobContent", getJobContent())
            .append("production", getProduction())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

    public Long getCheckGroupId() {
        return checkGroupId;
    }

    public void setCheckGroupId(Long checkGroupId) {
        this.checkGroupId = checkGroupId;
    }

    public Long getCheckProcessId() {
        return checkProcessId;
    }

    public void setCheckProcessId(Long checkProcessId) {
        this.checkProcessId = checkProcessId;
    }

    public String getCheckGroupName() {
        return checkGroupName;
    }

    public void setCheckGroupName(String checkGroupName) {
        this.checkGroupName = checkGroupName;
    }
}
