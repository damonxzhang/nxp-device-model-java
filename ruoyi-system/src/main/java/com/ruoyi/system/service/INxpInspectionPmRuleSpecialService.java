package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmRuleSpecial;

/**
 * 周PM规则加日期Service接口
 * 
 * @author DamonZhang
 * @date 2021-06-28
 */
public interface INxpInspectionPmRuleSpecialService 
{
    /**
     * 查询周PM规则加日期
     * 
     * @param id 周PM规则加日期ID
     * @return 周PM规则加日期
     */
    public NxpInspectionPmRuleSpecial selectNxpInspectionPmRuleSpecialById(Long id);

    /**
     * 查询周PM规则加日期列表
     * 
     * @param nxpInspectionPmRuleSpecial 周PM规则加日期
     * @return 周PM规则加日期集合
     */
    public List<NxpInspectionPmRuleSpecial> selectNxpInspectionPmRuleSpecialList(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial);

    /**
     * 新增周PM规则加日期
     * 
     * @param nxpInspectionPmRuleSpecial 周PM规则加日期
     * @return 结果
     */
    public int insertNxpInspectionPmRuleSpecial(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial);

    /**
     * 修改周PM规则加日期
     * 
     * @param nxpInspectionPmRuleSpecial 周PM规则加日期
     * @return 结果
     */
    public int updateNxpInspectionPmRuleSpecial(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial);

    /**
     * 批量删除周PM规则加日期
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmRuleSpecialByIds(String ids);

    /**
     * 删除周PM规则加日期信息
     * 
     * @param id 周PM规则加日期ID
     * @return 结果
     */
    public int deleteNxpInspectionPmRuleSpecialById(Long id);
}
