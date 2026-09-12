package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpCheckListMapper;
import com.ruoyi.system.domain.NxpCheckList;
import com.ruoyi.system.service.INxpCheckListService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查记录Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpCheckListServiceImpl implements INxpCheckListService 
{
    @Autowired
    private NxpCheckListMapper nxpCheckListMapper;

    /**
     * 查询检查记录
     * 
     * @param id 检查记录ID
     * @return 检查记录
     */
    @Override
    public NxpCheckList selectNxpCheckListById(Long id)
    {
        return nxpCheckListMapper.selectNxpCheckListById(id);
    }

    /**
     * 查询检查记录列表
     * 
     * @param nxpCheckList 检查记录
     * @return 检查记录
     */
    @Override
    public List<NxpCheckList> selectNxpCheckListList(NxpCheckList nxpCheckList)
    {
        return nxpCheckListMapper.selectNxpCheckListList(nxpCheckList);
    }

    @Override
    public List<NxpCheckList> selectCheckList(NxpCheckList nxpCheckList) {
        return nxpCheckListMapper.selectCheckList(nxpCheckList);
    }

    /**
     * 新增检查记录
     * 
     * @param nxpCheckList 检查记录
     * @return 结果
     */
    @Override
    public int insertNxpCheckList(NxpCheckList nxpCheckList)
    {
        nxpCheckList.setCreateTime(DateUtils.getNowDate());
        return nxpCheckListMapper.insertNxpCheckList(nxpCheckList);
    }

    /**
     * 修改检查记录
     * 
     * @param nxpCheckList 检查记录
     * @return 结果
     */
    @Override
    public int updateNxpCheckList(NxpCheckList nxpCheckList)
    {
        return nxpCheckListMapper.updateNxpCheckList(nxpCheckList);
    }

    /**
     * 删除检查记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckListByIds(String ids)
    {
        return nxpCheckListMapper.deleteNxpCheckListByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查记录信息
     * 
     * @param id 检查记录ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckListById(Long id)
    {
        return nxpCheckListMapper.deleteNxpCheckListById(id);
    }
}
