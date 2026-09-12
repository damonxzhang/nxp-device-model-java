package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 智能柜管理对象 nxp_smart_cabinet
 * 
 * @author YanCheng Liu
 * @date 2023-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpSmartCabinet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 位置 */
    @Excel(name = "位置")
    private String position;

    /** 密码 */
    @Excel(name = "密码")
    private String pwd;

    /** ip地址 */
    @Excel(name = "ip地址")
    private String ipAddress;

    /** 端口 */
    @Excel(name = "端口")
    private String port;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 智能柜编号 */
    @Excel(name = "智能柜编号")
    private String cabinetCode;

    /** 格子编号 */
    @Excel(name = "格子编号")
    private Integer boxCode;

    /** 类型 1-智能柜 2-扫码 */
    private Integer type;

    /** 模具类型 1-SAW 2-BA */
    private Integer moldType;
}
