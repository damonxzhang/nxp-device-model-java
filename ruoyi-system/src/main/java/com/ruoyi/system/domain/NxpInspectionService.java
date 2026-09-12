package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 维修详情对象 nxp_inspection_service
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public class NxpInspectionService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 报修表id */
    @Excel(name = "报修表id")
    private Long inspection_id;

    public String getInspection_info() {
        return inspection_info;
    }

    public void setInspection_info(String inspection_info) {
        this.inspection_info = inspection_info;
    }

    private String inspection_info;

    /** 维修内容表id */
    @Excel(name = "维修内容表id")
    private Long content_id;

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    private String contentName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    private String positionName;

    /** 维修位置表id */
    @Excel(name = "维修位置表id")
    private Long position_id;

    /** 维修人id */
    @Excel(name = "维修人id")
    private Long user_info_id;

    public String getUser_info_name() {
        return user_info_name;
    }

    public void setUser_info_name(String user_info_name) {
        this.user_info_name = user_info_name;
    }

    private String user_info_name;

    /** 维修情况说明 */
    @Excel(name = "维修情况说明")
    private String description;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long created_at;

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    private Date created_time;

    /** 修改时间 */
    @Excel(name = "修改时间")
    private Long updated_at;

    /** 维修时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "维修时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date service_time;

    /** 维修拍照id */
    @Excel(name = "维修拍照id")
    private String file_id;

    /** 维修状态 */
    @Excel(name = "维修状态")
    private Integer status;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long facility_id;

    /** 维修结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "维修结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date end_time;
    /**
     * 维修id等同于id
     */
    private String inspection_service_id;

    /**
     * 设备名称
     */
    private String facility_name;

    public String getFacility_model() {
        return facility_model;
    }

    public void setFacility_model(String facility_model) {
        this.facility_model = facility_model;
    }

    private String facility_model;
    /**
     * 设备负责人姓名
     */
    private String head_user;
    /**
     * 图片地址集合
     */
    private List<String> repair_file_paths;
    /**
     * 备件
     */
    private List<OutPut> sparepart_list;
    /**
     * 开始时间
     */
    private String repair_start_time;
    /**
     * 结束时间
     */
    private String repair_end_time;
    /**
     * 中止原因id
     */
    private Long abort_reason_id;
    /**
     * 其他原因
     */
    private String abort_reason_remark;
    /**
     * 无需维修原因id
     */
    private Long maintenance_reason_id;
    /**
     * 无需维修原因其他
     */
    private String maintenance_reason_name;
    /**
     * 是否插入到检查列表
     */
    private String isInsertCheckList;

    private String position;

    private String content;

    public String getRepair_time() {
        return repair_time;
    }

    public void setRepair_time(String repair_time) {
        this.repair_time = repair_time;
    }

    private String repair_time;

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    private String process_name;

    public String getProcess_id() {
        return process_id;
    }

    public void setProcess_id(String process_id) {
        this.process_id = process_id;
    }

    private String process_id;

    private List<NxpInspectionServiceSparepart> sparepart_List;

    private List<String> iIds;

    private Integer pageNum;
    private Integer pageSize;

    private String facility_user_name;

    private String part_no;
    /**
     * 维修时长-发送邮件使用
     */
    private String datePoor;

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
    public void setContent_id(Long content_id) 
    {
        this.content_id = content_id;
    }

    public Long getContent_id() 
    {
        return content_id;
    }
    public void setPosition_id(Long position_id) 
    {
        this.position_id = position_id;
    }

    public Long getPosition_id() 
    {
        return position_id;
    }
    public void setUser_info_id(Long user_info_id) 
    {
        this.user_info_id = user_info_id;
    }

    public Long getUser_info_id() 
    {
        return user_info_id;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCreated_at(Long created_at) 
    {
        this.created_at = created_at;
    }

    public Long getCreated_at() 
    {
        return created_at;
    }
    public void setUpdated_at(Long updated_at) 
    {
        this.updated_at = updated_at;
    }

    public Long getUpdated_at() 
    {
        return updated_at;
    }
    public void setService_time(Date service_time) 
    {
        this.service_time = service_time;
    }

    public Date getService_time() 
    {
        return service_time;
    }
    public void setFile_id(String file_id) 
    {
        this.file_id = file_id;
    }

    public String getFile_id() 
    {
        return file_id;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setFacility_id(Long facility_id) 
    {
        this.facility_id = facility_id;
    }

    public Long getFacility_id() 
    {
        return facility_id;
    }
    public void setEnd_time(Date end_time) 
    {
        this.end_time = end_time;
    }

    public Date getEnd_time() 
    {
        return end_time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspection_id", getInspection_id())
            .append("content_id", getContent_id())
            .append("position_id", getPosition_id())
            .append("user_info_id", getUser_info_id())
            .append("description", getDescription())
            .append("created_at", getCreated_at())
            .append("updated_at", getUpdated_at())
            .append("service_time", getService_time())
            .append("file_id", getFile_id())
            .append("status", getStatus())
            .append("facility_id", getFacility_id())
            .append("end_time", getEnd_time())
            .toString();
    }

    public String getInspection_service_id() {
        return inspection_service_id;
    }

    public void setInspection_service_id(String inspection_service_id) {
        this.inspection_service_id = inspection_service_id;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getHead_user() {
        return head_user;
    }

    public void setHead_user(String head_user) {
        this.head_user = head_user;
    }

    public List<String> getRepair_file_paths() {
        return repair_file_paths;
    }

    public void setRepair_file_paths(List<String> repair_file_paths) {
        this.repair_file_paths = repair_file_paths;
    }

    public List<OutPut> getSparepart_list() {
        return sparepart_list;
    }

    public void setSparepart_list(List<OutPut> sparepart_list) {
        this.sparepart_list = sparepart_list;
    }

    public String getRepair_start_time() {
        return repair_start_time;
    }

    public void setRepair_start_time(String repair_start_time) {
        this.repair_start_time = repair_start_time;
    }

    public String getRepair_end_time() {
        return repair_end_time;
    }

    public void setRepair_end_time(String repair_end_time) {
        this.repair_end_time = repair_end_time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<NxpInspectionServiceSparepart> getSparepart_List() {
        return sparepart_List;
    }

    public void setSparepart_List(List<NxpInspectionServiceSparepart> sparepart_List) {
        this.sparepart_List = sparepart_List;
    }

    public List<String> getiIds() {
        return iIds;
    }

    public void setiIds(List<String> iIds) {
        this.iIds = iIds;
    }

    public Long getAbort_reason_id() {
        return abort_reason_id;
    }

    public void setAbort_reason_id(Long abort_reason_id) {
        this.abort_reason_id = abort_reason_id;
    }

    public String getAbort_reason_remark() {
        return abort_reason_remark;
    }

    public void setAbort_reason_remark(String abort_reason_remark) {
        this.abort_reason_remark = abort_reason_remark;
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

    public String getFacility_user_name() {
        return facility_user_name;
    }

    public void setFacility_user_name(String facility_user_name) {
        this.facility_user_name = facility_user_name;
    }

    public String getPart_no() {
        return part_no;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }

    public Long getMaintenance_reason_id() {
        return maintenance_reason_id;
    }

    public void setMaintenance_reason_id(Long maintenance_reason_id) {
        this.maintenance_reason_id = maintenance_reason_id;
    }

    public String getMaintenance_reason_name() {
        return maintenance_reason_name;
    }

    public void setMaintenance_reason_name(String maintenance_reason_name) {
        this.maintenance_reason_name = maintenance_reason_name;
    }

    public String getDatePoor() {
        return datePoor;
    }

    public void setDatePoor(String datePoor) {
        this.datePoor = datePoor;
    }

    public String getIsInsertCheckList() {
        return isInsertCheckList;
    }

    public void setIsInsertCheckList(String isInsertCheckList) {
        this.isInsertCheckList = isInsertCheckList;
    }
}
