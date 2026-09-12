package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.domain.NxpMoldType;
import com.ruoyi.system.domain.NxpMoldTypeComponent;
import com.ruoyi.system.mapper.NxpMoldMaintenanceGroupMapper;
import com.ruoyi.system.mapper.NxpMoldTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldMaintenanceGroupItemMapper;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupItemService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具维护检查项Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-08-22
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldMaintenanceGroupItemServiceImpl implements INxpMoldMaintenanceGroupItemService 
{
    @Resource
    private NxpMoldMaintenanceGroupItemMapper nxpMoldMaintenanceGroupItemMapper;
    @Resource
    private NxpMoldMaintenanceGroupMapper nxpMoldMaintenanceGroupMapper;
    @Resource
    private NxpMoldTypeMapper nxpMoldTypeMapper;


    /**
     * 查询模具维护检查项
     * 
     * @param id 模具维护检查项ID
     * @return 模具维护检查项
     */
    @Override
    public NxpMoldMaintenanceGroupItem selectNxpMoldMaintenanceGroupItemById(Long id)
    {
        return nxpMoldMaintenanceGroupItemMapper.selectNxpMoldMaintenanceGroupItemById(id);
    }

    /**
     * 查询模具维护检查项列表
     * 
     * @param nxpMoldMaintenanceGroupItem 模具维护检查项
     * @return 模具维护检查项
     */
    @Override
    public List<NxpMoldMaintenanceGroupItem> selectNxpMoldMaintenanceGroupItemList(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem)
    {
        List<NxpMoldMaintenanceGroupItem> nxpMoldMaintenanceGroupItems = nxpMoldMaintenanceGroupItemMapper.selectNxpMoldMaintenanceGroupItemList(nxpMoldMaintenanceGroupItem);
        for (NxpMoldMaintenanceGroupItem moldMaintenanceGroupItem : nxpMoldMaintenanceGroupItems) {
            NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupMapper.selectNxpMoldMaintenanceGroupById(moldMaintenanceGroupItem.getGroupId());
            moldMaintenanceGroupItem.setGroupName(nxpMoldMaintenanceGroup.getGroupName());
            if (moldMaintenanceGroupItem.getMoldTypeId() != null){
                NxpMoldType nxpMoldType = nxpMoldTypeMapper.selectNxpMoldTypeById(moldMaintenanceGroupItem.getMoldTypeId());
                moldMaintenanceGroupItem.setMoldTypeName(nxpMoldType.getMoldTypeName());
            }
            if (moldMaintenanceGroupItem.getComponentId() != null){
                NxpMoldTypeComponent nxpMoldTypeComponent = nxpMoldTypeMapper.getByComponentId(moldMaintenanceGroupItem.getComponentId());
                moldMaintenanceGroupItem.setComponentName(nxpMoldTypeComponent.getComponentName());
            }
        }
        return nxpMoldMaintenanceGroupItems;
    }

    /**
     * 新增模具维护检查项
     * 
     * @param nxpMoldMaintenanceGroupItem 模具维护检查项
     * @return 结果
     */
    @Override
    public int insertNxpMoldMaintenanceGroupItem(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem)
    {
        nxpMoldMaintenanceGroupItem.setCreateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceGroupItemMapper.insertNxpMoldMaintenanceGroupItem(nxpMoldMaintenanceGroupItem);
    }

    /**
     * 修改模具维护检查项
     * 
     * @param nxpMoldMaintenanceGroupItem 模具维护检查项
     * @return 结果
     */
    @Override
    public int updateNxpMoldMaintenanceGroupItem(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem)
    {
        nxpMoldMaintenanceGroupItem.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceGroupItemMapper.updateNxpMoldMaintenanceGroupItem(nxpMoldMaintenanceGroupItem);
    }

    /**
     * 删除模具维护检查项对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceGroupItemByIds(String ids)
    {
        return nxpMoldMaintenanceGroupItemMapper.deleteNxpMoldMaintenanceGroupItemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具维护检查项信息
     * 
     * @param id 模具维护检查项ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceGroupItemById(Long id)
    {
        return nxpMoldMaintenanceGroupItemMapper.deleteNxpMoldMaintenanceGroupItemById(id);
    }
}
