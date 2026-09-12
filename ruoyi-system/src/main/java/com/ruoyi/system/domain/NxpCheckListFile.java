package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检查列表文件对象 nxp_check_list_file
 * 
 * @author YanCheng Liu
 * @date 2022-09-04
 */
public class NxpCheckListFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 检查列表id */
    @Excel(name = "检查列表id")
    private Long checkListId;

    /** 路径 */
    @Excel(name = "路径")
    private String filePath;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCheckListId(Long checkListId) 
    {
        this.checkListId = checkListId;
    }

    public Long getCheckListId() 
    {
        return checkListId;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkListId", getCheckListId())
            .append("filePath", getFilePath())
            .append("createTime", getCreateTime())
            .toString();
    }
}
