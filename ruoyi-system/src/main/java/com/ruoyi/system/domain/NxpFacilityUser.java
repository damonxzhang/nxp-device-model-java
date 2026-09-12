package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 设备相关人员对象 nxp_facility_user
 * 
 * @author YanCheng Liu
 * @date 2021-04-09
 */
public class NxpFacilityUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long facilityId;

    public Long getProcessOwner() {
        return processOwner;
    }

    public void setProcessOwner(Long processOwner) {
        this.processOwner = processOwner;
    }

    private Long processOwner;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    private String userIds;

    private String facilityName;

    public String getFacilityProcess() {
        return facilityProcess;
    }

    public void setFacilityProcess(String facilityProcess) {
        this.facilityProcess = facilityProcess;
    }

    private String facilityProcess;

    private String userName;

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    private String userNames;

    private Integer area;
    private Integer shift;

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    private Integer processId;

    private String users;

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFacilityId(Long facilityId) 
    {
        this.facilityId = facilityId;
    }

    public Long getFacilityId() 
    {
        return facilityId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("facilityId", getFacilityId())
            .append("userId", getUserId())
            .append("processId", getProcessId())
            .toString();
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    private Integer isMenu;

    public Integer getIsMe() {
        return isMe;
    }

    public void setIsMe(Integer isMe) {
        this.isMe = isMe;
    }

    private Integer isMe;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    private List<Integer> ids;


}
