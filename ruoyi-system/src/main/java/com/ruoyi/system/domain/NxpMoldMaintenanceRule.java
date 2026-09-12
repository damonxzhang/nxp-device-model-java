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
 * 模具维护规则对象 nxp_mold_maintenance_rule
 * 
 * @author YanCheng Liu
 * @date 2023-07-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenanceRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 检查周期 */
    @Excel(name = "检查周期")
    private Long cycle;

    /** 规则开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "规则开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 模具类型 */
    @Excel(name = "模具类型")
    private Long moldType;

    private Long forCount;

    /** 类型 1-固定 2-临时 */
    private Integer type;

    /** 应执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "应执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shouldExecuteTime;

    /** 实际执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际执行时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date practicalTime;
    /**
     * 模具id
     */
    private Long moldId;
    /**
     * 模具编号
     */
    private String moldCode;
    /**
     * 提前天数
     */
    private Long advanceNum;
}
