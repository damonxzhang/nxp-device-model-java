package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionMaintenanceReason;

/**
 * 无需维修原因Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-03-14
 */
public interface INxpInspectionMaintenanceReasonService 
{
    /**
     * 查询无需维修原因
     * 
     * @param id 无需维修原因ID
     * @return 无需维修原因
     */
    public NxpInspectionMaintenanceReason selectNxpInspectionMaintenanceReasonById(Long id);

    /**
     * 查询无需维修原因列表
     * 
     * @param nxpInspectionMaintenanceReason 无需维修原因
     * @return 无需维修原因集合
     */
    public List<NxpInspectionMaintenanceReason> selectNxpInspectionMaintenanceReasonList(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason);

    /**
     * 新增无需维修原因
     * 
     * @param nxpInspectionMaintenanceReason 无需维修原因
     * @return 结果
     */
    public int insertNxpInspectionMaintenanceReason(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason);

    /**
     * 修改无需维修原因
     * 
     * @param nxpInspectionMaintenanceReason 无需维修原因
     * @return 结果
     */
    public int updateNxpInspectionMaintenanceReason(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason);

    /**
     * 批量删除无需维修原因
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionMaintenanceReasonByIds(String ids);

    /**
     * 删除无需维修原因信息
     * 
     * @param id 无需维修原因ID
     * @return 结果
     */
    public int deleteNxpInspectionMaintenanceReasonById(Long id);
}
