package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检查内容对象 nxp_check_content
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
public class NxpCheckContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 检查内容 */
    @Excel(name = "检查内容")
    private String checkContent;

    /** 工序 */
    @Excel(name = "工序")
    private Long processId;

    private String processName;

    private Long checkGroupId;

    private String checkGroupName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCheckContent(String checkContent) 
    {
        this.checkContent = checkContent;
    }

    public String getCheckContent() 
    {
        return checkContent;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkContent", getCheckContent())
            .append("processId", getProcessId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Long getCheckGroupId() {
        return checkGroupId;
    }

    public void setCheckGroupId(Long checkGroupId) {
        this.checkGroupId = checkGroupId;
    }

    public String getCheckGroupName() {
        return checkGroupName;
    }

    public void setCheckGroupName(String checkGroupName) {
        this.checkGroupName = checkGroupName;
    }
}
