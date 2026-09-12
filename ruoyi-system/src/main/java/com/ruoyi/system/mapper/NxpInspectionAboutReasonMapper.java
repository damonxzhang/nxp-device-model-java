package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionAboutReason;

/**
 * 中止维修原因Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-03-01
 */
public interface NxpInspectionAboutReasonMapper 
{
    /**
     * 查询中止维修原因
     * 
     * @param id 中止维修原因ID
     * @return 中止维修原因
     */
    public NxpInspectionAboutReason selectNxpInspectionAboutReasonById(Long id);

    /**
     * 查询中止维修原因列表
     * 
     * @param nxpInspectionAboutReason 中止维修原因
     * @return 中止维修原因集合
     */
    public List<NxpInspectionAboutReason> selectNxpInspectionAboutReasonList(NxpInspectionAboutReason nxpInspectionAboutReason);

    /**
     * 新增中止维修原因
     * 
     * @param nxpInspectionAboutReason 中止维修原因
     * @return 结果
     */
    public int insertNxpInspectionAboutReason(NxpInspectionAboutReason nxpInspectionAboutReason);

    /**
     * 修改中止维修原因
     * 
     * @param nxpInspectionAboutReason 中止维修原因
     * @return 结果
     */
    public int updateNxpInspectionAboutReason(NxpInspectionAboutReason nxpInspectionAboutReason);

    /**
     * 删除中止维修原因
     * 
     * @param id 中止维修原因ID
     * @return 结果
     */
    public int deleteNxpInspectionAboutReasonById(Long id);

    /**
     * 批量删除中止维修原因
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionAboutReasonByIds(String[] ids);
}
