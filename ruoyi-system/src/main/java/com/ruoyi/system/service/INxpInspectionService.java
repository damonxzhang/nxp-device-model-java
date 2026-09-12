package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspection;

/**
 * 待处理设备Service接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface INxpInspectionService 
{
    /**
     * 查询待处理设备
     * 
     * @param id 待处理设备ID
     * @return 待处理设备
     */
    public NxpInspection selectNxpInspectionById(Long id);

    /**
     * 查询待处理设备列表
     * 
     * @param nxpInspection 待处理设备
     * @return 待处理设备集合
     */
    public List<NxpInspection> selectNxpInspectionList(NxpInspection nxpInspection);

    /**
     * 新增待处理设备
     * 
     * @param nxpInspection 待处理设备
     * @return 结果
     */
    public int insertNxpInspection(NxpInspection nxpInspection);

    /**
     * 修改待处理设备
     * 
     * @param nxpInspection 待处理设备
     * @return 结果
     */
    public int updateNxpInspection(NxpInspection nxpInspection);

    /**
     * 批量删除待处理设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionByIds(String ids);

    /**
     * 删除待处理设备信息
     * 
     * @param id 待处理设备ID
     * @return 结果
     */
    public int deleteNxpInspectionById(Long id);

    /**
     * 查询待处理设备列表(定时任务用)
     *
     * @param nxpInspection 待处理设备
     * @return 待处理设备集合
     */
    public List<NxpInspection> selectNxpInspectionListByTiming(NxpInspection nxpInspection);
}
