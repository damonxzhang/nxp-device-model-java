package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模具维护文件对象 nxp_mold_maintenance_file
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenanceFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 检查项id */
    @Excel(name = "检查项id")
    private Long moldCheckId;

    /** 模具维护id */
    @Excel(name = "模具维护id")
    private Long moldMaintenanceId;

    /** 文件存储路径 */
    @Excel(name = "文件存储路径")
    private String filePath;

}
