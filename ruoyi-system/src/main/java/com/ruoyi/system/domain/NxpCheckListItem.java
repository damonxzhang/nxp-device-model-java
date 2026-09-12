package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 检查列表检查项对象 nxp_check_list_item
 * 
 * @author YanCheng Liu
 * @date 2022-10-02
 */
public class NxpCheckListItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 检查id */
    @Excel(name = "检查id")
    private Long checkListId;

    /** 是否检查 */
    @Excel(name = "是否检查")
    private String isCheck;

    /** 检查内容id */
    @Excel(name = "检查内容id")
    private Long checkProjectId;

    /** 检查人id */
    @Excel(name = "检查人id")
    private Long checkUserId;

    /** 检查人姓名 */
    @Excel(name = "检查人姓名")
    private String checkUserName;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String checkContent;

    /** 图片id */
    @Excel(name = "图片id")
    private String files;

    private List<String> filePaths;
    private List<NxpCheckListFile> nxpCheckListFiles;

    private String checkProjectName;

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
    public void setIsCheck(String isCheck) 
    {
        this.isCheck = isCheck;
    }

    public String getIsCheck() 
    {
        return isCheck;
    }
    public void setCheckProjectId(Long checkProjectId) 
    {
        this.checkProjectId = checkProjectId;
    }

    public Long getCheckProjectId() 
    {
        return checkProjectId;
    }
    public void setCheckUserId(Long checkUserId) 
    {
        this.checkUserId = checkUserId;
    }

    public Long getCheckUserId() 
    {
        return checkUserId;
    }
    public void setCheckUserName(String checkUserName) 
    {
        this.checkUserName = checkUserName;
    }

    public String getCheckUserName() 
    {
        return checkUserName;
    }
    public void setCheckContent(String checkContent) 
    {
        this.checkContent = checkContent;
    }

    public String getCheckContent() 
    {
        return checkContent;
    }
    public void setFiles(String files) 
    {
        this.files = files;
    }

    public String getFiles() 
    {
        return files;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("checkListId", getCheckListId())
            .append("isCheck", getIsCheck())
            .append("checkProjectId", getCheckProjectId())
            .append("checkUserId", getCheckUserId())
            .append("checkUserName", getCheckUserName())
            .append("checkContent", getCheckContent())
            .append("files", getFiles())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getCheckProjectName() {
        return checkProjectName;
    }

    public void setCheckProjectName(String checkProjectName) {
        this.checkProjectName = checkProjectName;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public List<NxpCheckListFile> getNxpCheckListFiles() {
        return nxpCheckListFiles;
    }

    public void setNxpCheckListFiles(List<NxpCheckListFile> nxpCheckListFiles) {
        this.nxpCheckListFiles = nxpCheckListFiles;
    }
}
