package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionAboutReasonMapper;
import com.ruoyi.system.domain.NxpInspectionAboutReason;
import com.ruoyi.system.service.INxpInspectionAboutReasonService;
import com.ruoyi.common.core.text.Convert;

/**
 * 中止维修原因Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-03-01
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionAboutReasonServiceImpl implements INxpInspectionAboutReasonService 
{
    @Autowired
    private NxpInspectionAboutReasonMapper nxpInspectionAboutReasonMapper;

    /**
     * 查询中止维修原因
     * 
     * @param id 中止维修原因ID
     * @return 中止维修原因
     */
    @Override
    public NxpInspectionAboutReason selectNxpInspectionAboutReasonById(Long id)
    {
        return nxpInspectionAboutReasonMapper.selectNxpInspectionAboutReasonById(id);
    }

    /**
     * 查询中止维修原因列表
     * 
     * @param nxpInspectionAboutReason 中止维修原因
     * @return 中止维修原因
     */
    @Override
    public List<NxpInspectionAboutReason> selectNxpInspectionAboutReasonList(NxpInspectionAboutReason nxpInspectionAboutReason)
    {
        return nxpInspectionAboutReasonMapper.selectNxpInspectionAboutReasonList(nxpInspectionAboutReason);
    }

    /**
     * 新增中止维修原因
     * 
     * @param nxpInspectionAboutReason 中止维修原因
     * @return 结果
     */
    @Override
    public int insertNxpInspectionAboutReason(NxpInspectionAboutReason nxpInspectionAboutReason)
    {
        nxpInspectionAboutReason.setCreateTime(DateUtils.getNowDate());
        return nxpInspectionAboutReasonMapper.insertNxpInspectionAboutReason(nxpInspectionAboutReason);
    }

    /**
     * 修改中止维修原因
     * 
     * @param nxpInspectionAboutReason 中止维修原因
     * @return 结果
     */
    @Override
    public int updateNxpInspectionAboutReason(NxpInspectionAboutReason nxpInspectionAboutReason)
    {
        nxpInspectionAboutReason.setUpdateTime(DateUtils.getNowDate());
        return nxpInspectionAboutReasonMapper.updateNxpInspectionAboutReason(nxpInspectionAboutReason);
    }

    /**
     * 删除中止维修原因对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionAboutReasonByIds(String ids)
    {
        return nxpInspectionAboutReasonMapper.deleteNxpInspectionAboutReasonByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除中止维修原因信息
     * 
     * @param id 中止维修原因ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionAboutReasonById(Long id)
    {
        return nxpInspectionAboutReasonMapper.deleteNxpInspectionAboutReasonById(id);
    }
}
