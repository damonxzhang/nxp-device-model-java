package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维修详情备件对象 nxp_inspection_service_detail
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public class NxpInspectionServiceDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 备件表id */
    @Excel(name = "备件表id")
    private Long spare_id;

    /** 备件名称 */
    @Excel(name = "备件名称")
    private String spare_explain;

    /** 备件数量 */
    @Excel(name = "备件数量")
    private Integer spare_number;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long created_at;

    /** 修改时间 */
    @Excel(name = "修改时间")
    private Long updated_at;

    /** 维修详情表id */
    @Excel(name = "维修详情表id")
    private Long service_id;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSpare_id(Long spare_id) 
    {
        this.spare_id = spare_id;
    }

    public Long getSpare_id() 
    {
        return spare_id;
    }
    public void setSpare_explain(String spare_explain) 
    {
        this.spare_explain = spare_explain;
    }

    public String getSpare_explain() 
    {
        return spare_explain;
    }
    public void setSpare_number(Integer spare_number) 
    {
        this.spare_number = spare_number;
    }

    public Integer getSpare_number() 
    {
        return spare_number;
    }
    public void setCreated_at(Long created_at) 
    {
        this.created_at = created_at;
    }

    public Long getCreated_at() 
    {
        return created_at;
    }
    public void setUpdated_at(Long updated_at) 
    {
        this.updated_at = updated_at;
    }

    public Long getUpdated_at() 
    {
        return updated_at;
    }
    public void setService_id(Long service_id) 
    {
        this.service_id = service_id;
    }

    public Long getService_id() 
    {
        return service_id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("spare_id", getSpare_id())
            .append("spare_explain", getSpare_explain())
            .append("spare_number", getSpare_number())
            .append("created_at", getCreated_at())
            .append("updated_at", getUpdated_at())
            .append("service_id", getService_id())
            .toString();
    }
}
