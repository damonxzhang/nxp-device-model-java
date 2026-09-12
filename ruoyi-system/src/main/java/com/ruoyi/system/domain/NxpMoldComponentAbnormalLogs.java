package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具异常信息对象 nxp_mold_component_abnormal_logs
 * 
 * @author YanCheng Liu
 * @date 2023-07-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldComponentAbnormalLogs extends BaseEntity
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

    /** 部件id */
    @Excel(name = "部件id")
    private Long componentId;

    /** 类型 1-更换 2-检查 */
    @Excel(name = "类型", dictType = "log_type")
    private Integer logType;

    /** 模具类型 1-SAW 2-BA */
    private Integer moldType;
}
