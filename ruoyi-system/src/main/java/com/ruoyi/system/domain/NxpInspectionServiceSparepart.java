package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维修设备备件对象 nxp_inspection_service_sparepart
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public class NxpInspectionServiceSparepart extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 维修ID */
    @Excel(name = "维修ID")
    private Long inspection_service_id;

    /** 备件ID */
    @Excel(name = "备件ID")
    private String s_id;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String e_id;

    /** 备件领取数量 */
    @Excel(name = "备件领取数量")
    private Integer o_amount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInspection_service_id(Long inspection_service_id) 
    {
        this.inspection_service_id = inspection_service_id;
    }

    public Long getInspection_service_id() 
    {
        return inspection_service_id;
    }
    public void setS_id(String s_id) 
    {
        this.s_id = s_id;
    }

    public String getS_id() 
    {
        return s_id;
    }
    public void setE_id(String e_id) 
    {
        this.e_id = e_id;
    }

    public String getE_id() 
    {
        return e_id;
    }
    public void setO_amount(Integer o_amount) 
    {
        this.o_amount = o_amount;
    }

    public Integer getO_amount() 
    {
        return o_amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspection_service_id", getInspection_service_id())
            .append("s_id", getS_id())
            .append("e_id", getE_id())
            .append("o_amount", getO_amount())
            .toString();
    }
}
