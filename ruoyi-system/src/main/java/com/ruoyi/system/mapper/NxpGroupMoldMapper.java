package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpGroupMold;

/**
 * 模具&检查组Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-10-13
 */
public interface NxpGroupMoldMapper 
{
    /**
     * 查询模具&检查组
     * 
     * @param id 模具&检查组ID
     * @return 模具&检查组
     */
    public NxpGroupMold selectNxpGroupMoldById(Long id);

    /**
     * 查询模具&检查组列表
     * 
     * @param nxpGroupMold 模具&检查组
     * @return 模具&检查组集合
     */
    public List<NxpGroupMold> selectNxpGroupMoldList(NxpGroupMold nxpGroupMold);

    /**
     * 新增模具&检查组
     * 
     * @param nxpGroupMold 模具&检查组
     * @return 结果
     */
    public int insertNxpGroupMold(NxpGroupMold nxpGroupMold);

    /**
     * 修改模具&检查组
     * 
     * @param nxpGroupMold 模具&检查组
     * @return 结果
     */
    public int updateNxpGroupMold(NxpGroupMold nxpGroupMold);

    /**
     * 删除模具&检查组
     * 
     * @param id 模具&检查组ID
     * @return 结果
     */
    public int deleteNxpGroupMoldById(Long id);

    /**
     * 批量删除模具&检查组
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpGroupMoldByIds(String[] ids);

    /**
     * 根据moldIds查询绑定的检查组
     * @param moldIds
     * @return
     */
    NxpGroupMold selectByMoldIds(String moldIds);
}
