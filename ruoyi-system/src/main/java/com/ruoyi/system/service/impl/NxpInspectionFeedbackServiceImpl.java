package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionFeedbackMapper;
import com.ruoyi.system.domain.NxpInspectionFeedback;
import com.ruoyi.system.service.INxpInspectionFeedbackService;
import com.ruoyi.common.core.text.Convert;

/**
 * APP反馈Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-04-28
 */
@Service
@DataSource(value = DataSourceType.SLAVE)

public class NxpInspectionFeedbackServiceImpl implements INxpInspectionFeedbackService 
{
    @Autowired
    private NxpInspectionFeedbackMapper nxpInspectionFeedbackMapper;

    /**
     * 查询APP反馈
     * 
     * @param id APP反馈ID
     * @return APP反馈
     */
    @Override
    public NxpInspectionFeedback selectNxpInspectionFeedbackById(Long id)
    {
        return nxpInspectionFeedbackMapper.selectNxpInspectionFeedbackById(id);
    }

    /**
     * 查询APP反馈列表
     * 
     * @param nxpInspectionFeedback APP反馈
     * @return APP反馈
     */
    @Override
    public List<NxpInspectionFeedback> selectNxpInspectionFeedbackList(NxpInspectionFeedback nxpInspectionFeedback)
    {
        return nxpInspectionFeedbackMapper.selectNxpInspectionFeedbackList(nxpInspectionFeedback);
    }

    /**
     * 新增APP反馈
     * 
     * @param nxpInspectionFeedback APP反馈
     * @return 结果
     */
    @Override
    public int insertNxpInspectionFeedback(NxpInspectionFeedback nxpInspectionFeedback)
    {
        return nxpInspectionFeedbackMapper.insertNxpInspectionFeedback(nxpInspectionFeedback);
    }

    /**
     * 修改APP反馈
     * 
     * @param nxpInspectionFeedback APP反馈
     * @return 结果
     */
    @Override
    public int updateNxpInspectionFeedback(NxpInspectionFeedback nxpInspectionFeedback)
    {
        return nxpInspectionFeedbackMapper.updateNxpInspectionFeedback(nxpInspectionFeedback);
    }

    /**
     * 删除APP反馈对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionFeedbackByIds(String ids)
    {
        return nxpInspectionFeedbackMapper.deleteNxpInspectionFeedbackByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除APP反馈信息
     * 
     * @param id APP反馈ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionFeedbackById(Long id)
    {
        return nxpInspectionFeedbackMapper.deleteNxpInspectionFeedbackById(id);
    }
}
