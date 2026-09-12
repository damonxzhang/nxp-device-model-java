package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmRule;

/**
 * 周PM检查规则Service接口
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
public interface INxpInspectionPmRuleService 
{
    /**
     * 查询周PM检查规则
     * 
     * @param id 周PM检查规则ID
     * @return 周PM检查规则
     */
    public NxpInspectionPmRule selectNxpInspectionPmRuleById(Long id);

    /**
     * 查询周PM检查规则列表
     * 
     * @param nxpInspectionPmRule 周PM检查规则
     * @return 周PM检查规则集合
     */
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleList(NxpInspectionPmRule nxpInspectionPmRule);
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleListNew(NxpInspectionPmRule nxpInspectionPmRule);
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleListGroupBy(NxpInspectionPmRule nxpInspectionPmRule);
    /**
     * 新增周PM检查规则
     * 
     * @param nxpInspectionPmRule 周PM检查规则
     * @return 结果
     */
    public int insertNxpInspectionPmRule(NxpInspectionPmRule nxpInspectionPmRule);

    /**
     * 修改周PM检查规则
     * 
     * @param nxpInspectionPmRule 周PM检查规则
     * @return 结果
     */
    public int updateNxpInspectionPmRule(NxpInspectionPmRule nxpInspectionPmRule);

    /**
     * 批量删除周PM检查规则
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmRuleByIds(String ids);

    /**
     * 删除周PM检查规则信息
     * 
     * @param id 周PM检查规则ID
     * @return 结果
     */
    public int deleteNxpInspectionPmRuleById(Long id);
}
