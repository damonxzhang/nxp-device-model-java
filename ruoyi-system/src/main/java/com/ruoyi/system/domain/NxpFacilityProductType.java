package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备&产品类型对象 nxp_facility_product_type
 * 
 * @author YanCheng Liu
 * @date 2023-06-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpFacilityProductType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备表id */
    @Excel(name = "设备表id")
    private Long facilityId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String facilityNumber;

    /** 产品类型id */
//    @Excel(name = "产品类型id")
    private String productTypeId;

    /** 产品类型名称 */
    @Excel(name = "产品类型名称")
    private String productTypeName;

}
