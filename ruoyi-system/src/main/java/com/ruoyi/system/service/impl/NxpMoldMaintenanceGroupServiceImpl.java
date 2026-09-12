package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;
import com.ruoyi.system.mapper.NxpMoldMaintenanceGroupMapper;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具维护分组Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldMaintenanceGroupServiceImpl implements INxpMoldMaintenanceGroupService 
{
    @Resource
    private NxpMoldMaintenanceGroupMapper nxpMoldMaintenanceGroupMapper;

    /**
     * 查询模具维护分组
     * 
     * @param id 模具维护分组ID
     * @return 模具维护分组
     */
    @Override
    public NxpMoldMaintenanceGroup selectNxpMoldMaintenanceGroupById(Long id)
    {
        return nxpMoldMaintenanceGroupMapper.selectNxpMoldMaintenanceGroupById(id);
    }

    /**
     * 查询模具维护分组列表
     * 
     * @param nxpMoldMaintenanceGroup 模具维护分组
     * @return 模具维护分组
     */
    @Override
    public List<NxpMoldMaintenanceGroup> selectNxpMoldMaintenanceGroupList(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        return nxpMoldMaintenanceGroupMapper.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
    }

    /**
     * 新增模具维护分组
     * 
     * @param nxpMoldMaintenanceGroup 模具维护分组
     * @return 结果
     */
    @Transactional
    @Override
    public int insertNxpMoldMaintenanceGroup(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        nxpMoldMaintenanceGroup.setCreateTime(DateUtils.getNowDate());
        int rows = nxpMoldMaintenanceGroupMapper.insertNxpMoldMaintenanceGroup(nxpMoldMaintenanceGroup);
//        insertNxpMoldMaintenanceGroupItem(nxpMoldMaintenanceGroup);
        return rows;
    }

    /**
     * 修改模具维护分组
     * 
     * @param nxpMoldMaintenanceGroup 模具维护分组
     * @return 结果
     */
    @Transactional
    @Override
    public int updateNxpMoldMaintenanceGroup(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        nxpMoldMaintenanceGroup.setUpdateTime(DateUtils.getNowDate());
//        nxpMoldMaintenanceGroupMapper.deleteNxpMoldMaintenanceGroupItemByGroupId(nxpMoldMaintenanceGroup.getId());
//        insertNxpMoldMaintenanceGroupItem(nxpMoldMaintenanceGroup);
        return nxpMoldMaintenanceGroupMapper.updateNxpMoldMaintenanceGroup(nxpMoldMaintenanceGroup);
    }

    /**
     * 删除模具维护分组对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteNxpMoldMaintenanceGroupByIds(String ids)
    {
//        nxpMoldMaintenanceGroupMapper.deleteNxpMoldMaintenanceGroupItemByGroupIds(Convert.toStrArray(ids));
        return nxpMoldMaintenanceGroupMapper.deleteNxpMoldMaintenanceGroupByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具维护分组信息
     * 
     * @param id 模具维护分组ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceGroupById(Long id)
    {
        nxpMoldMaintenanceGroupMapper.deleteNxpMoldMaintenanceGroupItemByGroupId(id);
        return nxpMoldMaintenanceGroupMapper.deleteNxpMoldMaintenanceGroupById(id);
    }

    /**
     * 根据id查询groupItem
     * @param id
     * @return
     */
    @Override
    public NxpMoldMaintenanceGroupItem selectGroupItemById(Long id) {
        return nxpMoldMaintenanceGroupMapper.selectGroupItemById(id);
    }

    /**
     * 新增模具检查项信息
     * 
     * @param nxpMoldMaintenanceGroup 模具维护分组对象
     */
    public void insertNxpMoldMaintenanceGroupItem(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        List<NxpMoldMaintenanceGroupItem> nxpMoldMaintenanceGroupItemList = nxpMoldMaintenanceGroup.getNxpMoldMaintenanceGroupItemList();
        Long id = nxpMoldMaintenanceGroup.getId();
        if (StringUtils.isNotNull(nxpMoldMaintenanceGroupItemList))
        {
            List<NxpMoldMaintenanceGroupItem> list = new ArrayList<NxpMoldMaintenanceGroupItem>();
            for (NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem : nxpMoldMaintenanceGroupItemList)
            {
                nxpMoldMaintenanceGroupItem.setGroupId(id);
                list.add(nxpMoldMaintenanceGroupItem);
            }
            if (list.size() > 0)
            {
                nxpMoldMaintenanceGroupMapper.batchNxpMoldMaintenanceGroupItem(list);
            }
        }
    }
}
