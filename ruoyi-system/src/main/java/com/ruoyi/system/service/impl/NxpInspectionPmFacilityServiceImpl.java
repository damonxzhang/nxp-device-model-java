package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionPmFacilityMapper;
import com.ruoyi.system.domain.NxpInspectionPmFacility;
import com.ruoyi.system.service.INxpInspectionPmFacilityService;
import com.ruoyi.common.core.text.Convert;

/**
 * 周PM检查项关联表Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmFacilityServiceImpl implements INxpInspectionPmFacilityService 
{
    @Autowired
    private NxpInspectionPmFacilityMapper nxpInspectionPmFacilityMapper;

    /**
     * 查询周PM检查项关联表
     * 
     * @param id 周PM检查项关联表ID
     * @return 周PM检查项关联表
     */
    @Override
    public NxpInspectionPmFacility selectNxpInspectionPmFacilityById(Long id)
    {
        return nxpInspectionPmFacilityMapper.selectNxpInspectionPmFacilityById(id);
    }

    @Override
    public List<NxpInspectionPmFacility> selectNxpInspectionPmFacilityListByFacilityId(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        return nxpInspectionPmFacilityMapper.selectNxpInspectionPmFacilityListByFacilityId(nxpInspectionPmFacility);
    }

    /**
     * 查询周PM检查项关联表列表
     * 
     * @param nxpInspectionPmFacility 周PM检查项关联表
     * @return 周PM检查项关联表
     */
    @Override
    public List<NxpInspectionPmFacility> selectNxpInspectionPmFacilityList(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        return nxpInspectionPmFacilityMapper.selectNxpInspectionPmFacilityList(nxpInspectionPmFacility);
    }

    /**
     * 新增周PM检查项关联表
     * 
     * @param nxpInspectionPmFacility 周PM检查项关联表
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPmFacility(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        return nxpInspectionPmFacilityMapper.insertNxpInspectionPmFacility(nxpInspectionPmFacility);
    }

    /**
     * 修改周PM检查项关联表
     * 
     * @param nxpInspectionPmFacility 周PM检查项关联表
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPmFacility(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        return nxpInspectionPmFacilityMapper.updateNxpInspectionPmFacility(nxpInspectionPmFacility);
    }

    /**
     * 删除周PM检查项关联表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmFacilityByIds(String ids)
    {
        return nxpInspectionPmFacilityMapper.deleteNxpInspectionPmFacilityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周PM检查项关联表信息
     * 
     * @param id 周PM检查项关联表ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmFacilityById(Long id)
    {
        return nxpInspectionPmFacilityMapper.deleteNxpInspectionPmFacilityById(id);
    }

    @Override
    public int deleteNxpInspectionPmFacilityByFacilityId(Long faciliyt_id)
    {
        return nxpInspectionPmFacilityMapper.deleteNxpInspectionPmFacilityByFacilityId(faciliyt_id);
    }
}
