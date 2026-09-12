package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 模具&产品类型对象 nxp_mold_product
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模具id */
    @Excel(name = "模具id")
    private Long moldId;

    /** 模具名称 */
    @Excel(name = "模具名称")
    private String moldName;

    /** 产品类型id */
    @Excel(name = "产品类型id")
    private Long productTypeId;

    /** 产品类型名称 */
    @Excel(name = "产品类型名称")
    private String productTypeName;

    private List<String> productTypeList;
}
