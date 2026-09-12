package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具部件对象 nxp_mold_type_component
 * 
 * @author YanCheng Liu
 * @date 2023-10-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NxpMoldTypeComponent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模具种类id */
    @Excel(name = "模具种类id")
    private Long moldTypeId;

    /** 部件名称 */
    @Excel(name = "部件名称")
    private String componentName;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 主键 */
    private Long componentId;
}
