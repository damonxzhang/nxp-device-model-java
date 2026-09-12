package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionServiceSparepartMapper;
import com.ruoyi.system.domain.NxpInspectionServiceSparepart;
import com.ruoyi.system.service.INxpInspectionServiceSparepartService;
import com.ruoyi.common.core.text.Convert;

/**
 * 维修设备备件Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionServiceSparepartServiceImpl implements INxpInspectionServiceSparepartService 
{
    @Autowired
    private NxpInspectionServiceSparepartMapper nxpInspectionServiceSparepartMapper;

    /**
     * 查询维修设备备件
     * 
     * @param id 维修设备备件ID
     * @return 维修设备备件
     */
    @Override
    public NxpInspectionServiceSparepart selectNxpInspectionServiceSparepartById(Long id)
    {
        return nxpInspectionServiceSparepartMapper.selectNxpInspectionServiceSparepartById(id);
    }

    /**
     * 查询维修设备备件列表
     * 
     * @param nxpInspectionServiceSparepart 维修设备备件
     * @return 维修设备备件
     */
    @Override
    public List<NxpInspectionServiceSparepart> selectNxpInspectionServiceSparepartList(NxpInspectionServiceSparepart nxpInspectionServiceSparepart)
    {
        return nxpInspectionServiceSparepartMapper.selectNxpInspectionServiceSparepartList(nxpInspectionServiceSparepart);
    }

    /**
     * 新增维修设备备件
     * 
     * @param nxpInspectionServiceSparepart 维修设备备件
     * @return 结果
     */
    @Override
    public int insertNxpInspectionServiceSparepart(NxpInspectionServiceSparepart nxpInspectionServiceSparepart)
    {
        return nxpInspectionServiceSparepartMapper.insertNxpInspectionServiceSparepart(nxpInspectionServiceSparepart);
    }

    /**
     * 修改维修设备备件
     * 
     * @param nxpInspectionServiceSparepart 维修设备备件
     * @return 结果
     */
    @Override
    public int updateNxpInspectionServiceSparepart(NxpInspectionServiceSparepart nxpInspectionServiceSparepart)
    {
        return nxpInspectionServiceSparepartMapper.updateNxpInspectionServiceSparepart(nxpInspectionServiceSparepart);
    }

    /**
     * 删除维修设备备件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionServiceSparepartByIds(String ids)
    {
        return nxpInspectionServiceSparepartMapper.deleteNxpInspectionServiceSparepartByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修设备备件信息
     * 
     * @param id 维修设备备件ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionServiceSparepartById(Long id)
    {
        return nxpInspectionServiceSparepartMapper.deleteNxpInspectionServiceSparepartById(id);
    }
}
