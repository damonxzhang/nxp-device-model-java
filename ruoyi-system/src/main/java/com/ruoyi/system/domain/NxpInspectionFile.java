package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维修上传图片对象 nxp_inspection_file
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public class NxpInspectionFile
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String file_path;

    /** 创建时间 */
    private Date create_time;

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
    public void setCreate_time(Date create_time) 
    {
        this.create_time = create_time;
    }

    public Date getCreate_time() 
    {
        return create_time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("file_path", getFile_path())
            .append("create_time", getCreate_time())
            .toString();
    }
}
