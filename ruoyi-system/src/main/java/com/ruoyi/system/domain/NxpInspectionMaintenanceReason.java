package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 无需维修原因对象 nxp_inspection_maintenance_reason
 * 
 * @author YanCheng Liu
 * @date 2021-03-14
 */
public class NxpInspectionMaintenanceReason extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 无需维修原因名称 */
    @Excel(name = "无需维修原因名称")
    private String no_maintenance_reason;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 创建时间 */
    private Date create_time;

    /** 更新时间 */
    private Date update_time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNo_maintenance_reason(String no_maintenance_reason) 
    {
        this.no_maintenance_reason = no_maintenance_reason;
    }

    public String getNo_maintenance_reason() 
    {
        return no_maintenance_reason;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setCreate_time(Date create_time) 
    {
        this.create_time = create_time;
    }

    public Date getCreate_time() 
    {
        return create_time;
    }
    public void setUpdate_time(Date update_time) 
    {
        this.update_time = update_time;
    }

    public Date getUpdate_time() 
    {
        return update_time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("no_maintenance_reason", getNo_maintenance_reason())
            .append("status", getStatus())
            .append("create_time", getCreate_time())
            .append("update_time", getUpdate_time())
            .append("remark", getRemark())
            .toString();
    }
}
