package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.Map;

/**
 * 待更换模具对象 t_replaced_list
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplacedList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long replaceId;

    /** 模具id */
    private Long moldId;

    /** 模具名称 */
    private String moldName;

    /** 工序 */
    @Excel(name = "工序")
    private String process;

    /** 老模具编号 */
    @Excel(name = "老模具编号")
    private String moldCode;

    /** 新模具编号 */
    @Excel(name = "新模具编号")
    private String newMoldCode;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String facilityNumber;

    /** 申请人名称 */
    @Excel(name = "申请人")
    private String userInfoName;

    /** 产品类型名称 */
    @Excel(name = "产品类型")
    private String productTypeName;

    /** 执行人名称 */
    @Excel(name = "执行人")
    private String executeUserInfoName;

    /**
     * zhangxin
     */
    /** 模具RMS */
    private String moldRmsCode;

    /** 设备表id */
    private Long facilityId;

    /** 工序id */
    private String processId;

    /** 提交人id */
    private Long userInfoId;

    /** 状态 1-待更换 2-已更换 */
    @Excel(name = "状态", dictType = "mold_replaced_status")
    private Integer status;

    /** 类型 1-模具 2-模具盒 */
    private Integer type;

    /** 执行人id */
    private Integer executeUserId;

    private Integer pageNum = 1;
    private Integer pageSize = 10;

    /** 产品类型id */
    private Long productTypeId;

    private NxpMold nxpMold;

    private Integer userId;

    /** 更换角色 1-pm更换 2-PM和作业员都可以换 */
    private Integer isReplace;

    private Integer moldType;

    /**
     * 扩展数据
     */
    private Map<String,Object> extendedData;

    private Long newMoldId;

    /**
     * 0-不可删 1-可删
     */
    private Integer isDel;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String moldCodeParams;
}
