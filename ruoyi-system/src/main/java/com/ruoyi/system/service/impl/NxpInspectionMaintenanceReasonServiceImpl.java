package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionMaintenanceReasonMapper;
import com.ruoyi.system.domain.NxpInspectionMaintenanceReason;
import com.ruoyi.system.service.INxpInspectionMaintenanceReasonService;
import com.ruoyi.common.core.text.Convert;

/**
 * 无需维修原因Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-03-14
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionMaintenanceReasonServiceImpl implements INxpInspectionMaintenanceReasonService 
{
    @Autowired
    private NxpInspectionMaintenanceReasonMapper nxpInspectionMaintenanceReasonMapper;

    /**
     * 查询无需维修原因
     * 
     * @param id 无需维修原因ID
     * @return 无需维修原因
     */
    @Override
    public NxpInspectionMaintenanceReason selectNxpInspectionMaintenanceReasonById(Long id)
    {
        return nxpInspectionMaintenanceReasonMapper.selectNxpInspectionMaintenanceReasonById(id);
    }

    /**
     * 查询无需维修原因列表
     * 
     * @param nxpInspectionMaintenanceReason 无需维修原因
     * @return 无需维修原因
     */
    @Override
    public List<NxpInspectionMaintenanceReason> selectNxpInspectionMaintenanceReasonList(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason)
    {
        return nxpInspectionMaintenanceReasonMapper.selectNxpInspectionMaintenanceReasonList(nxpInspectionMaintenanceReason);
    }

    /**
     * 新增无需维修原因
     * 
     * @param nxpInspectionMaintenanceReason 无需维修原因
     * @return 结果
     */
    @Override
    public int insertNxpInspectionMaintenanceReason(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason)
    {
        nxpInspectionMaintenanceReason.setCreate_time(new Date());
        return nxpInspectionMaintenanceReasonMapper.insertNxpInspectionMaintenanceReason(nxpInspectionMaintenanceReason);
    }

    /**
     * 修改无需维修原因
     * 
     * @param nxpInspectionMaintenanceReason 无需维修原因
     * @return 结果
     */
    @Override
    public int updateNxpInspectionMaintenanceReason(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason)
    {
        nxpInspectionMaintenanceReason.setUpdate_time(new Date());
        return nxpInspectionMaintenanceReasonMapper.updateNxpInspectionMaintenanceReason(nxpInspectionMaintenanceReason);
    }

    /**
     * 删除无需维修原因对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionMaintenanceReasonByIds(String ids)
    {
        return nxpInspectionMaintenanceReasonMapper.deleteNxpInspectionMaintenanceReasonByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除无需维修原因信息
     * 
     * @param id 无需维修原因ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionMaintenanceReasonById(Long id)
    {
        return nxpInspectionMaintenanceReasonMapper.deleteNxpInspectionMaintenanceReasonById(id);
    }
}
