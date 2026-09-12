package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具部件管理对象 nxp_mold_component
 * 
 * @author YanCheng Liu
 * @date 2023-06-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldComponent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模具id */
    @Excel(name = "模具id")
    private Long moldId;

    /** 模具编号 */
    @Excel(name = "模具编号")
    private String moldCode;

    /** 部件名称 */
    @Excel(name = "部件名称")
    private String componentName;

    /** 状态 1-正常2-异常3-缺件 */
    @Excel(name = "状态 1-正常2-异常3-缺件")
    private Integer status;
    /**
     * ids
     */
    private String ids;
    /**
     * 模具种类id
     */
    private Long moldTypeId;
    /**
     * 模具种类名称
     */
    private String moldTypeName;
    /**
     * 模具rmsCode
     */
    private String moldRmsCode;

    private Long componentId;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private Integer moldType;
}
