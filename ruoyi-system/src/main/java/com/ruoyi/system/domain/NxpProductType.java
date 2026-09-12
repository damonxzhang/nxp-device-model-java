package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品类型对象 nxp_product_type
 * 
 * @author YanCheng Liu
 * @date 2023-06-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpProductType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 产品类型名称 */
    @Excel(name = "产品类型名称")
    private String productTypeName;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;

    /** 模具类型 1-SAW 2-BA */
    @Excel(name = "模具类型 1-SAW 2-BA")
    private Integer moldType;
}
