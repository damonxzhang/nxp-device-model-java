package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.DTO.NxpInspectionPmDTO;
import com.ruoyi.system.domain.NxpInspection;
import com.ruoyi.system.domain.NxpInspectionPm;

/**
 * 周PM检查Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface NxpInspectionPmMapper 
{
    /**
     * 查询周PM检查
     * 
     * @param id 周PM检查ID
     * @return 周PM检查
     */
    public NxpInspectionPm selectNxpInspectionPmById(Long id);
    public NxpInspectionPm selectNxpInspectionPmLastOne(Long facility_id);

    /**
     * 查询周PM检查列表
     * 
     * @param nxpInspectionPm 周PM检查
     * @return 周PM检查集合
     */
    public List<NxpInspectionPm> selectNxpInspectionPmList(NxpInspectionPm nxpInspectionPm);
    public List<NxpInspectionPm> selectNxpInspectionPmList1(NxpInspectionPm nxpInspectionPm);
    public List<NxpInspectionPm> selectNxpInspectionPmList2(NxpInspectionPm nxpInspectionPm);
    public List<NxpInspectionPm> selectNxpInspectionPmOverDue(NxpInspectionPm nxpInspectionPm);

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
     * @param nxpInspectionPm 周PM检查
     * @return 结果
     */
    public int updateNxpInspectionPm(NxpInspectionPm nxpInspectionPm);

    /**
     * 删除周PM检查
     * 
     * @param id 周PM检查ID
     * @return 结果
     */
    public int deleteNxpInspectionPmById(Long id);

    /**
     * 批量删除周PM检查
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmByIds(String[] ids);

    /**
     * 与我相关的pm列表
     * @param nxpInspectionPm
     * @return
     */
    List<NxpInspectionPm> selectPmList(NxpInspectionPm nxpInspectionPm);

    /**
     * 周PM检查内容
     * @param nxpInspectionPm
     * @return
     */
    NxpInspectionPmDTO pmInfo(NxpInspectionPm nxpInspectionPm);

    /**
     * 根据设备id查询
     * @param facilityId
     * @return
     */
    NxpInspectionPm selectNxpInspectionPmByfacilityId(Long facilityId);

    NxpInspectionPm selectByFacilityIdMaxCheckTime(Long facilityId);
}
