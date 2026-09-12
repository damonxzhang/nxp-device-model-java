package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * app消息对象 nxp_app_message
 * 
 * @author YanCheng Liu
 * @date 2021-02-07
 */
public class NxpAppMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    public Long getPm_id() {
        return pm_id;
    }

    public void setPm_id(Long pm_id) {
        this.pm_id = pm_id;
    }

    private Long pm_id;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String typeName;

    /** 关联设备ID */
    @Excel(name = "关联设备ID")
    private Long facility_id;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String comment;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long user_id;

    public Long getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(Long create_user_id) {
        this.create_user_id = create_user_id;
    }

    private Long create_user_id;

    public Long getInspection_id() {
        return inspection_id;
    }

    public void setInspection_id(Long inspection_id) {
        this.inspection_id = inspection_id;
    }

    private Long inspection_id;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String del;

    /** 关联问题ID */
    @Excel(name = "关联问题ID")
    private Long type_id;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private String created_at;

    private String facility_name;

    private String head_user;
    /**
     * 工序
     */
    private String process;

    private String facility_number;

    private Integer pageNum;
    private Integer pageSize;
    /**
     * 是否接收 0-已接收 1-未接收
     */
    private Integer is_receive;
    /**
     * 批量更新消息已读ids
     */
    private String ids;

    private List<String> idList;
    /**
     * 检查id
     */
    private Long check_id;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setFacility_id(Long facility_id) 
    {
        this.facility_id = facility_id;
    }

    public Long getFacility_id() 
    {
        return facility_id;
    }
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }
    public void setUser_id(Long user_id) 
    {
        this.user_id = user_id;
    }

    public Long getUser_id() 
    {
        return user_id;
    }
    public void setDel(String del) 
    {
        this.del = del;
    }

    public String getDel() 
    {
        return del;
    }
    public void setType_id(Long type_id) 
    {
        this.type_id = type_id;
    }

    public Long getType_id() 
    {
        return type_id;
    }
    public void setCreated_at(String created_at) 
    {
        this.created_at = created_at;
    }

    public String getCreated_at() 
    {
        return created_at;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("status", getStatus())
            .append("type", getType())
            .append("facility_id", getFacility_id())
            .append("pm_id", getPm_id())
            .append("comment", getComment())
            .append("create_user_id", getCreate_user_id())
            .append("inspection_id", getInspection_id())
            .append("user_id", getUser_id())
            .append("del", getDel())
            .append("type_id", getType_id())
            .append("created_at", getCreated_at())
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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getFacility_number() {
        return facility_number;
    }

    public void setFacility_number(String facility_number) {
        this.facility_number = facility_number;
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

    public Integer getIs_receive() {
        return is_receive;
    }

    public void setIs_receive(Integer is_receive) {
        this.is_receive = is_receive;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public Long getCheck_id() {
        return check_id;
    }

    public void setCheck_id(Long check_id) {
        this.check_id = check_id;
    }
}
