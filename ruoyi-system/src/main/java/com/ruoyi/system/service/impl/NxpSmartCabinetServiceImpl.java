package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpSmartCabinetMapper;
import com.ruoyi.system.domain.NxpSmartCabinet;
import com.ruoyi.system.service.INxpSmartCabinetService;
import com.ruoyi.common.core.text.Convert;

/**
 * 智能柜管理Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-06-24
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpSmartCabinetServiceImpl implements INxpSmartCabinetService 
{
    @Autowired
    private NxpSmartCabinetMapper nxpSmartCabinetMapper;

    /**
     * 查询智能柜管理
     * 
     * @param id 智能柜管理ID
     * @return 智能柜管理
     */
    @Override
    public NxpSmartCabinet selectNxpSmartCabinetById(Long id)
    {
        return nxpSmartCabinetMapper.selectNxpSmartCabinetById(id);
    }

    /**
     * 查询智能柜管理列表
     * 
     * @param nxpSmartCabinet 智能柜管理
     * @return 智能柜管理
     */
    @Override
    public List<NxpSmartCabinet> selectNxpSmartCabinetList(NxpSmartCabinet nxpSmartCabinet)
    {
        return nxpSmartCabinetMapper.selectNxpSmartCabinetList(nxpSmartCabinet);
    }

    /**
     * 新增智能柜管理
     * 
     * @param nxpSmartCabinet 智能柜管理
     * @return 结果
     */
    @Override
    public int insertNxpSmartCabinet(NxpSmartCabinet nxpSmartCabinet)
    {
        nxpSmartCabinet.setCreateTime(DateUtils.getNowDate());
        return nxpSmartCabinetMapper.insertNxpSmartCabinet(nxpSmartCabinet);
    }

    /**
     * 修改智能柜管理
     * 
     * @param nxpSmartCabinet 智能柜管理
     * @return 结果
     */
    @Override
    public int updateNxpSmartCabinet(NxpSmartCabinet nxpSmartCabinet)
    {
        nxpSmartCabinet.setUpdateTime(DateUtils.getNowDate());
        return nxpSmartCabinetMapper.updateNxpSmartCabinet(nxpSmartCabinet);
    }

    /**
     * 删除智能柜管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpSmartCabinetByIds(String ids)
    {
        return nxpSmartCabinetMapper.deleteNxpSmartCabinetByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除智能柜管理信息
     * 
     * @param id 智能柜管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpSmartCabinetById(Long id)
    {
        return nxpSmartCabinetMapper.deleteNxpSmartCabinetById(id);
    }
}
