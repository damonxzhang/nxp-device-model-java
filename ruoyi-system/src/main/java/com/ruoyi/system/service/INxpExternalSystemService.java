package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpExternalSystem;

/**
 * 外部系统Service接口
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
public interface INxpExternalSystemService 
{
    /**
     * 查询外部系统
     * 
     * @param id 外部系统ID
     * @return 外部系统
     */
    public NxpExternalSystem selectNxpExternalSystemById(Long id);

    /**
     * 查询外部系统列表
     * 
     * @param nxpExternalSystem 外部系统
     * @return 外部系统集合
     */
    public List<NxpExternalSystem> selectNxpExternalSystemList(NxpExternalSystem nxpExternalSystem);

    /**
     * 新增外部系统
     * 
     * @param nxpExternalSystem 外部系统
     * @return 结果
     */
    public int insertNxpExternalSystem(NxpExternalSystem nxpExternalSystem);

    /**
     * 修改外部系统
     * 
     * @param nxpExternalSystem 外部系统
     * @return 结果
     */
    public int updateNxpExternalSystem(NxpExternalSystem nxpExternalSystem);

    /**
     * 批量删除外部系统
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpExternalSystemByIds(String ids);

    /**
     * 删除外部系统信息
     * 
     * @param id 外部系统ID
     * @return 结果
     */
    public int deleteNxpExternalSystemById(Long id);
}
