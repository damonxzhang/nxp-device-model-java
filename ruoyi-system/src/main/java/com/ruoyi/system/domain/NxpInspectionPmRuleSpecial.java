package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 周PM规则加日期对象 nxp_inspection_pm_rule_special
 * 
 * @author DamonZhang
 * @date 2021-06-28
 */
public class NxpInspectionPmRuleSpecial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 定时任务启动日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "定时任务启动日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    private Long dateTime;


    /** 向后跳跃天数 */
    @Excel(name = "向后跳跃天数")
    private Long day;

    /**  */
    private Long id;

    public void setDate(Date date) 
    {
        this.date = date;
    }

    public Date getDate() 
    {
        return date;
    }
    public void setDay(Long day) 
    {
        this.day = day;
    }

    public Long getDay() 
    {
        return day;
    }
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("date", getDate())
            .append("day", getDay())
            .append("id", getId())
            .toString();
    }
}
