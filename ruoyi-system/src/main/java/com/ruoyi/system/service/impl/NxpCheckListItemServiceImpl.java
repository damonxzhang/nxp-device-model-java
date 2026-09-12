package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpCheckListItemMapper;
import com.ruoyi.system.domain.NxpCheckListItem;
import com.ruoyi.system.service.INxpCheckListItemService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查列表检查项Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2022-10-02
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpCheckListItemServiceImpl implements INxpCheckListItemService 
{
    @Autowired
    private NxpCheckListItemMapper nxpCheckListItemMapper;

    /**
     * 查询检查列表检查项
     * 
     * @param id 检查列表检查项ID
     * @return 检查列表检查项
     */
    @Override
    public NxpCheckListItem selectNxpCheckListItemById(Long id)
    {
        return nxpCheckListItemMapper.selectNxpCheckListItemById(id);
    }

    /**
     * 查询检查列表检查项列表
     * 
     * @param nxpCheckListItem 检查列表检查项
     * @return 检查列表检查项
     */
    @Override
    public List<NxpCheckListItem> selectNxpCheckListItemList(NxpCheckListItem nxpCheckListItem)
    {
        return nxpCheckListItemMapper.selectNxpCheckListItemList(nxpCheckListItem);
    }

    /**
     * 新增检查列表检查项
     * 
     * @param nxpCheckListItem 检查列表检查项
     * @return 结果
     */
    @Override
    public int insertNxpCheckListItem(NxpCheckListItem nxpCheckListItem)
    {
        nxpCheckListItem.setCreateTime(DateUtils.getNowDate());
        return nxpCheckListItemMapper.insertNxpCheckListItem(nxpCheckListItem);
    }

    /**
     * 修改检查列表检查项
     * 
     * @param nxpCheckListItem 检查列表检查项
     * @return 结果
     */
    @Override
    public int updateNxpCheckListItem(NxpCheckListItem nxpCheckListItem)
    {
        nxpCheckListItem.setUpdateTime(DateUtils.getNowDate());
        return nxpCheckListItemMapper.updateNxpCheckListItem(nxpCheckListItem);
    }

    /**
     * 删除检查列表检查项对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckListItemByIds(String ids)
    {
        return nxpCheckListItemMapper.deleteNxpCheckListItemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查列表检查项信息
     * 
     * @param id 检查列表检查项ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckListItemById(Long id)
    {
        return nxpCheckListItemMapper.deleteNxpCheckListItemById(id);
    }

    @Override
    public List<NxpCheckListItem> selectByCheckListId(Long checkListId) {
        return nxpCheckListItemMapper.selectByCheckListId(checkListId);
    }
}
