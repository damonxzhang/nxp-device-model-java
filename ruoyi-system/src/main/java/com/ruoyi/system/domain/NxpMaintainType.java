package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备问题类型对象 nxp_maintain_type
 * 
 * @author YanCheng Liu
 * @date 2021-03-04
 */
public class NxpMaintainType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long create_user;

    /** 创建时间 */
    private Long create_time;

    /** 更新时间 */
    private Long update_time;

    /** 删除时间 */
    @Excel(name = "删除时间")
    private Long delete_time;

    /** 主键 */
    private Long id;

    /** 区域 */
    @Excel(name = "区域")
    private Long area;

    /** 工序 */
    @Excel(name = "工序")
    private Long process;

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setCreate_user(Long create_user) 
    {
        this.create_user = create_user;
    }

    public Long getCreate_user() 
    {
        return create_user;
    }
    public void setCreate_time(Long create_time) 
    {
        this.create_time = create_time;
    }

    public Long getCreate_time() 
    {
        return create_time;
    }
    public void setUpdate_time(Long update_time) 
    {
        this.update_time = update_time;
    }

    public Long getUpdate_time() 
    {
        return update_time;
    }
    public void setDelete_time(Long delete_time) 
    {
        this.delete_time = delete_time;
    }

    public Long getDelete_time() 
    {
        return delete_time;
    }
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setArea(Long area) 
    {
        this.area = area;
    }

    public Long getArea() 
    {
        return area;
    }
    public void setProcess(Long process) 
    {
        this.process = process;
    }

    public Long getProcess() 
    {
        return process;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("status", getStatus())
            .append("create_user", getCreate_user())
            .append("create_time", getCreate_time())
            .append("update_time", getUpdate_time())
            .append("delete_time", getDelete_time())
            .append("id", getId())
            .append("area", getArea())
            .append("process", getProcess())
            .toString();
    }
}
