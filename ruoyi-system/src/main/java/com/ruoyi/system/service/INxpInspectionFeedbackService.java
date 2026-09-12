package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionFeedback;

/**
 * APP反馈Service接口
 * 
 * @author DamonZhang
 * @date 2021-04-28
 */
public interface INxpInspectionFeedbackService 
{
    /**
     * 查询APP反馈
     * 
     * @param id APP反馈ID
     * @return APP反馈
     */
    public NxpInspectionFeedback selectNxpInspectionFeedbackById(Long id);

    /**
     * 查询APP反馈列表
     * 
     * @param nxpInspectionFeedback APP反馈
     * @return APP反馈集合
     */
    public List<NxpInspectionFeedback> selectNxpInspectionFeedbackList(NxpInspectionFeedback nxpInspectionFeedback);

    /**
     * 新增APP反馈
     * 
     * @param nxpInspectionFeedback APP反馈
     * @return 结果
     */
    public int insertNxpInspectionFeedback(NxpInspectionFeedback nxpInspectionFeedback);

    /**
     * 修改APP反馈
     * 
     * @param nxpInspectionFeedback APP反馈
     * @return 结果
     */
    public int updateNxpInspectionFeedback(NxpInspectionFeedback nxpInspectionFeedback);

    /**
     * 批量删除APP反馈
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionFeedbackByIds(String ids);

    /**
     * 删除APP反馈信息
     * 
     * @param id APP反馈ID
     * @return 结果
     */
    public int deleteNxpInspectionFeedbackById(Long id);
}
