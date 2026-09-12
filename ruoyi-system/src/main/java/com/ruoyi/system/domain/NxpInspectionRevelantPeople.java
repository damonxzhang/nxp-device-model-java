package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备相关人员对象 nxp_inspection_revelant_people
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public class NxpInspectionRevelantPeople extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** inspection id */
    @Excel(name = "inspection id")
    private Long inspection_id;

    /** 设备id */
    @Excel(name = "设备id")
    private Long facility_id;

    /** 设备相关人 */
    @Excel(name = "设备相关人")
    private Long revelant_people;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInspection_id(Long inspection_id) 
    {
        this.inspection_id = inspection_id;
    }

    public Long getInspection_id() 
    {
        return inspection_id;
    }
    public void setFacility_id(Long facility_id) 
    {
        this.facility_id = facility_id;
    }

    public Long getFacility_id() 
    {
        return facility_id;
    }
    public void setRevelant_people(Long revelant_people) 
    {
        this.revelant_people = revelant_people;
    }

    public Long getRevelant_people() 
    {
        return revelant_people;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspection_id", getInspection_id())
            .append("facility_id", getFacility_id())
            .append("revelant_people", getRevelant_people())
            .toString();
    }
}
