package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpAppVersion;

/**
 * App版本Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-02-08
 */
public interface INxpAppVersionService 
{
    /**
     * 查询App版本
     * 
     * @param id App版本ID
     * @return App版本
     */
    public NxpAppVersion selectNxpAppVersionById(Long id);

    /**
     * 查询App版本列表
     * 
     * @param nxpAppVersion App版本
     * @return App版本集合
     */
    public List<NxpAppVersion> selectNxpAppVersionList(NxpAppVersion nxpAppVersion);

    /**
     * 新增App版本
     * 
     * @param nxpAppVersion App版本
     * @return 结果
     */
    public int insertNxpAppVersion(NxpAppVersion nxpAppVersion);

    /**
     * 修改App版本
     * 
     * @param nxpAppVersion App版本
     * @return 结果
     */
    public int updateNxpAppVersion(NxpAppVersion nxpAppVersion);

    /**
     * 批量删除App版本
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpAppVersionByIds(String ids);

    /**
     * 删除App版本信息
     * 
     * @param id App版本ID
     * @return 结果
     */
    public int deleteNxpAppVersionById(Long id);
}
