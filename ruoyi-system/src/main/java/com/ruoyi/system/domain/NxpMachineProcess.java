package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工序对象 nxp_machine_process
 * 
 * @author YanCheng Liu
 * @date 2021-02-08
 */
public class NxpMachineProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    /** 负责人 */
    @Excel(name = "负责人")
    private Long owner;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String area;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /** 负责人姓名 */
    @Excel(name = "负责人姓名")
    private String ownerName;

    public void setCreate_dt(Date create_dt) {
        this.create_dt = create_dt;
    }

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date create_dt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date update_dt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public Date getCreate_dt() 
    {
        return create_dt;
    }
    public void setUpdate_dt(Date update_dt) 
    {
        this.update_dt = update_dt;
    }

    public Date getUpdate_dt() 
    {
        return update_dt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("status", getStatus())
            .append("owner", getOwner())
            .append("create_dt", getCreate_dt())
            .append("update_dt", getUpdate_dt())
            .toString();
    }
}
