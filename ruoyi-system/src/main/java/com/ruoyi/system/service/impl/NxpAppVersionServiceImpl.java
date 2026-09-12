package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpAppVersionMapper;
import com.ruoyi.system.domain.NxpAppVersion;
import com.ruoyi.system.service.INxpAppVersionService;
import com.ruoyi.common.core.text.Convert;

/**
 * App版本Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-02-08
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpAppVersionServiceImpl implements INxpAppVersionService 
{
    @Autowired
    private NxpAppVersionMapper nxpAppVersionMapper;

    /**
     * 查询App版本
     * 
     * @param id App版本ID
     * @return App版本
     */
    @Override
    public NxpAppVersion selectNxpAppVersionById(Long id)
    {
        return nxpAppVersionMapper.selectNxpAppVersionById(id);
    }

    /**
     * 查询App版本列表
     * 
     * @param nxpAppVersion App版本
     * @return App版本
     */
    @Override
    public List<NxpAppVersion> selectNxpAppVersionList(NxpAppVersion nxpAppVersion)
    {
        return nxpAppVersionMapper.selectNxpAppVersionList(nxpAppVersion);
    }

    /**
     * 新增App版本
     * 
     * @param nxpAppVersion App版本
     * @return 结果
     */
    @Override
    public int insertNxpAppVersion(NxpAppVersion nxpAppVersion)
    {
        // 1. 判断前端传输的状态是否为启用状态
        if (nxpAppVersion.getStatus() == 1){
            // 查询所有生效的版本号
            NxpAppVersion appVersion = new NxpAppVersion();
            appVersion.setStatus(1);
            List<NxpAppVersion> nxpAppVersions = nxpAppVersionMapper.selectNxpAppVersionList(appVersion);
            for (NxpAppVersion version : nxpAppVersions) {
                // 所有已启用的版本改为禁用
                version.setStatus(2);
                nxpAppVersionMapper.updateNxpAppVersion(version);
            }
        }
        return nxpAppVersionMapper.insertNxpAppVersion(nxpAppVersion);
    }

    /**
     * 修改App版本
     * 
     * @param nxpAppVersion App版本
     * @return 结果
     */
    @Override
    public int updateNxpAppVersion(NxpAppVersion nxpAppVersion)
    {
        return nxpAppVersionMapper.updateNxpAppVersion(nxpAppVersion);
    }

    /**
     * 删除App版本对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpAppVersionByIds(String ids)
    {
        return nxpAppVersionMapper.deleteNxpAppVersionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除App版本信息
     * 
     * @param id App版本ID
     * @return 结果
     */
    @Override
    public int deleteNxpAppVersionById(Long id)
    {
        return nxpAppVersionMapper.deleteNxpAppVersionById(id);
    }
}
