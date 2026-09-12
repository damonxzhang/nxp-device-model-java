package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具维护列表对象 nxp_mold_maintenance
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenance extends BaseEntity
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

    /** 模具盒编号 */
    @Excel(name = "模具盒编号")
    private String moldBoxCode;

    /** 模具RMS编号 */
    @Excel(name = "模具RMS编号")
    private String moldRmsCode;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    /** 检查状态 */
    @Excel(name = "检查状态")
    private Integer status;

    /** 实际检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际检查时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date realCheckTime;

    /** 用户id */
    private Integer userInfoId;

    private Integer pageNum = 1;
    private Integer pageSize = 10;

    /** 工序 */
    private String process;

    /** 维护人id */
    private Integer maintenanceUserId;

    /**
     * 维护类型 1-SAW 2-BA 3-临时
     */
    private Integer maintenanceType;

    /** 模具类型 1-SAW 2-BA */
    @Excel(name = "模具类型 1-SAW 2-BA")
    private Integer moldType;
    /**
     * 规则id
     */
    private Long ruleId;

    /** 逾期状态 0-未逾期 1-已逾期 */
    private Integer overdueState;
}
