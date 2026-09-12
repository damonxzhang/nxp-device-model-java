package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;

/**
 * 模具维护分组Service接口
 *
 * @author YanCheng Liu
 * @date 2023-07-16
 */
public interface INxpMoldMaintenanceGroupService {
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
     * 批量删除模具维护分组
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupByIds(String ids);

    /**
     * 删除模具维护分组信息
     *
     * @param id 模具维护分组ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupById(Long id);

    /**
     * 根据id查询groupItem
     * @param id
     * @return
     */
    public NxpMoldMaintenanceGroupItem selectGroupItemById(Long id);
}
