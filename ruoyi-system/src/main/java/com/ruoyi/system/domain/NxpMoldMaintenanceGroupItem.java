package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具检查项对象 nxp_mold_maintenance_group_item
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenanceGroupItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long groupItemId;

    /** 分组id */
    @Excel(name = "分组id")
    private Long groupId;

    /** 检查项 */
    @Excel(name = "检查项")
    private String itemName;

    /** 状态 1:启用 2:禁用 */
    @Excel(name = "状态 1:启用 2:禁用")
    private Integer itemStatus;

    /** 是否拍照 0-是 1否 */
    @Excel(name = "是否拍照 0-是 1否")
    private Integer isPhotograph;

    private String fileId;

    /** 检查项结果：1：正常；2：异常 */
    private Integer itemResult;

    private String groupName;

    private Long moldTypeId;
    private Long componentId;

    private String moldTypeName;

    private String componentName;

    /** 模具类型 1-SAW 2-BA */
    private Integer moldType;
}
