package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmFacility;

/**
 * 周PM检查项关联表Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface NxpInspectionPmFacilityMapper 
{
    /**
     * 查询周PM检查项关联表
     * 
     * @param id 周PM检查项关联表ID
     * @return 周PM检查项关联表
     */
    public NxpInspectionPmFacility selectNxpInspectionPmFacilityById(Long id);
    public NxpInspectionPmFacility selectNxpInspectionPmByFacilityId(Long facilityId);
    public List<NxpInspectionPmFacility> selectNxpInspectionPmFacilityListByFacilityId(NxpInspectionPmFacility nxpInspectionPmFacility);

    /**
     * 查询周PM检查项关联表列表
     * 
     * @param nxpInspectionPmFacility 周PM检查项关联表
     * @return 周PM检查项关联表集合
     */
    public List<NxpInspectionPmFacility> selectNxpInspectionPmFacilityList(NxpInspectionPmFacility nxpInspectionPmFacility);

    /**
     * 新增周PM检查项关联表
     * 
     * @param nxpInspectionPmFacility 周PM检查项关联表
     * @return 结果
     */
    public int insertNxpInspectionPmFacility(NxpInspectionPmFacility nxpInspectionPmFacility);

    /**
     * 修改周PM检查项关联表
     * 
     * @param nxpInspectionPmFacility 周PM检查项关联表
     * @return 结果
     */
    public int updateNxpInspectionPmFacility(NxpInspectionPmFacility nxpInspectionPmFacility);
    public int updateNxpInspectionPmFacilityByFacility(NxpInspectionPmFacility nxpInspectionPmFacility);

    /**
     * 删除周PM检查项关联表
     * 
     * @param id 周PM检查项关联表ID
     * @return 结果
     */
    public int deleteNxpInspectionPmFacilityById(Long id);
    public int deleteNxpInspectionPmFacilityByFacilityId(Long facility_id);

    /**
     * 批量删除周PM检查项关联表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmFacilityByIds(String[] ids);
}
