package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绑定关系管理对象 nxp_bind_relationship
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
public class NxpBindRelationship extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 检查内容id */
    @Excel(name = "检查内容id")
    private Long checkContentId;

    /** 维修位置id */
    @Excel(name = "维修位置id")
    private Long positionId;

    /** 维修内容id */
    @Excel(name = "维修内容id")
    private Long inspectionContentId;

    /** 维修内容状态 */
    @Excel(name = "维修内容状态")
    private String inspectionContentState;

    /** pm分组id */
    @Excel(name = "pm分组id")
    private Long pmGroupId;

    /** pm检查项id */
    @Excel(name = "pm检查项id")
    private Long pmGroupItemId;

    /** pm检查项状态 */
    @Excel(name = "pm检查项状态")
    private String pmGroupItemState;

    /** 作业内容id */
    @Excel(name = "作业内容id")
    private Long swcJobContentId;

    /** swc作业内容状态 */
    @Excel(name = "swc作业内容状态")
    private String swcJobContentState;

    private String checkContentName;

    private String positionName;

    private String inspectionContentName;

    private String pmGroupName;

    private String pmGroupItemName;

    private String swcJobContentName;

    private Long checkGroupId;

    private String checkGroupName;

    public void setCheckContentId(Long checkContentId) 
    {
        this.checkContentId = checkContentId;
    }

    public Long getCheckContentId() 
    {
        return checkContentId;
    }
    public void setPositionId(Long positionId) 
    {
        this.positionId = positionId;
    }

    public Long getPositionId() 
    {
        return positionId;
    }
    public void setInspectionContentId(Long inspectionContentId) 
    {
        this.inspectionContentId = inspectionContentId;
    }

    public Long getInspectionContentId() 
    {
        return inspectionContentId;
    }
    public void setInspectionContentState(String inspectionContentState) 
    {
        this.inspectionContentState = inspectionContentState;
    }

    public String getInspectionContentState() 
    {
        return inspectionContentState;
    }
    public void setPmGroupId(Long pmGroupId) 
    {
        this.pmGroupId = pmGroupId;
    }

    public Long getPmGroupId() 
    {
        return pmGroupId;
    }
    public void setPmGroupItemId(Long pmGroupItemId) 
    {
        this.pmGroupItemId = pmGroupItemId;
    }

    public Long getPmGroupItemId() 
    {
        return pmGroupItemId;
    }
    public void setPmGroupItemState(String pmGroupItemState) 
    {
        this.pmGroupItemState = pmGroupItemState;
    }

    public String getPmGroupItemState() 
    {
        return pmGroupItemState;
    }
    public void setSwcJobContentId(Long swcJobContentId) 
    {
        this.swcJobContentId = swcJobContentId;
    }

    public Long getSwcJobContentId() 
    {
        return swcJobContentId;
    }
    public void setSwcJobContentState(String swcJobContentState) 
    {
        this.swcJobContentState = swcJobContentState;
    }

    public String getSwcJobContentState() 
    {
        return swcJobContentState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("checkContentId", getCheckContentId())
            .append("positionId", getPositionId())
            .append("inspectionContentId", getInspectionContentId())
            .append("inspectionContentState", getInspectionContentState())
            .append("pmGroupId", getPmGroupId())
            .append("pmGroupItemId", getPmGroupItemId())
            .append("pmGroupItemState", getPmGroupItemState())
            .append("swcJobContentId", getSwcJobContentId())
            .append("swcJobContentState", getSwcJobContentState())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getCheckContentName() {
        return checkContentName;
    }

    public void setCheckContentName(String checkContentName) {
        this.checkContentName = checkContentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getInspectionContentName() {
        return inspectionContentName;
    }

    public void setInspectionContentName(String inspectionContentName) {
        this.inspectionContentName = inspectionContentName;
    }

    public String getPmGroupName() {
        return pmGroupName;
    }

    public void setPmGroupName(String pmGroupName) {
        this.pmGroupName = pmGroupName;
    }

    public String getPmGroupItemName() {
        return pmGroupItemName;
    }

    public void setPmGroupItemName(String pmGroupItemName) {
        this.pmGroupItemName = pmGroupItemName;
    }

    public String getSwcJobContentName() {
        return swcJobContentName;
    }

    public void setSwcJobContentName(String swcJobContentName) {
        this.swcJobContentName = swcJobContentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckGroupId() {
        return checkGroupId;
    }

    public void setCheckGroupId(Long checkGroupId) {
        this.checkGroupId = checkGroupId;
    }

    public String getCheckGroupName() {
        return checkGroupName;
    }

    public void setCheckGroupName(String checkGroupName) {
        this.checkGroupName = checkGroupName;
    }
}
