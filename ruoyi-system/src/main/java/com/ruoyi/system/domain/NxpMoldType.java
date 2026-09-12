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
 * 部件种类对象 nxp_mold_type
 * 
 * @author YanCheng Liu
 * @date 2023-10-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NxpMoldType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模具种类名称 */
    @Excel(name = "模具种类名称")
    private String moldTypeName;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 适用机型 */
    private String machineModel;

    /** 模具类型 1-SAW 2-BA */
    private Integer moldType;

    private String machineModelName;

    /** 模具部件信息 */
    private List<NxpMoldTypeComponent> nxpMoldTypeComponentList;

}
