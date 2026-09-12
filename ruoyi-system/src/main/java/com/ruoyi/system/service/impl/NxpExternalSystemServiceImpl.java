package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpExternalSystemMapper;
import com.ruoyi.system.domain.NxpExternalSystem;
import com.ruoyi.system.service.INxpExternalSystemService;
import com.ruoyi.common.core.text.Convert;

/**
 * 外部系统Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpExternalSystemServiceImpl implements INxpExternalSystemService 
{
    @Autowired
    private NxpExternalSystemMapper nxpExternalSystemMapper;

    /**
     * 查询外部系统
     * 
     * @param id 外部系统ID
     * @return 外部系统
     */
    @Override
    public NxpExternalSystem selectNxpExternalSystemById(Long id)
    {
        return nxpExternalSystemMapper.selectNxpExternalSystemById(id);
    }

    /**
     * 查询外部系统列表
     * 
     * @param nxpExternalSystem 外部系统
     * @return 外部系统
     */
    @Override
    public List<NxpExternalSystem> selectNxpExternalSystemList(NxpExternalSystem nxpExternalSystem)
    {
        return nxpExternalSystemMapper.selectNxpExternalSystemList(nxpExternalSystem);
    }

    /**
     * 新增外部系统
     * 
     * @param nxpExternalSystem 外部系统
     * @return 结果
     */
    @Override
    public int insertNxpExternalSystem(NxpExternalSystem nxpExternalSystem)
    {
        nxpExternalSystem.setCreateTime(DateUtils.getNowDate());
        return nxpExternalSystemMapper.insertNxpExternalSystem(nxpExternalSystem);
    }

    /**
     * 修改外部系统
     * 
     * @param nxpExternalSystem 外部系统
     * @return 结果
     */
    @Override
    public int updateNxpExternalSystem(NxpExternalSystem nxpExternalSystem)
    {
        nxpExternalSystem.setUpdateTime(DateUtils.getNowDate());
        return nxpExternalSystemMapper.updateNxpExternalSystem(nxpExternalSystem);
    }

    /**
     * 删除外部系统对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpExternalSystemByIds(String ids)
    {
        return nxpExternalSystemMapper.deleteNxpExternalSystemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除外部系统信息
     * 
     * @param id 外部系统ID
     * @return 结果
     */
    @Override
    public int deleteNxpExternalSystemById(Long id)
    {
        return nxpExternalSystemMapper.deleteNxpExternalSystemById(id);
    }
}
