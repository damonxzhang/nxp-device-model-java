package com.ruoyi.system.domain;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户信息对象 nxp_user_info
 * 
 * @author YanCheng Liu
 * @date 2021-02-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 角色id */
    @Excel(name = "角色id")
    private Long role_id;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 直属经理的用户的ID */
    @Excel(name = "直属经理的用户的ID")
    private Long leader_uid;

    /** PID唯一标识 */
    @Excel(name = "PID唯一标识")
    private String pid;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String tel;

    /** 中文名称 */
    @Excel(name = "中文名称")
    private String chinese_name;

    /** 英文名称 */
    @Excel(name = "英文名称")
    private String english_name;

    /** 性别 */
    @Excel(name = "性别")
    private Integer sex;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 职务  1操作员  2带班   3产品工程师   4 设备工程师  5PM技术员   6PM leader   7生产 leader   8 分部经理  9部门经理 */
    @Excel(name = "职务  1操作员  2带班   3产品工程师   4 设备工程师  5PM技术员   6PM leader   7生产 leader   8 分部经理  9部门经理")
    private Integer position;

    /** 班别   1: A班  2 : B班  3 : C班  4 : D班  5: R班  6 : AB班  7 : CD班 */
    @Excel(name = "班别   1: A班  2 : B班  3 : C班  4 : D班  5: R班  6 : AB班  7 : CD班")
    private Integer shift;

    /** 区域  1 Non-lead   2 Leaded  3Plating */
    @Excel(name = "区域  1 Non-lead   2 Leaded  3Plating")
    private String area;

    /** 站别主键ID */
    @Excel(name = "站别主键ID")
    private Long station_id;

    /** SFC账号 */
    @Excel(name = "SFC账号")
    private String sfc_number;

    /** FSL工号 */
    @Excel(name = "FSL工号")
    private String fsl_number;

    /** 餐卡后五位 */
    @Excel(name = "餐卡后五位")
    private String meal_card_code;

    /** WBI code */
    @Excel(name = "WBI code")
    private String wbi_code;

    /** 识别证码 */
    @Excel(name = "识别证码")
    private String badge_code;

    /** 多技能工位 */
    @Excel(name = "多技能工位")
    private Long skill;

    /** 部门号 */
    @Excel(name = "部门号")
    private String department_number;

    /** 所属分部  1 Non-lead   2 Leaded   3 PM     4 Plating */
    @Excel(name = "所属分部  1 Non-lead   2 Leaded   3 PM     4 Plating")
    private String section;

    /** 创建时间 */
    private Date create_time;

    /** 更新时间 */
    private Date update_time;

    /** gemba权限 */
    @Excel(name = "gemba权限")
    private Long gemba_role;

    /** gemba小组 */
    @Excel(name = "gemba小组")
    private Long team;

    private boolean flag = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NxpUserInfo)) return false;
        NxpUserInfo that = (NxpUserInfo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
