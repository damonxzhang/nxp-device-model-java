package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * APP反馈对象 nxp_inspection_feedback
 * 
 * @author DamonZhang
 * @date 2021-04-28
 */
public class NxpInspectionFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    public Long getUserShiftId() {
        return userShiftId;
    }

    public void setUserShiftId(Long userShiftId) {
        this.userShiftId = userShiftId;
    }

    private Long userShiftId;

    public String getUserShift() {
        return userShift;
    }

    public void setUserShift(String userShift) {
        this.userShift = userShift;
    }

    private String userShift;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String feedback;

    public String getFeedback_start_time() {
        return feedback_start_time;
    }

    public void setFeedback_start_time(String feedback_start_time) {
        this.feedback_start_time = feedback_start_time;
    }

    private String feedback_start_time;

    public String getFeedback_end_time() {
        return feedback_end_time;
    }

    public void setFeedback_end_time(String feedback_end_time) {
        this.feedback_end_time = feedback_end_time;
    }

    private String feedback_end_time;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    private String created_at;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    private String userPhone;

    /** 状态：1：待接受；2：已接受；3：已更新；4：已拒绝 */
    @Excel(name = "状态：1：待接受；2：已接受；3：已更新；4：已拒绝")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setFeedback(String feedback) 
    {
        this.feedback = feedback;
    }

    public String getFeedback() 
    {
        return feedback;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("feedback", getFeedback())
            .append("status", getStatus())
            .toString();
    }
}
