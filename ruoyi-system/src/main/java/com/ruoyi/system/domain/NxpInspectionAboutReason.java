package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 中止维修原因对象 nxp_inspection_about_reason
 * 
 * @author YanCheng Liu
 * @date 2021-03-01
 */
public class NxpInspectionAboutReason extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 中止原因名称 */
    @Excel(name = "中止原因名称")
    private String aboutReasonName;

    /** 状态 1启用 2禁用 */
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAboutReasonName(String aboutReasonName) 
    {
        this.aboutReasonName = aboutReasonName;
    }

    public String getAboutReasonName() 
    {
        return aboutReasonName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("aboutReasonName", getAboutReasonName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
