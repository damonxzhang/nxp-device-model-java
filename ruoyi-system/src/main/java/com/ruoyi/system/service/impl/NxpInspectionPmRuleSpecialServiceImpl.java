package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionPmRuleSpecialMapper;
import com.ruoyi.system.domain.NxpInspectionPmRuleSpecial;
import com.ruoyi.system.service.INxpInspectionPmRuleSpecialService;
import com.ruoyi.common.core.text.Convert;

/**
 * 周PM规则加日期Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-06-28
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmRuleSpecialServiceImpl implements INxpInspectionPmRuleSpecialService 
{
    @Autowired
    private NxpInspectionPmRuleSpecialMapper nxpInspectionPmRuleSpecialMapper;

    /**
     * 查询周PM规则加日期
     * 
     * @param id 周PM规则加日期ID
     * @return 周PM规则加日期
     */
    @Override
    public NxpInspectionPmRuleSpecial selectNxpInspectionPmRuleSpecialById(Long id)
    {
        return nxpInspectionPmRuleSpecialMapper.selectNxpInspectionPmRuleSpecialById(id);
    }

    /**
     * 查询周PM规则加日期列表
     * 
     * @param nxpInspectionPmRuleSpecial 周PM规则加日期
     * @return 周PM规则加日期
     */
    @Override
    public List<NxpInspectionPmRuleSpecial> selectNxpInspectionPmRuleSpecialList(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial)
    {
        return nxpInspectionPmRuleSpecialMapper.selectNxpInspectionPmRuleSpecialList(nxpInspectionPmRuleSpecial);
    }

    /**
     * 新增周PM规则加日期
     * 
     * @param nxpInspectionPmRuleSpecial 周PM规则加日期
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPmRuleSpecial(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial)
    {
        return nxpInspectionPmRuleSpecialMapper.insertNxpInspectionPmRuleSpecial(nxpInspectionPmRuleSpecial);
    }

    /**
     * 修改周PM规则加日期
     * 
     * @param nxpInspectionPmRuleSpecial 周PM规则加日期
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPmRuleSpecial(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial)
    {
        return nxpInspectionPmRuleSpecialMapper.updateNxpInspectionPmRuleSpecial(nxpInspectionPmRuleSpecial);
    }

    /**
     * 删除周PM规则加日期对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmRuleSpecialByIds(String ids)
    {
        return nxpInspectionPmRuleSpecialMapper.deleteNxpInspectionPmRuleSpecialByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周PM规则加日期信息
     * 
     * @param id 周PM规则加日期ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmRuleSpecialById(Long id)
    {
        return nxpInspectionPmRuleSpecialMapper.deleteNxpInspectionPmRuleSpecialById(id);
    }
}
