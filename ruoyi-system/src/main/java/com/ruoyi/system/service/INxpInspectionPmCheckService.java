package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmCheck;

/**
 * 周PM复查Service接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface INxpInspectionPmCheckService 
{
    /**
     * 查询周PM复查
     * 
     * @param id 周PM复查ID
     * @return 周PM复查
     */
    public NxpInspectionPmCheck selectNxpInspectionPmCheckById(Long id);

    /**
     * 查询周PM复查列表
     * 
     * @param nxpInspectionPmCheck 周PM复查
     * @return 周PM复查集合
     */
    public List<NxpInspectionPmCheck> selectNxpInspectionPmCheckList(NxpInspectionPmCheck nxpInspectionPmCheck);

    /**
     * 新增周PM复查
     * 
     * @param nxpInspectionPmCheck 周PM复查
     * @return 结果
     */
    public int insertNxpInspectionPmCheck(NxpInspectionPmCheck nxpInspectionPmCheck);

    /**
     * 修改周PM复查
     * 
     * @param nxpInspectionPmCheck 周PM复查
     * @return 结果
     */
    public int updateNxpInspectionPmCheck(NxpInspectionPmCheck nxpInspectionPmCheck);

    /**
     * 批量删除周PM复查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmCheckByIds(String ids);

    /**
     * 删除周PM复查信息
     * 
     * @param id 周PM复查ID
     * @return 结果
     */
    public int deleteNxpInspectionPmCheckById(Long id);

    /**
     * 根据pmId查询
     * @param id
     * @return
     */
    NxpInspectionPmCheck selectNxpInspectionPmByPmId(Long id);
}
