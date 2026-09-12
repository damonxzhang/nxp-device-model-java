package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NxpMoldComponent;

/**
 * 模具部件管理Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-28
 */
public interface INxpMoldComponentService 
{
    /**
     * 查询模具部件管理
     * 
     * @param id 模具部件管理ID
     * @return 模具部件管理
     */
    public NxpMoldComponent selectNxpMoldComponentById(Long id);

    /**
     * 查询模具部件管理列表
     * 
     * @param nxpMoldComponent 模具部件管理
     * @return 模具部件管理集合
     */
    public List<NxpMoldComponent> selectNxpMoldComponentList(NxpMoldComponent nxpMoldComponent);

    /**
     * 新增模具部件管理
     * 
     * @param nxpMoldComponent 模具部件管理
     * @return 结果
     */
    public AjaxResult insertNxpMoldComponent(NxpMoldComponent nxpMoldComponent);

    /**
     * 修改模具部件管理
     * 
     * @param nxpMoldComponent 模具部件管理
     * @return 结果
     */
    public int updateNxpMoldComponent(NxpMoldComponent nxpMoldComponent);

    /**
     * 批量删除模具部件管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldComponentByIds(String ids);

    /**
     * 删除模具部件管理信息
     * 
     * @param id 模具部件管理ID
     * @return 结果
     */
    public int deleteNxpMoldComponentById(Long id);

    /**
     * 根据模具编号查询
     * @param moldCode
     * @return
     */
    List<NxpMoldComponent> selectNxpMoldComponentByMoldCode(String moldCode);

    /**
     * 根据模具id查询
     * @param moldId
     * @return
     */
    List<NxpMoldComponent> selectNxpMoldComponentByMoldId(Long moldId);

    /**
     * 根据部件id查询
     * @param componentId
     * @return
     */
    NxpMoldComponent selectNxpMoldComponentByComponentId(Long componentId);

    /**
     * 根据模具id和部件id查询
     * @param nxpMoldComponent
     * @return
     */
    NxpMoldComponent selectNxpMoldComponentByMoldIdAndComponentId(NxpMoldComponent nxpMoldComponent);

    int deleteNxpMoldComponentByMoldId(Long id);

    int insertNxpMoldComponentApi(NxpMoldComponent nxpMoldComponent);
}
