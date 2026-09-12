package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 检查记录对象 nxp_check_list
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
public class NxpCheckList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 工序id */
    private Long processId;

    /** 设备表id */
    private Long facilityId;

    /** 提交人id */
    private Long submitterId;

    /** 提交人姓名 */
    @Excel(name = "提交人姓名")
    private String submitterName;

    /** 类型 */
    @Excel(name = "类型")
    private Long type;

    /** 状态 */
    @Excel(name = "状态")
    private Long state;

    /** 提交内容 */
    @Excel(name = "提交内容")
    private String content;

    /** 报修id */
    private Long inspectionId;

    /** 维修详情id */
    private Long inspectionServiceId;

    /** 维修内容id */
    private Long inspectionContentId;

    /** pmId */
    private Long pmId;

    /** 作业内容Id */
    private Long swcJobContentId;

    /** 作业内容 */
    private String swcJobContentStr;

    /** pm分组id */
    private Long pmGroupId;

    /** pm检查项id */
    private Long pmGroupItemId;

    /** 维修内容 */
    private String inspectionContentStr;

    /** 检查项内容 */
    private String pmGroupItemStr;

    /** 是否检查 */
    private String isCheck;

    /** 检查人姓名 */
    private String checkUserName;

    /** 检查人id */
    private Long checkUserId;

    /** 设备编号 */
    private String facilityNumber;

    private String checkContent;

    private String files;

    private Integer pageNum;
    private Integer pageSize;

    private String processName;
    private String facilityName;

    private Integer searchTime;

    private List<NxpCheckListItem> checks;

    private List<String> filePaths;

    private String checkProjectId;
    /**
     * 班别 1: A班  2 : B班  3 : C班  4 : D班  5: R班  6 : AB班  7 : CD班
     */
    private Integer shift;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setFacilityId(Long facilityId) 
    {
        this.facilityId = facilityId;
    }

    public Long getFacilityId() 
    {
        return facilityId;
    }
    public void setSubmitterId(Long submitterId) 
    {
        this.submitterId = submitterId;
    }

    public Long getSubmitterId() 
    {
        return submitterId;
    }
    public void setSubmitterName(String submitterName) 
    {
        this.submitterName = submitterName;
    }

    public String getSubmitterName() 
    {
        return submitterName;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setInspectionId(Long inspectionId) 
    {
        this.inspectionId = inspectionId;
    }

    public Long getInspectionId() 
    {
        return inspectionId;
    }
    public void setInspectionServiceId(Long inspectionServiceId) 
    {
        this.inspectionServiceId = inspectionServiceId;
    }

    public Long getInspectionServiceId() 
    {
        return inspectionServiceId;
    }
    public void setInspectionContentId(Long inspectionContentId) 
    {
        this.inspectionContentId = inspectionContentId;
    }

    public Long getInspectionContentId() 
    {
        return inspectionContentId;
    }
    public void setPmId(Long pmId) 
    {
        this.pmId = pmId;
    }

    public Long getPmId() 
    {
        return pmId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processId", getProcessId())
            .append("facilityId", getFacilityId())
            .append("submitterId", getSubmitterId())
            .append("submitterName", getSubmitterName())
            .append("type", getType())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("content", getContent())
            .append("inspectionId", getInspectionId())
            .append("inspectionServiceId", getInspectionServiceId())
            .append("inspectionContentId", getInspectionContentId())
            .append("pmId", getPmId())
            .toString();
    }

    public Long getSwcJobContentId() {
        return swcJobContentId;
    }

    public void setSwcJobContentId(Long swcJobContentId) {
        this.swcJobContentId = swcJobContentId;
    }

    public String getSwcJobContentStr() {
        return swcJobContentStr;
    }

    public void setSwcJobContentStr(String swcJobContentStr) {
        this.swcJobContentStr = swcJobContentStr;
    }

    public Long getPmGroupId() {
        return pmGroupId;
    }

    public void setPmGroupId(Long pmGroupId) {
        this.pmGroupId = pmGroupId;
    }

    public Long getPmGroupItemId() {
        return pmGroupItemId;
    }

    public void setPmGroupItemId(Long pmGroupItemId) {
        this.pmGroupItemId = pmGroupItemId;
    }

    public String getInspectionContentStr() {
        return inspectionContentStr;
    }

    public void setInspectionContentStr(String inspectionContentStr) {
        this.inspectionContentStr = inspectionContentStr;
    }

    public String getPmGroupItemStr() {
        return pmGroupItemStr;
    }

    public void setPmGroupItemStr(String pmGroupItemStr) {
        this.pmGroupItemStr = pmGroupItemStr;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Long getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Integer getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Integer searchTime) {
        this.searchTime = searchTime;
    }

    public List<NxpCheckListItem> getChecks() {
        return checks;
    }

    public void setChecks(List<NxpCheckListItem> checks) {
        this.checks = checks;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public String getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(String checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public String getFacilityNumber() {
        return facilityNumber;
    }

    public void setFacilityNumber(String facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }
}
