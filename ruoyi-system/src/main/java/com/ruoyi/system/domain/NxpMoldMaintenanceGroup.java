package com.ruoyi.system.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具维护分组对象 nxp_mold_maintenance_group
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenanceGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String groupName;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 模具检查项信息 */
    private List<NxpMoldMaintenanceGroupItem> nxpMoldMaintenanceGroupItemList;

    /** 模具类型 1-SAW 2-BA */
    @Excel(name = "模具类型 1-SAW 2-BA")
    private Integer moldType;
}
