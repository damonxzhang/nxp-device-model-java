package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * CAR扣分对象 nxp_swc_car
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
@Data
public class NxpSwcCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** gemba小组 */
    @Excel(name = "gemba小组")
    private Long gembaGroupId;

    /** 年份 */
    @Excel(name = "年份")
    private Long year;

    /** 月份 */
    @Excel(name = "月份")
    private Long month;

    /** car扣分 */
    @Excel(name = "car扣分")
    private Double car;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    private String gembaName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGembaGroupId(Long gembaGroupId) 
    {
        this.gembaGroupId = gembaGroupId;
    }

    public Long getGembaGroupId() 
    {
        return gembaGroupId;
    }
    public void setYear(Long year) 
    {
        this.year = year;
    }

    public Long getYear() 
    {
        return year;
    }
    public void setMonth(Long month) 
    {
        this.month = month;
    }

    public Long getMonth() 
    {
        return month;
    }
    public void setCar(Double car)
    {
        this.car = car;
    }

    public Double getCar()
    {
        return car;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gembaGroupId", getGembaGroupId())
            .append("year", getYear())
            .append("month", getMonth())
            .append("car", getCar())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
