package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldComponentAbnormalLogsMapper;
import com.ruoyi.system.domain.NxpMoldComponentAbnormalLogs;
import com.ruoyi.system.service.INxpMoldComponentAbnormalLogsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 模具异常信息Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-07-12
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldComponentAbnormalLogsServiceImpl implements INxpMoldComponentAbnormalLogsService 
{
    @Autowired
    private NxpMoldComponentAbnormalLogsMapper nxpMoldComponentAbnormalLogsMapper;

    /**
     * 查询模具异常信息
     * 
     * @param id 模具异常信息ID
     * @return 模具异常信息
     */
    @Override
    public NxpMoldComponentAbnormalLogs selectNxpMoldComponentAbnormalLogsById(Long id)
    {
        return nxpMoldComponentAbnormalLogsMapper.selectNxpMoldComponentAbnormalLogsById(id);
    }

    /**
     * 查询模具异常信息列表
     * 
     * @param nxpMoldComponentAbnormalLogs 模具异常信息
     * @return 模具异常信息
     */
    @Override
    public List<NxpMoldComponentAbnormalLogs> selectNxpMoldComponentAbnormalLogsList(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs)
    {
        return nxpMoldComponentAbnormalLogsMapper.selectNxpMoldComponentAbnormalLogsList(nxpMoldComponentAbnormalLogs);
    }

    /**
     * 新增模具异常信息
     * 
     * @param nxpMoldComponentAbnormalLogs 模具异常信息
     * @return 结果
     */
    @Override
    public int insertNxpMoldComponentAbnormalLogs(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs)
    {
        nxpMoldComponentAbnormalLogs.setCreateTime(DateUtils.getNowDate());
        return nxpMoldComponentAbnormalLogsMapper.insertNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs);
    }

    /**
     * 修改模具异常信息
     * 
     * @param nxpMoldComponentAbnormalLogs 模具异常信息
     * @return 结果
     */
    @Override
    public int updateNxpMoldComponentAbnormalLogs(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs)
    {
        nxpMoldComponentAbnormalLogs.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldComponentAbnormalLogsMapper.updateNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs);
    }

    /**
     * 删除模具异常信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldComponentAbnormalLogsByIds(String ids)
    {
        return nxpMoldComponentAbnormalLogsMapper.deleteNxpMoldComponentAbnormalLogsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具异常信息信息
     * 
     * @param id 模具异常信息ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldComponentAbnormalLogsById(Long id)
    {
        return nxpMoldComponentAbnormalLogsMapper.deleteNxpMoldComponentAbnormalLogsById(id);
    }
}
