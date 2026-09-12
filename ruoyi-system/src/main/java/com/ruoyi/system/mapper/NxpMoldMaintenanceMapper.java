package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldMaintenance;
import com.ruoyi.system.domain.VO.NxpMoldMaintenanceVO;

/**
 * 模具维护列表Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
public interface NxpMoldMaintenanceMapper 
{
    /**
     * 查询模具维护列表
     * 
     * @param id 模具维护列表ID
     * @return 模具维护列表
     */
    public NxpMoldMaintenance selectNxpMoldMaintenanceById(Long id);

    /**
     * 查询模具维护列表列表
     * 
     * @param nxpMoldMaintenance 模具维护列表
     * @return 模具维护列表集合
     */
    public List<NxpMoldMaintenance> selectNxpMoldMaintenanceList(NxpMoldMaintenance nxpMoldMaintenance);

    /**
     * 新增模具维护列表
     * 
     * @param nxpMoldMaintenance 模具维护列表
     * @return 结果
     */
    public int insertNxpMoldMaintenance(NxpMoldMaintenance nxpMoldMaintenance);

    /**
     * 修改模具维护列表
     * 
     * @param nxpMoldMaintenance 模具维护列表
     * @return 结果
     */
    public int updateNxpMoldMaintenance(NxpMoldMaintenance nxpMoldMaintenance);

    /**
     * 删除模具维护列表
     * 
     * @param id 模具维护列表ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceById(Long id);

    /**
     * 批量删除模具维护列表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceByIds(String[] ids);

    /**
     * 查询模具维护列表列表(接口用)
     *
     * @param nxpMoldMaintenance 模具维护列表
     * @return 模具维护列表集合
     */
    List<NxpMoldMaintenance> selectNxpMoldMaintenanceListApi(NxpMoldMaintenance nxpMoldMaintenance);

    /**
     * 查询模具维护列表列表(接口用)
     *
     * @param nxpMoldMaintenanceVO 模具维护列表
     * @return 模具维护列表集合
     */
    List<NxpMoldMaintenance> selectNxpMoldMaintenanceListApiParams(NxpMoldMaintenanceVO nxpMoldMaintenanceVO);

    /**
     * 查询模具维护列表列表-定时任务用
     * @param moldMaintenance
     * @return
     */
    List<NxpMoldMaintenance> selectNxpMoldMaintenanceListTask(NxpMoldMaintenance moldMaintenance);
}
