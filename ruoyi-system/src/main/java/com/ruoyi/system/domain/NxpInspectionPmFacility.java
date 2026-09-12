package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 周PM检查项关联表对象 nxp_inspection_pm_facility
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public class NxpInspectionPmFacility extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备表id */
    @Excel(name = "设备表id")
    private Long facility_id;

    /** 分组id */
    @Excel(name = "分组id")
    private Long group_id;

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
    public void setGroup_id(Long group_id) 
    {
        this.group_id = group_id;
    }

    public Long getGroup_id() 
    {
        return group_id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("facility_id", getFacility_id())
            .append("group_id", getGroup_id())
            .toString();
    }
}
