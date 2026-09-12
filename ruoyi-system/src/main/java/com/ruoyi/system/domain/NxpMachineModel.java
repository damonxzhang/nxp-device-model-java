package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备型号对象 nxp_machine_model
 * 
 * @author YanCheng Liu
 * @date 2021-02-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMachineModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机型 */
    @Excel(name = "机型")
    private String name;

    /** 维修位置 */
    @Excel(name = "维修位置")
    private String inspection_position;

    private String inspectionPosition;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 发布人 */
    @Excel(name = "发布人")
    private Long create_by;

    /** 发布时间 */
    @Excel(name = "发布时间")
    private String create_dt;

    /** 更新时间 */
    @Excel(name = "更新时间")
    private String update_dt;

    /** 主键 */
    private Long id;

    /** 工序名称 */
    private String processName;

    /** 工序id */
    private Long processId;

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    private Long vendorId;

    /** 用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;

    /** 负责人 */
    private Integer owner;
    private String ownerName;

    private Integer area;
    private Integer shift;

    private Integer isMe;

}
