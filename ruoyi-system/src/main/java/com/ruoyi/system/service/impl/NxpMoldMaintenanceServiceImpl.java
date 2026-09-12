package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.VO.NxpMoldMaintenanceVO;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldMaintenanceMapper;
import com.ruoyi.system.domain.NxpMoldMaintenance;
import com.ruoyi.system.service.INxpMoldMaintenanceService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具维护列表Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldMaintenanceServiceImpl implements INxpMoldMaintenanceService 
{
    @Resource
    private NxpMoldMaintenanceMapper nxpMoldMaintenanceMapper;

    /**
     * 查询模具维护列表
     * 
     * @param id 模具维护列表ID
     * @return 模具维护列表
     */
    @Override
    public NxpMoldMaintenance selectNxpMoldMaintenanceById(Long id)
    {
        return nxpMoldMaintenanceMapper.selectNxpMoldMaintenanceById(id);
    }

    /**
     * 查询模具维护列表列表
     * 
     * @param nxpMoldMaintenance 模具维护列表
     * @return 模具维护列表
     */
    @Override
    public List<NxpMoldMaintenance> selectNxpMoldMaintenanceList(NxpMoldMaintenance nxpMoldMaintenance)
    {
        return nxpMoldMaintenanceMapper.selectNxpMoldMaintenanceList(nxpMoldMaintenance);
    }

    /**
     * 新增模具维护列表
     * 
     * @param nxpMoldMaintenance 模具维护列表
     * @return 结果
     */
    @Override
    public int insertNxpMoldMaintenance(NxpMoldMaintenance nxpMoldMaintenance)
    {
        nxpMoldMaintenance.setCreateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceMapper.insertNxpMoldMaintenance(nxpMoldMaintenance);
    }

    /**
     * 修改模具维护列表
     * 
     * @param nxpMoldMaintenance 模具维护列表
     * @return 结果
     */
    @Override
    public int updateNxpMoldMaintenance(NxpMoldMaintenance nxpMoldMaintenance)
    {
        nxpMoldMaintenance.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceMapper.updateNxpMoldMaintenance(nxpMoldMaintenance);
    }

    /**
     * 删除模具维护列表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceByIds(String ids)
    {
        return nxpMoldMaintenanceMapper.deleteNxpMoldMaintenanceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具维护列表信息
     * 
     * @param id 模具维护列表ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceById(Long id)
    {
        return nxpMoldMaintenanceMapper.deleteNxpMoldMaintenanceById(id);
    }

    /**
     * 查询模具维护列表列表(接口用)
     *
     * @param nxpMoldMaintenance 模具维护列表
     * @return 模具维护列表集合
     */
    @Override
    public List<NxpMoldMaintenance> selectNxpMoldMaintenanceListApi(NxpMoldMaintenance nxpMoldMaintenance) {
        return nxpMoldMaintenanceMapper.selectNxpMoldMaintenanceListApi(nxpMoldMaintenance);
    }

    /**
     * 查询模具维护列表列表(接口用)
     *
     * @param nxpMoldMaintenanceVO 模具维护列表
     * @return 模具维护列表集合
     */
    @Override
    public List<NxpMoldMaintenance> selectNxpMoldMaintenanceListApiParams(NxpMoldMaintenanceVO nxpMoldMaintenanceVO) {
        return nxpMoldMaintenanceMapper.selectNxpMoldMaintenanceListApiParams(nxpMoldMaintenanceVO);
    }

    /**
     * 查询模具维护列表列表-定时任务用
     * @param moldMaintenance
     * @return
     */
    @Override
    public List<NxpMoldMaintenance> selectNxpMoldMaintenanceListTask(NxpMoldMaintenance moldMaintenance) {
        return nxpMoldMaintenanceMapper.selectNxpMoldMaintenanceListTask(moldMaintenance);
    }
}
