package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 待归还模具对象 nxp_mold_return
 * 
 * @author YanCheng Liu
 * @date 2023-07-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldReturn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模具id */
    @Excel(name = "模具id")
    private Long moldId;

    /** 模具名称 */
    @Excel(name = "模具名称")
    private String moldName;

    /** 模具编号 */
    @Excel(name = "模具编号")
    private String moldCode;

    /** 设备表id */
    @Excel(name = "设备表id")
    private Long facilityId;

    /** 工序id */
    @Excel(name = "工序id")
    private String processId;

    /** 提交人id */
    @Excel(name = "提交人id")
    private Long userInfoId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String facilityNumber;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 归还位置 */
    @Excel(name = "归还位置")
    private Integer returnPosition;

    private Integer pageNum = 1;
    private Integer pageSize = 10;

    private String process;

    private String moldRmsCode;

    private String userInfoName;

    private Integer moldType;
}
