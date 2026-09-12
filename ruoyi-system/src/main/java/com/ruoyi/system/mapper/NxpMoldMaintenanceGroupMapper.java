package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;

/**
 * 模具维护分组Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
public interface NxpMoldMaintenanceGroupMapper 
{
    /**
     * 查询模具维护分组
     * 
     * @param id 模具维护分组ID
     * @return 模具维护分组
     */
    public NxpMoldMaintenanceGroup selectNxpMoldMaintenanceGroupById(Long id);

    /**
     * 查询模具维护分组列表
     * 
     * @param nxpMoldMaintenanceGroup 模具维护分组
     * @return 模具维护分组集合
     */
    public List<NxpMoldMaintenanceGroup> selectNxpMoldMaintenanceGroupList(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup);

    /**
     * 新增模具维护分组
     * 
     * @param nxpMoldMaintenanceGroup 模具维护分组
     * @return 结果
     */
    public int insertNxpMoldMaintenanceGroup(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup);

    /**
     * 修改模具维护分组
     * 
     * @param nxpMoldMaintenanceGroup 模具维护分组
     * @return 结果
     */
    public int updateNxpMoldMaintenanceGroup(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup);

    /**
     * 删除模具维护分组
     * 
     * @param id 模具维护分组ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupById(Long id);

    /**
     * 批量删除模具维护分组
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupByIds(String[] ids);

    /**
     * 批量删除模具检查项
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupItemByGroupIds(String[] ids);
    
    /**
     * 批量新增模具检查项
     * 
     * @param nxpMoldMaintenanceGroupItemList 模具检查项列表
     * @return 结果
     */
    public int batchNxpMoldMaintenanceGroupItem(List<NxpMoldMaintenanceGroupItem> nxpMoldMaintenanceGroupItemList);
    

    /**
     * 通过模具维护分组ID删除模具检查项信息
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupItemByGroupId(Long id);

    /**
     * 根据id查询groupItem
     * @param groupItemId
     * @return
     */
    public NxpMoldMaintenanceGroupItem selectGroupItemById(Long groupItemId);

    /**
     * 根据组别id查询模具种类
     * @param groupId
     * @return
     */
    List<Long> selectMoldTypeIdByGroupId(Long groupId);
}
