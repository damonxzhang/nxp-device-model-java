package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldComponent;

/**
 * 模具部件管理Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-28
 */
public interface NxpMoldComponentMapper 
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
    public int insertNxpMoldComponent(NxpMoldComponent nxpMoldComponent);

    /**
     * 修改模具部件管理
     * 
     * @param nxpMoldComponent 模具部件管理
     * @return 结果
     */
    public int updateNxpMoldComponent(NxpMoldComponent nxpMoldComponent);

    /**
     * 删除模具部件管理
     * 
     * @param id 模具部件管理ID
     * @return 结果
     */
    public int deleteNxpMoldComponentById(Long id);

    /**
     * 批量删除模具部件管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldComponentByIds(String[] ids);

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
     * 删除模具绑定的模具种类和部件，统一设置为逻辑删除
     * @param nxpMoldComponent
     * @return
     */
    int updateNxpMoldComponentByDel(NxpMoldComponent nxpMoldComponent);

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

    int deleteNxpMoldComponentByMoldId(Long moldId);
}
