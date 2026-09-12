package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupBind;

/**
 * 模具检查组绑定Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
public interface INxpMoldMaintenanceGroupBindService 
{
    /**
     * 查询模具检查组绑定
     * 
     * @param id 模具检查组绑定ID
     * @return 模具检查组绑定
     */
    public NxpMoldMaintenanceGroupBind selectNxpMoldMaintenanceGroupBindById(Long id);

    /**
     * 查询模具检查组绑定列表
     * 
     * @param nxpMoldMaintenanceGroupBind 模具检查组绑定
     * @return 模具检查组绑定集合
     */
    public List<NxpMoldMaintenanceGroupBind> selectNxpMoldMaintenanceGroupBindList(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind);

    /**
     * 新增模具检查组绑定
     * 
     * @param nxpMoldMaintenanceGroupBind 模具检查组绑定
     * @return 结果
     */
    public int insertNxpMoldMaintenanceGroupBind(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind);

    /**
     * 修改模具检查组绑定
     * 
     * @param nxpMoldMaintenanceGroupBind 模具检查组绑定
     * @return 结果
     */
    public int updateNxpMoldMaintenanceGroupBind(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind);

    /**
     * 批量删除模具检查组绑定
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupBindByIds(String ids);

    /**
     * 删除模具检查组绑定信息
     * 
     * @param id 模具检查组绑定ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceGroupBindById(Long id);

    /**
     * 根据模具id查询分组
     * @param moldId
     * @return
     */
    NxpMoldMaintenanceGroupBind selectNxpMoldMaintenanceGroupBindByMoldId(Long moldId);
}
