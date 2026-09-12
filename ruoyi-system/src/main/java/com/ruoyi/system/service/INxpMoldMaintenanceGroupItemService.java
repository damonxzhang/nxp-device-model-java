package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;

/**
 * 模具维护检查项Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-08-22
 */
public interface INxpMoldMaintenanceGroupItemService 
{
    /**
     * 查询模具维护检查项
     * 
     * @param id 模具维护检查项ID
     * @return 模具维护检查项
     */
    public NxpMoldMaintenanceGroupItem selectNxpMoldMaintenanceGroupItemById(Long id);

    /**
     * 查询模具维护检查项列表
     * 
     * @param nxpMoldMaintenanceGroupItem 模具维护检查项
     * @return 模具维护检查项集合
     */
    public List<NxpMoldMaintenanceGroupItem> selectNxpMoldMaintenanceGroupItemList(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem);

    /**
     * 新增模具维护检查项
     * 
     * @param nxpMoldMaintenanceGroupItem 模具维护检查项
     * @return 结果
     */
    public int insertNxpMoldMaintenanceGroupItem(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem);

    /**
     * 修改模具维护检查项
     * 
     * @param nxpMoldMaintenanceGroupItem 模具维护检查项
     * @return 结果
     */
    public int updateNxpMoldMaintenanceGroupItem(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem);

    /**
     * 批量删除模具维护检查项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupItemByIds(String ids);

    /**
     * 删除模具维护检查项信息
     * 
     * @param id 模具维护检查项ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupItemById(Long id);
}
