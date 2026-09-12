package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 待处理设备对象 nxp_inspection
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public class NxpInspection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备表id */
    @Excel(name = "设备表id")
    private Long facility_id;

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    private Long area;

    /** 维修状态 */
    @Excel(name = "维修状态")
    private Integer status;

    /** 设备相关人 */
    @Excel(name = "设备相关人")
    private String relevant_people;

    /** 报修时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报修时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date repair_time;

    /** 情况说明 */
    @Excel(name = "情况说明")
    private String explanation;

    /** 报修拍照说明图片id */
    @Excel(name = "报修拍照说明图片id")
    private String repair_file_id;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long created_at;

    public Date getCreated_time() {
        return created_time;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    private String totalTime;
    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    private Date created_time;

    /** 修改时间 */
    @Excel(name = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long updated_at;

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }

    private Date updated_time;

    /** 后续操作 */
    @Excel(name = "后续操作")
    private Integer operate;

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    private String operateName;

    /** 问题类型 */
    @Excel(name = "问题类型")
    private Integer question_type;

    /** 提建议人id */
    @Excel(name = "提建议人id")
    private Long user_info_id;

    public String getUser_info_name() {
        return user_info_name;
    }

    public void setUser_info_name(String user_info_name) {
        this.user_info_name = user_info_name;
    }

    private String user_info_name;

    /** 关联批号 */
    @Excel(name = "关联批号")
    private String part_no;
    /** 是否置顶 */
    @Excel(name = "是否置顶 0-置顶 1-非置顶")
    private Integer is_top;
    /**
     * 设备名称
     */
    private String facility_name;
    /**
     * 设备负责人姓名
     */
    private String head_user;
    /**
     * 图片地址集合
     */
    private List<String> repair_file_paths;
    /**
     * 图片id
     */
    private String pic_id;

    public String getRepair_file_path() {
        return repair_file_path;
    }

    public void setRepair_file_path(String repair_file_path) {
        this.repair_file_path = repair_file_path;
    }

    private String repair_file_path;

    private Integer pageNum;

    private Integer pageSize;
    /**
     * 开始时间
     */
    private String repair_start_time;
    /**
     * 结束时间
     */
    private String repair_end_time;

    private Long user_id;

    private String relevant_people_name;

    private String questionTypeName;
    /**
     * 设备编号
     */
    private String facilityNumber;
    /**
     * 设备工序
     */
    private String facilityProcess;
    /**
     * 设备工序id
     */
    private Long process_id;
    /**
     * 报修id
     */
    private String inspection_id;
    /**
     * 无需维修原因id
     */
    private Long maintenance_reason_id;
    /**
     * 无需维修原因其他
     */
    private String maintenance_reason_name;

    private String inspection_service_id;
    /**
     * 状态-中文
     */
    private String statusName;
    /**
     * 设备最新维修记录-发送邮件使用
     */
    private NxpInspectionService nxpInspectionService;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFacility_id(Long facility_id) 
    {
        this.facility_id = facility_id;
    }

    public Long getFacility_id() 
    {
        return facility_id;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setRelevant_people(String relevant_people) 
    {
        this.relevant_people = relevant_people;
    }

    public String getRelevant_people() 
    {
        return relevant_people;
    }
    public void setRepair_time(Date repair_time) 
    {
        this.repair_time = repair_time;
    }

    public Date getRepair_time() 
    {
        return repair_time;
    }
    public void setExplanation(String explanation) 
    {
        this.explanation = explanation;
    }

    public String getExplanation() 
    {
        return explanation;
    }
    public void setRepair_file_id(String repair_file_id) 
    {
        this.repair_file_id = repair_file_id;
    }

    public String getRepair_file_id() 
    {
        return repair_file_id;
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
    public void setOperate(Integer operate) 
    {
        this.operate = operate;
    }

    public Integer getOperate() 
    {
        return operate;
    }
    public void setQuestion_type(Integer question_type) 
    {
        this.question_type = question_type;
    }

    public Integer getQuestion_type() 
    {
        return question_type;
    }
    public void setUser_info_id(Long user_info_id) 
    {
        this.user_info_id = user_info_id;
    }

    public Long getUser_info_id() 
    {
        return user_info_id;
    }
    public void setPart_no(String part_no) 
    {
        this.part_no = part_no;
    }

    public String getPart_no() 
    {
        return part_no;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("facility_id", getFacility_id())
            .append("status", getStatus())
            .append("relevant_people", getRelevant_people())
            .append("repair_time", getRepair_time())
            .append("explanation", getExplanation())
            .append("repair_file_id", getRepair_file_id())
            .append("created_at", getCreated_at())
            .append("updated_at", getUpdated_at())
            .append("operate", getOperate())
            .append("question_type", getQuestion_type())
            .append("user_info_id", getUser_info_id())
            .append("part_no", getPart_no())
            .append("head_user", getHead_user())
            .toString();
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

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getRelevant_people_name() {
        return relevant_people_name;
    }

    public void setRelevant_people_name(String relevant_people_name) {
        this.relevant_people_name = relevant_people_name;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public String getFacilityNumber() {
        return facilityNumber;
    }

    public void setFacilityNumber(String facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public String getFacilityProcess() {
        return facilityProcess;
    }

    public void setFacilityProcess(String facilityProcess) {
        this.facilityProcess = facilityProcess;
    }

    public Long getProcess_id() {
        return process_id;
    }

    public void setProcess_id(Long process_id) {
        this.process_id = process_id;
    }

    public String getInspection_id() {
        return inspection_id;
    }

    public void setInspection_id(String inspection_id) {
        this.inspection_id = inspection_id;
    }

    public Integer getIs_top() {
        return is_top;
    }

    public void setIs_top(Integer is_top) {
        this.is_top = is_top;
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

    public String getInspection_service_id() {
        return inspection_service_id;
    }

    public void setInspection_service_id(String inspection_service_id) {
        this.inspection_service_id = inspection_service_id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public NxpInspectionService getNxpInspectionService() {
        return nxpInspectionService;
    }

    public void setNxpInspectionService(NxpInspectionService nxpInspectionService) {
        this.nxpInspectionService = nxpInspectionService;
    }
}
