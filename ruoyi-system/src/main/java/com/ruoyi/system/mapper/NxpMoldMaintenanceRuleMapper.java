package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldMaintenanceRule;

/**
 * 模具维护规则Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-24
 */
public interface NxpMoldMaintenanceRuleMapper 
{
    /**
     * 查询模具维护规则
     * 
     * @param id 模具维护规则ID
     * @return 模具维护规则
     */
    public NxpMoldMaintenanceRule selectNxpMoldMaintenanceRuleById(Long id);

    /**
     * 查询模具维护规则列表
     * 
     * @param nxpMoldMaintenanceRule 模具维护规则
     * @return 模具维护规则集合
     */
    public List<NxpMoldMaintenanceRule> selectNxpMoldMaintenanceRuleList(NxpMoldMaintenanceRule nxpMoldMaintenanceRule);

    /**
     * 新增模具维护规则
     * 
     * @param nxpMoldMaintenanceRule 模具维护规则
     * @return 结果
     */
    public int insertNxpMoldMaintenanceRule(NxpMoldMaintenanceRule nxpMoldMaintenanceRule);

    /**
     * 修改模具维护规则
     * 
     * @param nxpMoldMaintenanceRule 模具维护规则
     * @return 结果
     */
    public int updateNxpMoldMaintenanceRule(NxpMoldMaintenanceRule nxpMoldMaintenanceRule);

    /**
     * 删除模具维护规则
     * 
     * @param id 模具维护规则ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceRuleById(Long id);

    /**
     * 批量删除模具维护规则
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceRuleByIds(String[] ids);

    /**
     * 根据模具类型查询
     * @param type
     * @return
     */
    NxpMoldMaintenanceRule selectByMoldType(Integer type);
}
