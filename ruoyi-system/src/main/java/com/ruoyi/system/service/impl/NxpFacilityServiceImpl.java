package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.VO.NxpFacilityVO;
import com.ruoyi.system.mapper.NxpMoldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpFacilityMapper;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.service.INxpFacilityService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 设备管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpFacilityServiceImpl implements INxpFacilityService
{
    @Autowired
    private NxpFacilityMapper nxpFacilityMapper;
    @Resource
    private NxpMoldMapper nxpMoldMapper;

    /**
     * 查询设备管理
     * 
     * @param id 设备管理ID
     * @return 设备管理
     */
    @Override
    public NxpFacility selectNxpFacilityById(Long id)
    {
        return nxpFacilityMapper.selectNxpFacilityById(id);
    }

    /**
     * 查询设备管理列表
     * 
     * @param nxpFacility 设备管理
     * @return 设备管理
     */
    @Override
    public List<NxpFacility> selectNxpFacilityList(NxpFacility nxpFacility)
    {
        return nxpFacilityMapper.selectNxpFacilityList(nxpFacility);
    }

    /**
     * 新增设备管理
     * 
     * @param nxpFacility 设备管理
     * @return 结果
     */
    @Override
    public int insertNxpFacility(NxpFacility nxpFacility)
    {
        return nxpFacilityMapper.insertNxpFacility(nxpFacility);
    }

    /**
     * 修改设备管理
     * 
     * @param nxpFacility 设备管理
     * @return 结果
     */
    @Override
    public int updateNxpFacility(NxpFacility nxpFacility)
    {
        return nxpFacilityMapper.updateNxpFacility(nxpFacility);
    }

    /**
     * 删除设备管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpFacilityByIds(String ids)
    {
        return nxpFacilityMapper.deleteNxpFacilityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpFacilityById(Long id)
    {
        return nxpFacilityMapper.deleteNxpFacilityById(id);
    }

    @Override
    public List<NxpFacility> selectNxpFacilityByNotInIds(NxpFacilityVO nxpFacilityVO) {
        return nxpFacilityMapper.selectNxpFacilityByNotInIds(nxpFacilityVO);
    }

    @Override
    public List<NxpFacility> selectNxpFacilityPmList(NxpFacility nxpFacility) {
        return nxpFacilityMapper.selectNxpFacilityPmList(nxpFacility);
    }

    /**
     * 根据设备编号查询设备
     * @param number
     * @return
     */
    @Override
    public NxpFacility selectByNumber(String number) {
        return nxpFacilityMapper.selectNxpFacilityAndProcessByNumber(number);
    }

    @Override
    public List<NxpFacility> selectFacilityByModelAndProductTypeId(NxpMold nxpMold) {
        NxpMold nxpMold1 = nxpMoldMapper.selectNxpMoldByCode(nxpMold.getMoldCode());
        nxpMold.setProductTypeId(nxpMold1.getProductTypeId());
        return nxpFacilityMapper.selectFacilityByModelAndProductTypeId(nxpMold);
    }
}
