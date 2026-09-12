package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * pm图片对象 nxp_inspection_pm_file
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public class NxpInspectionPmFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 文件存储路径 */
    @Excel(name = "文件存储路径")
    private String file_path;

    /** pmId */
    @Excel(name = "pmId")
    private Long pm_id;

    /** pm_check_id */
    @Excel(name = "pm_check_id")
    private Long pm_check_id;

    /** 类型 */
    @Excel(name = "类型")
    private Long type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFile_path(String file_path) 
    {
        this.file_path = file_path;
    }

    public String getFile_path() 
    {
        return file_path;
    }
    public void setPm_id(Long pm_id) 
    {
        this.pm_id = pm_id;
    }

    public Long getPm_id() 
    {
        return pm_id;
    }
    public void setPm_check_id(Long pm_check_id) 
    {
        this.pm_check_id = pm_check_id;
    }

    public Long getPm_check_id() 
    {
        return pm_check_id;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("file_path", getFile_path())
            .append("createTime", getCreateTime())
            .append("pm_id", getPm_id())
            .append("pm_check_id", getPm_check_id())
            .append("type", getType())
            .toString();
    }
}
