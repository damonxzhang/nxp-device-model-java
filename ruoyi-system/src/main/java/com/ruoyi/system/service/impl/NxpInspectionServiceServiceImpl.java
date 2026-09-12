package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionServiceMapper;
import com.ruoyi.system.domain.NxpInspectionService;
import com.ruoyi.system.service.INxpInspectionServiceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 维修详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionServiceServiceImpl implements INxpInspectionServiceService 
{
    @Autowired
    private NxpInspectionServiceMapper nxpInspectionServiceMapper;

    /**
     * 查询维修详情
     * 
     * @param id 维修详情ID
     * @return 维修详情
     */
    @Override
    public NxpInspectionService selectNxpInspectionServiceById(Long id)
    {
        return nxpInspectionServiceMapper.selectNxpInspectionServiceById(id);
    }

    /**
     * 查询维修详情列表
     * 
     * @param nxpInspectionService 维修详情
     * @return 维修详情
     */
    @Override
    public List<NxpInspectionService> selectNxpInspectionServiceList(NxpInspectionService nxpInspectionService)
    {
        return nxpInspectionServiceMapper.selectNxpInspectionServiceListNew(nxpInspectionService);
    }

    /**
     * 新增维修详情
     * 
     * @param nxpInspectionService 维修详情
     * @return 结果
     */
    @Override
    public int insertNxpInspectionService(NxpInspectionService nxpInspectionService)
    {
        return nxpInspectionServiceMapper.insertNxpInspectionService(nxpInspectionService);
    }

    /**
     * 修改维修详情
     * 
     * @param nxpInspectionService 维修详情
     * @return 结果
     */
    @Override
    public int updateNxpInspectionService(NxpInspectionService nxpInspectionService)
    {
        return nxpInspectionServiceMapper.updateNxpInspectionService(nxpInspectionService);
    }

    /**
     * 删除维修详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionServiceByIds(String ids)
    {
        return nxpInspectionServiceMapper.deleteNxpInspectionServiceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修详情信息
     * 
     * @param id 维修详情ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionServiceById(Long id)
    {
        return nxpInspectionServiceMapper.deleteNxpInspectionServiceById(id);
    }

    @Override
    public List<NxpInspectionService> selectNxpInspectionServiceListTiming(NxpInspectionService nxpInspectionService) {
        return nxpInspectionServiceMapper.selectNxpInspectionServiceList(nxpInspectionService);
    }
}
