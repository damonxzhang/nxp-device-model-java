package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * App版本对象 nxp_app_version
 * 
 * @author YanCheng Liu
 * @date 2021-03-23
 */
public class NxpAppVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 新版本名 */
    @Excel(name = "新版本名")
    private String newVersionName;

    /** 新版本号；每更新一个版本+1 */
    @Excel(name = "新版本号；每更新一个版本+1")
    private Long newVersionNumber;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Integer status;

    /** 版本说明 */
    @Excel(name = "版本说明")
    private String content;

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String remark;

    /** 软件下载地址 */
    @Excel(name = "软件下载地址")
    private String downloadUrl;

    /** 是否强制更新 0 不强制更新，1 强制更新 */
    @Excel(name = "是否强制更新 0 不强制更新，1 强制更新")
    private Integer forcedUpgrade;

    /** 是否更新 0 不更新，1更新 */
    @Excel(name = "是否更新 0 不更新，1更新")
    private Integer upgrade;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date upgradeTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNewVersionName(String newVersionName) 
    {
        this.newVersionName = newVersionName;
    }

    public String getNewVersionName() 
    {
        return newVersionName;
    }
    public void setNewVersionNumber(Long newVersionNumber) 
    {
        this.newVersionNumber = newVersionNumber;
    }

    public Long getNewVersionNumber() 
    {
        return newVersionNumber;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setDownloadUrl(String downloadUrl) 
    {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() 
    {
        return downloadUrl;
    }
    public void setForcedUpgrade(Integer forcedUpgrade) 
    {
        this.forcedUpgrade = forcedUpgrade;
    }

    public Integer getForcedUpgrade() 
    {
        return forcedUpgrade;
    }
    public void setUpgrade(Integer upgrade) 
    {
        this.upgrade = upgrade;
    }

    public Integer getUpgrade() 
    {
        return upgrade;
    }
    public void setUpgradeTime(Date upgradeTime) 
    {
        this.upgradeTime = upgradeTime;
    }

    public Date getUpgradeTime() 
    {
        return upgradeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("newVersionName", getNewVersionName())
            .append("newVersionNumber", getNewVersionNumber())
            .append("status", getStatus())
            .append("content", getContent())
            .append("remark", getRemark())
            .append("downloadUrl", getDownloadUrl())
            .append("forcedUpgrade", getForcedUpgrade())
            .append("upgrade", getUpgrade())
            .append("upgradeTime", getUpgradeTime())
            .toString();
    }
}
