package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldComponentAbnormalLogs;

/**
 * 模具异常信息Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-12
 */
public interface NxpMoldComponentAbnormalLogsMapper 
{
    /**
     * 查询模具异常信息
     * 
     * @param id 模具异常信息ID
     * @return 模具异常信息
     */
    public NxpMoldComponentAbnormalLogs selectNxpMoldComponentAbnormalLogsById(Long id);

    /**
     * 查询模具异常信息列表
     * 
     * @param nxpMoldComponentAbnormalLogs 模具异常信息
     * @return 模具异常信息集合
     */
    public List<NxpMoldComponentAbnormalLogs> selectNxpMoldComponentAbnormalLogsList(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs);

    /**
     * 新增模具异常信息
     * 
     * @param nxpMoldComponentAbnormalLogs 模具异常信息
     * @return 结果
     */
    public int insertNxpMoldComponentAbnormalLogs(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs);

    /**
     * 修改模具异常信息
     * 
     * @param nxpMoldComponentAbnormalLogs 模具异常信息
     * @return 结果
     */
    public int updateNxpMoldComponentAbnormalLogs(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs);

    /**
     * 删除模具异常信息
     * 
     * @param id 模具异常信息ID
     * @return 结果
     */
    public int deleteNxpMoldComponentAbnormalLogsById(Long id);

    /**
     * 批量删除模具异常信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldComponentAbnormalLogsByIds(String[] ids);
}
