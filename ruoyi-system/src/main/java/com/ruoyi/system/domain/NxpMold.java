package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 模具管理对象 nxp_mold
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMold extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模具名称 */
    @Excel(name = "模具名称")
    private String moldName;

    /** 模具编号 */
    @Excel(name = "模具编号")
    private String moldCode;

    /** 模具位置 */
    @Excel(name = "模具位置")
    private String moldAddress;

    /** 模具状态 */
    @Excel(name = "模具状态")
    private Integer status;

    /** 绑定设备id */
    @Excel(name = "绑定设备id")
    private Integer facilityId;

    /** RMS编号 */
    @Excel(name = "RMS编号")
    private String moldRmsCode;

    /** 密码 */
    @Excel(name = "密码")
    private String moldPwd;

    /** 模具类型 1-SAW 2-BA */
    @Excel(name = "模具类型 1-SAW 2-BA")
    private Integer moldType;

    /** pkgType */
    @Excel(name = "pkgType")
    private String pkgType;

    /** 长度 */
    @Excel(name = "长度")
    private String length;

    /** 矩阵 */
    @Excel(name = "矩阵")
    private String matrix;

    /** thin */
    @Excel(name = "thin")
    private String thin;

    /** 序号 */
    @Excel(name = "序号")
    private String serialNumber;

    /** 模具位置id */
    @Excel(name = "模具位置id")
    private Long moldAddressId;

    /** Substrate长宽 */
    @Excel(name = "Substrate长宽")
    private String substrateLength;

    /** size */
    @Excel(name = "size")
    private String size;

    /** I/O数量 */
    @Excel(name = "I/O数量")
    private String ioNumber;

    /** pitch */
    @Excel(name = "pitch")
    private String pitch;

    /** 球径 */
    @Excel(name = "球径")
    private String sphericalDiameter;

    /** ID */
    @Excel(name = "ID")
    private String baId;

    /** PIN */
    @Excel(name = "PIN")
    private String pin;

    /** 大小 */
    @Excel(name = "大小")
    private String bigOrSmall;

    /** 适用机型 */
    private String machineModel;

    /** 是否优先推荐 0是 1否 */
    private Integer isRecommend;

    /**
     * 部件
     */
    private List<NxpMoldComponent> nxpMoldComponents;

    private int pageNum = 1;
    private int pageSize = 10;

    private String process;

    /*private String moldProductName;
    private Long moldProductId;*/

    private Integer userInfoId;
    /**
     * 产品类型id
     */
    private String productTypeId;
    /**
     * 产品类型名称
     */
    private String productTypeName;

    private String machineModelName;

    private boolean flag = false;

    private boolean colorFlag = false;

    private String facilityNumber;
}
