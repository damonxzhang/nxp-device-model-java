package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldMaintenanceCheck;

/**
 * 模具维护检查结果Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
public interface NxpMoldMaintenanceCheckMapper 
{
    /**
     * 查询模具维护检查结果
     * 
     * @param id 模具维护检查结果ID
     * @return 模具维护检查结果
     */
    public NxpMoldMaintenanceCheck selectNxpMoldMaintenanceCheckById(Long id);

    /**
     * 查询模具维护检查结果列表
     * 
     * @param nxpMoldMaintenanceCheck 模具维护检查结果
     * @return 模具维护检查结果集合
     */
    public List<NxpMoldMaintenanceCheck> selectNxpMoldMaintenanceCheckList(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck);

    /**
     * 新增模具维护检查结果
     * 
     * @param nxpMoldMaintenanceCheck 模具维护检查结果
     * @return 结果
     */
    public int insertNxpMoldMaintenanceCheck(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck);

    /**
     * 修改模具维护检查结果
     * 
     * @param nxpMoldMaintenanceCheck 模具维护检查结果
     * @return 结果
     */
    public int updateNxpMoldMaintenanceCheck(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck);

    /**
     * 删除模具维护检查结果
     * 
     * @param id 模具维护检查结果ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceCheckById(Long id);

    /**
     * 批量删除模具维护检查结果
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceCheckByIds(String[] ids);
}
