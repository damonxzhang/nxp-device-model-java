package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmRule;

/**
 * 周PM检查规则Mapper接口
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
public interface NxpInspectionPmRuleMapper 
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
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleList1(NxpInspectionPmRule nxpInspectionPmRule);
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleListNew(NxpInspectionPmRule nxpInspectionPmRule);
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleListGroupBy(NxpInspectionPmRule nxpInspectionPmRule);
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleListByIds(String ids);

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
     * 删除周PM检查规则
     * 
     * @param id 周PM检查规则ID
     * @return 结果
     */
    public int deleteNxpInspectionPmRuleById(Long id);

    /**
     * 批量删除周PM检查规则
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmRuleByIds(String[] ids);
}
