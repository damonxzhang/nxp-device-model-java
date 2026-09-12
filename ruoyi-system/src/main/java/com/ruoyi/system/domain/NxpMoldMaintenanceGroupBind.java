package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具检查组绑定对象 nxp_mold_maintenance_group_bind
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenanceGroupBind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模具id */
    @Excel(name = "模具id")
    private Long moldId;

    /** 分组id */
    @Excel(name = "分组id")
    private Long moldMaintenanceGroupId;

    private String moldCode;

    private String groupName;

    /** 模具类型 1-SAW 2-BA */
    private Integer moldType;
}
