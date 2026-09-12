package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具&检查组对象 nxp_group_mold
 * 
 * @author YanCheng Liu
 * @date 2023-10-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpGroupMold extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 检查组id */
    @Excel(name = "检查组id")
    private Long groupId;

    /** 检查组名称 */
    @Excel(name = "检查组名称")
    private String groupName;

    /** 模具id */
    @Excel(name = "模具id")
    private String moldIds;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    private String moldCode;

    /** 模具类型 1-SAW 2-BA */
    private Integer moldType;
}
