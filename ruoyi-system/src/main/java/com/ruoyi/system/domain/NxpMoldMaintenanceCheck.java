package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具维护检查结果对象 nxp_mold_maintenance_check
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenanceCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模具维护id */
    @Excel(name = "模具维护id")
    private Long moldMaintenanceId;

    /** 检查项id */
    @Excel(name = "检查项id")
    private Long groupItemId;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /** 检查人id */
    @Excel(name = "检查人id")
    private Long userInfoId;

    /** 检查项结果 */
    @Excel(name = "检查项结果")
    private Long groupItemResult;

    /** 图片id */
    @Excel(name = "图片id")
    private String fileId;

    private String groupItemName;

    private String userInfoName;

    private List<NxpMoldMaintenanceFile> files;
}
