package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户权限标签对象 nxp_user_label
 * 
 * @author YanCheng Liu
 * @date 2023-08-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpUserLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userInfoId;

    /** 中文名称 */
    @Excel(name = "中文名称")
    private String chineseName;

    /** 人员类型 */
    @Excel(name = "人员类型")
    private Integer isPm;

    /** 所属模具类型 */
    @Excel(name = "所属模具类型")
    private Integer moldType;

    /** 职位 1.操作员2.PM3.模具维护人员4.管理员 */
    @Excel(name = "职位")
    private Integer position;
}
