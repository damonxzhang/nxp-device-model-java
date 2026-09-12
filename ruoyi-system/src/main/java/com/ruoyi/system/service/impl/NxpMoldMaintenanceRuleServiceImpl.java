package com.ruoyi.system.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldMaintenance;
import com.ruoyi.system.mapper.NxpMoldMaintenanceMapper;
import com.ruoyi.system.mapper.NxpMoldMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldMaintenanceRuleMapper;
import com.ruoyi.system.domain.NxpMoldMaintenanceRule;
import com.ruoyi.system.service.INxpMoldMaintenanceRuleService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具维护规则Service业务层处理
 *
 * @author YanCheng Liu
 * @date 2023-07-24
 */
@Slf4j
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldMaintenanceRuleServiceImpl implements INxpMoldMaintenanceRuleService {
    @Resource
    private NxpMoldMaintenanceRuleMapper nxpMoldMaintenanceRuleMapper;
    @Resource
    private NxpMoldMapper nxpMoldMapper;
    @Resource
    private NxpMoldMaintenanceMapper nxpMoldMaintenanceMapper;

    /**
     * 查询模具维护规则
     *
     * @param id 模具维护规则ID
     * @return 模具维护规则
     */
    @Override
    public NxpMoldMaintenanceRule selectNxpMoldMaintenanceRuleById(Long id) {
        return nxpMoldMaintenanceRuleMapper.selectNxpMoldMaintenanceRuleById(id);
    }

    /**
     * 查询模具维护规则列表
     *
     * @param nxpMoldMaintenanceRule 模具维护规则
     * @return 模具维护规则
     */
    @Override
    public List<NxpMoldMaintenanceRule> selectNxpMoldMaintenanceRuleList(NxpMoldMaintenanceRule nxpMoldMaintenanceRule) {
        return nxpMoldMaintenanceRuleMapper.selectNxpMoldMaintenanceRuleList(nxpMoldMaintenanceRule);
    }

    /**
     * 新增模具维护规则
     *
     * @param nxpMoldMaintenanceRule 模具维护规则
     * @return 结果
     */
    @Override
    public int insertNxpMoldMaintenanceRule(NxpMoldMaintenanceRule nxpMoldMaintenanceRule) {
        // 根据周期和开始时间计算应执行时间
        Long cycle = nxpMoldMaintenanceRule.getCycle();
        Date beginTime = nxpMoldMaintenanceRule.getBeginTime();
        LocalDate beginLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, beginTime));
        // 提前时间
        Long advance = nxpMoldMaintenanceRule.getAdvanceNum();
        LocalDate shouldExecuteDate = beginLocalDate.plusDays(cycle - advance);
        Date convertDate = Date.from(shouldExecuteDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        nxpMoldMaintenanceRule.setShouldExecuteTime(convertDate);

        nxpMoldMaintenanceRule.setCreateTime(DateUtils.getNowDate());

        if (nxpMoldMaintenanceRule.getMoldId() != null) {
            Long moldId = nxpMoldMaintenanceRule.getMoldId();
            NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(moldId);
            if (nxpMold != null) {
                nxpMoldMaintenanceRule.setMoldCode(nxpMold.getMoldCode());
            }
        }
        int i = nxpMoldMaintenanceRuleMapper.insertNxpMoldMaintenanceRule(nxpMoldMaintenanceRule);

        // 获取当前时间,如果设置的规则开始时间等于当前时间，那么直接执行一次
        LocalDate currentDate = LocalDate.now();
        if (currentDate.equals(beginLocalDate)){
            immediately(nxpMoldMaintenanceRule);
        }
        return i;
    }

    /**
     * 修改模具维护规则
     *
     * @param nxpMoldMaintenanceRule 模具维护规则
     * @return 结果
     */
    @Override
    public int updateNxpMoldMaintenanceRule(NxpMoldMaintenanceRule nxpMoldMaintenanceRule) {
        // 根据周期和开始时间计算应执行时间
        Long cycle = nxpMoldMaintenanceRule.getCycle();
        Date beginTime = nxpMoldMaintenanceRule.getBeginTime();
        LocalDate beginLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, beginTime));
        // 提前时间
        Long advance = nxpMoldMaintenanceRule.getAdvanceNum();
        // 判断实际执行时间字段是否为空
        Date convertDate = null;
        Date practicalTime = nxpMoldMaintenanceRule.getPracticalTime();
        if (practicalTime != null){
            LocalDate practicalTimeLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, practicalTime));
            if (practicalTimeLocalDate.getYear() != 1900){
                LocalDate shouldExecuteDate = practicalTimeLocalDate.plusDays(cycle - advance);
                convertDate = Date.from(shouldExecuteDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }else {
                LocalDate shouldExecuteDate = beginLocalDate.plusDays(cycle - advance);
                convertDate = Date.from(shouldExecuteDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        }else {
            LocalDate shouldExecuteDate = beginLocalDate.plusDays(cycle - advance);
            convertDate = Date.from(shouldExecuteDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        nxpMoldMaintenanceRule.setShouldExecuteTime(convertDate);
        nxpMoldMaintenanceRule.setUpdateTime(DateUtils.getNowDate());
        if (nxpMoldMaintenanceRule.getMoldId() != null) {
            Long moldId = nxpMoldMaintenanceRule.getMoldId();
            NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(moldId);
            if (nxpMold != null) {
                nxpMoldMaintenanceRule.setMoldCode(nxpMold.getMoldCode());
            }
        }
        int i = nxpMoldMaintenanceRuleMapper.updateNxpMoldMaintenanceRule(nxpMoldMaintenanceRule);

        // 获取当前时间,如果设置的规则开始时间等于当前时间，那么直接执行一次
        LocalDate currentDate = LocalDate.now();
        if (currentDate.equals(beginLocalDate)){
            immediately(nxpMoldMaintenanceRule);
        }
        return i;
    }

    @Override
    public int updateNxpMoldMaintenanceRuleTask(NxpMoldMaintenanceRule nxpMoldMaintenanceRule) {
        return nxpMoldMaintenanceRuleMapper.updateNxpMoldMaintenanceRule(nxpMoldMaintenanceRule);
    }

    /**
     * 删除模具维护规则对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceRuleByIds(String ids) {
        return nxpMoldMaintenanceRuleMapper.deleteNxpMoldMaintenanceRuleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具维护规则信息
     *
     * @param id 模具维护规则ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceRuleById(Long id) {
        return nxpMoldMaintenanceRuleMapper.deleteNxpMoldMaintenanceRuleById(id);
    }

    /**
     * 根据模具类型查询
     *
     * @param type
     * @return
     */
    @Override
    public NxpMoldMaintenanceRule selectByMoldType(Integer type) {
        return nxpMoldMaintenanceRuleMapper.selectByMoldType(type);
    }

    /**
     * 如果规则时间为当天，那么立刻执行
     */
    private void immediately(NxpMoldMaintenanceRule moldMaintenanceRule) {
        // 可以生成维护记录
        NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(moldMaintenanceRule.getMoldId());
        if (nxpMold != null) {
            // 维护列表数据
            NxpMoldMaintenance nxpMoldMaintenanceParams = new NxpMoldMaintenance();
            nxpMoldMaintenanceParams.setMoldId(nxpMold.getId());
            nxpMoldMaintenanceParams.setMoldCode(nxpMold.getMoldCode());
            nxpMoldMaintenanceParams.setMoldRmsCode(nxpMold.getMoldRmsCode());
            nxpMoldMaintenanceParams.setMoldBoxCode(nxpMold.getMoldCode());
            nxpMoldMaintenanceParams.setCheckTime(DateUtils.getCurrentByHour(12));
            nxpMoldMaintenanceParams.setStatus(1);
            nxpMoldMaintenanceParams.setMaintenanceType(moldMaintenanceRule.getType());
            nxpMoldMaintenanceParams.setMoldType(nxpMold.getMoldType());
            nxpMoldMaintenanceParams.setCreateTime(DateUtils.getNowDate());
            nxpMoldMaintenanceParams.setOverdueState(0);
            nxpMoldMaintenanceParams.setRuleId(moldMaintenanceRule.getId());
            nxpMoldMaintenanceMapper.insertNxpMoldMaintenance(nxpMoldMaintenanceParams);
        }
        // 实际执行时间
        moldMaintenanceRule.setPracticalTime(DateUtils.getNowDate());
        nxpMoldMaintenanceRuleMapper.updateNxpMoldMaintenanceRule(moldMaintenanceRule);
    }
}
