package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpCheckListItem;

/**
 * 检查列表检查项Service接口
 * 
 * @author YanCheng Liu
 * @date 2022-10-02
 */
public interface INxpCheckListItemService 
{
    /**
     * 查询检查列表检查项
     * 
     * @param id 检查列表检查项ID
     * @return 检查列表检查项
     */
    public NxpCheckListItem selectNxpCheckListItemById(Long id);

    /**
     * 查询检查列表检查项列表
     * 
     * @param nxpCheckListItem 检查列表检查项
     * @return 检查列表检查项集合
     */
    public List<NxpCheckListItem> selectNxpCheckListItemList(NxpCheckListItem nxpCheckListItem);

    /**
     * 新增检查列表检查项
     * 
     * @param nxpCheckListItem 检查列表检查项
     * @return 结果
     */
    public int insertNxpCheckListItem(NxpCheckListItem nxpCheckListItem);

    /**
     * 修改检查列表检查项
     * 
     * @param nxpCheckListItem 检查列表检查项
     * @return 结果
     */
    public int updateNxpCheckListItem(NxpCheckListItem nxpCheckListItem);

    /**
     * 批量删除检查列表检查项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpCheckListItemByIds(String ids);

    /**
     * 删除检查列表检查项信息
     * 
     * @param id 检查列表检查项ID
     * @return 结果
     */
    public int deleteNxpCheckListItemById(Long id);

    /**
     * 根据检查id查询检查项
     * @param checkListId
     * @return
     */
    public List<NxpCheckListItem> selectByCheckListId(Long checkListId);
}
