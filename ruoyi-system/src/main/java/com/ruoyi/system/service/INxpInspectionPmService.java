package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpInspectionPm;

/**
 * 周PM检查Service接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface INxpInspectionPmService 
{
    /**
     * 查询周PM检查
     * 
     * @param id 周PM检查ID
     * @return 周PM检查
     */
    public NxpInspectionPm selectNxpInspectionPmById(Long id);

    /**
     * 查询周PM检查列表
     * 
     * @param nxpInspectionPm 周PM检查
     * @return 周PM检查集合
     */
    public List<NxpInspectionPm> selectNxpInspectionPmList(NxpInspectionPm nxpInspectionPm);

    /**
     * 新增周PM检查
     * 
     * @param nxpInspectionPm 周PM检查
     * @return 结果
     */
    public int insertNxpInspectionPm(NxpInspectionPm nxpInspectionPm);

    /**
     * 修改周PM检查
     * 
     * @param nxpFacility 周PM检查
     * @return 结果
     */
    public int updateNxpInspectionPm(NxpFacility nxpFacility);

    /**
     * 批量删除周PM检查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmByIds(String ids);

    /**
     * 删除周PM检查信息
     * 
     * @param id 周PM检查ID
     * @return 结果
     */
    public int deleteNxpInspectionPmById(Long id);

    /**
     * 根据设备id查询
     * @param facilityId
     * @return
     */
    public NxpInspectionPm selectNxpInspectionPmByfacilityId(Long facilityId);
    public List<NxpInspectionPm> selectNxpInspectionPmOverDue(NxpInspectionPm nxpInspectionPm);
}
