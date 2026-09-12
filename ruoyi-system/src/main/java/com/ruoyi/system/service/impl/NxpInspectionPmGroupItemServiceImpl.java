package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionPmGroupItemMapper;
import com.ruoyi.system.domain.NxpInspectionPmGroupItem;
import com.ruoyi.system.service.INxpInspectionPmGroupItemService;
import com.ruoyi.common.core.text.Convert;

/**
 * 周PM检查项Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-08
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmGroupItemServiceImpl implements INxpInspectionPmGroupItemService 
{
    @Autowired
    private NxpInspectionPmGroupItemMapper nxpInspectionPmGroupItemMapper;

    /**
     * 查询周PM检查项
     * 
     * @param id 周PM检查项ID
     * @return 周PM检查项
     */
    @Override
    public NxpInspectionPmGroupItem selectNxpInspectionPmGroupItemById(Long id)
    {
        return nxpInspectionPmGroupItemMapper.selectNxpInspectionPmGroupItemById(id);
    }

    /**
     * 查询周PM检查项列表
     * 
     * @param nxpInspectionPmGroupItem 周PM检查项
     * @return 周PM检查项
     */
    @Override
    public List<NxpInspectionPmGroupItem> selectNxpInspectionPmGroupItemList(NxpInspectionPmGroupItem nxpInspectionPmGroupItem)
    {
        return nxpInspectionPmGroupItemMapper.selectNxpInspectionPmGroupItemList(nxpInspectionPmGroupItem);
    }

    /**
     * 新增周PM检查项
     * 
     * @param nxpInspectionPmGroupItem 周PM检查项
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPmGroupItem(NxpInspectionPmGroupItem nxpInspectionPmGroupItem)
    {
        return nxpInspectionPmGroupItemMapper.insertNxpInspectionPmGroupItem(nxpInspectionPmGroupItem);
    }

    /**
     * 修改周PM检查项
     * 
     * @param nxpInspectionPmGroupItem 周PM检查项
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPmGroupItem(NxpInspectionPmGroupItem nxpInspectionPmGroupItem)
    {
        return nxpInspectionPmGroupItemMapper.updateNxpInspectionPmGroupItem(nxpInspectionPmGroupItem);
    }

    /**
     * 删除周PM检查项对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmGroupItemByIds(String ids)
    {
        return nxpInspectionPmGroupItemMapper.deleteNxpInspectionPmGroupItemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周PM检查项信息
     * 
     * @param id 周PM检查项ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmGroupItemById(Long id)
    {
        return nxpInspectionPmGroupItemMapper.deleteNxpInspectionPmGroupItemById(id);
    }
}
