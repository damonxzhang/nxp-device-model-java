package com.ruoyi.quartz.task;

import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LocalDateUtils;
import com.ruoyi.quartz.util.AbstractQuartzJob;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 模具维护定时任务
 *
 * @author snkj
 * @create 2023-07-24 10:45
 */
@Component("moldMaintenanceTask")
public class MoldMaintenanceTask {
    private static final Logger log = LoggerFactory.getLogger(MoldMaintenanceTask.class);
    @Resource
    private INxpMoldMaintenanceRuleService nxpMoldMaintenanceRuleService;
    @Resource
    private INxpMoldService nxpMoldService;
    @Resource
    private INxpMoldMaintenanceService nxpMoldMaintenanceService;
    @Resource
    private IReplacedListService replacedListService;
    @Resource
    private INxpMoldReturnService nxpMoldReturnService;

    /**
     * 模具维护生成定时任务
     */
    public void moldMaintenanceQuartz(){
        // 1. 先查询所有已启用的规则
        NxpMoldMaintenanceRule nxpMoldMaintenanceRule = new NxpMoldMaintenanceRule();
        nxpMoldMaintenanceRule.setStatus(1L);
        List<NxpMoldMaintenanceRule> nxpMoldMaintenanceRules = nxpMoldMaintenanceRuleService.selectNxpMoldMaintenanceRuleList(nxpMoldMaintenanceRule);
        if (nxpMoldMaintenanceRules.size() == 0){
            log.error("定时任务中规则为空");
            return;
        }
        // 2. 遍历所有规则，根据不同的规则生成维护信息
        for (NxpMoldMaintenanceRule moldMaintenanceRule : nxpMoldMaintenanceRules) {
            // 执行周期
            Long cycle = moldMaintenanceRule.getCycle();
            // 规则开始时间
            Date beginTime = moldMaintenanceRule.getBeginTime();
            LocalDate beginTimeLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, beginTime));
            // 获取当前时间
            LocalDate currentDate = LocalDate.now();
            // 提前天数
            Long advance = moldMaintenanceRule.getAdvanceNum();
            // 根据规则id查询最新一条已完成的维护，获取到最新的实际维护检查时间
            LocalDate finalExecuteLocalDate = null;
//            Long ruleId = moldMaintenanceRule.getId();
            Date shouldExecuteTime = moldMaintenanceRule.getShouldExecuteTime();
            /*NxpMoldMaintenance moldMaintenance = new NxpMoldMaintenance();
            moldMaintenance.setRuleId(ruleId);
            moldMaintenance.setMoldId(moldMaintenanceRule.getMoldId());
            List<NxpMoldMaintenance> nxpMoldMaintenances = nxpMoldMaintenanceService.selectNxpMoldMaintenanceListTask(moldMaintenance);
            if (nxpMoldMaintenances != null && nxpMoldMaintenances.size() > 0){
                NxpMoldMaintenance nxpMoldMaintenance = nxpMoldMaintenances.get(0);
                Date checkTime = nxpMoldMaintenance.getCheckTime();
                LocalDate realCheckTimeLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, checkTime));
                // 最新的实际维护检查时间 + （周期 - 提前时间） = 最新的执行生成维护记录时间
                finalExecuteLocalDate = realCheckTimeLocalDate.plusDays(cycle + advance);
            }else {
                finalExecuteLocalDate = beginTimeLocalDate.minusDays(advance);
            }*/
            // ShouldExecuteTime = beginTime + （周期）
            // 实际执行时间=shouldTime + （周期 - 提前时间）
            // shouldTime = shouldTime + 周期
            LocalDate nextShouldExecuteDate = null;
            if (shouldExecuteTime == null){
                finalExecuteLocalDate = beginTimeLocalDate.minusDays(advance);
//                finalExecuteLocalDate = beginTimeLocalDate.plusDays(cycle - advance);
                nextShouldExecuteDate = beginTimeLocalDate.plusDays(cycle);
            }else {
                LocalDate shouldExecuteTimeLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, shouldExecuteTime));
                finalExecuteLocalDate = shouldExecuteTimeLocalDate.minusDays(advance);
//                finalExecuteLocalDate = shouldExecuteTimeLocalDate.plusDays(cycle - advance);
                nextShouldExecuteDate = shouldExecuteTimeLocalDate.plusDays(cycle);
            }
            // 判断当前时间是否等于应执行时间
            if (LocalDateUtils.localDateIsEqual(finalExecuteLocalDate,currentDate) || LocalDateUtils.localDateIsBefore(finalExecuteLocalDate,currentDate)){ // 相等
                // 可以生成维护记录
                NxpMold nxpMold = nxpMoldService.selectNxpMoldById(moldMaintenanceRule.getMoldId());
                if (nxpMold != null){
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
                    nxpMoldMaintenanceParams.setRuleId(moldMaintenanceRule.getId());
                    nxpMoldMaintenanceParams.setOverdueState(0);
                    nxpMoldMaintenanceService.insertNxpMoldMaintenance(nxpMoldMaintenanceParams);

                    nxpMold.setFacilityId(0);
                    nxpMoldService.updateNxpMoldApi(nxpMold);
                }

                // 到这里表示维护代码已执行，重新维护应执行时间
                Date convertDate = Date.from(nextShouldExecuteDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                moldMaintenanceRule.setShouldExecuteTime(convertDate);

                // 实际执行时间
                moldMaintenanceRule.setPracticalTime(DateUtils.getNowDate());
                nxpMoldMaintenanceRuleService.updateNxpMoldMaintenanceRuleTask(moldMaintenanceRule);
            }
        }
    }

    /**
     * 检查逾期
     */
    public void overdue(){
        // 1. 先查询所有未检查的维护记录
        NxpMoldMaintenance nxpMoldMaintenanceParams = new NxpMoldMaintenance();
        nxpMoldMaintenanceParams.setStatus(1);
        nxpMoldMaintenanceParams.setPageNum(null);
        nxpMoldMaintenanceParams.setPageSize(null);
        List<NxpMoldMaintenance> nxpMoldMaintenances = nxpMoldMaintenanceService.selectNxpMoldMaintenanceList(nxpMoldMaintenanceParams);
        for (NxpMoldMaintenance nxpMoldMaintenance : nxpMoldMaintenances) {
            // 2. 根据模具id查询该模具的规则
            NxpMoldMaintenanceRule nxpMoldMaintenanceRule = nxpMoldMaintenanceRuleService.selectNxpMoldMaintenanceRuleById(nxpMoldMaintenance.getRuleId());
            // 3. 获取周期，判断是否在这个时间内
            Long cycle = nxpMoldMaintenanceRule.getCycle();
            Date checkTime = nxpMoldMaintenance.getCheckTime();
            LocalDate checkTimeLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, checkTime));
            // 获取当前时间
            LocalDate currentDate = LocalDate.now();
//            LocalDate finalCheckTime = checkTimeLocalDate.plusDays(cycle);
            if (checkTimeLocalDate.isBefore(currentDate)){
                // 表示逾期
                nxpMoldMaintenance.setOverdueState(1);
                nxpMoldMaintenance.setStatus(3);
                nxpMoldMaintenanceService.updateNxpMoldMaintenance(nxpMoldMaintenance);
            }
        }
    }

    public void baMoldQuartz(){
        // 1. 查询昨天7点到今天7点，待更换列表中的ba模具
        ReplacedList replacedList = new ReplacedList();
        // 获取昨天7点
        String yesterday7 = DateUtils.getYesterday7();
        // 获取今天7点
        String today7 = DateUtils.getToday7();
        replacedList.setMoldType(2);
        replacedList.setStatus(4);
        replacedList.getParams().put("beginTime",yesterday7);
        replacedList.getParams().put("endTime",today7);
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<ReplacedList> lists = replacedListService.selectReplacedListListQuartz(replacedList);
        DynamicDataSourceContextHolder.clearDataSourceType();
        for (ReplacedList replaced : lists) {
            /*Long moldId = replaced.getMoldId();
            NxpMold nxpMold = nxpMoldService.selectNxpMoldById(moldId);
            if (nxpMold != null){
                // 先查询维护表有无待维护的模具
                NxpMoldMaintenance params = new NxpMoldMaintenance();
                params.setMoldId(nxpMold.getId());
                params.setStatus(1);
                List<NxpMoldMaintenance> nxpMoldMaintenances = nxpMoldMaintenanceService.selectNxpMoldMaintenanceList(params);
                if (nxpMoldMaintenances == null || nxpMoldMaintenances.size() == 0){
                    // 插入待维护表
                    NxpMoldMaintenance nxpMoldMaintenanceParams = new NxpMoldMaintenance();
                    nxpMoldMaintenanceParams.setMoldId(nxpMold.getId());
                    nxpMoldMaintenanceParams.setMoldCode(nxpMold.getMoldCode());
                    nxpMoldMaintenanceParams.setMoldRmsCode(nxpMold.getMoldRmsCode());
                    nxpMoldMaintenanceParams.setMoldBoxCode(nxpMold.getMoldCode());
                    nxpMoldMaintenanceParams.setCheckTime(DateUtils.getCurrentByHour(12));
                    nxpMoldMaintenanceParams.setStatus(1);
                    nxpMoldMaintenanceParams.setMaintenanceType(4);
                    nxpMoldMaintenanceParams.setMoldType(nxpMold.getMoldType());
                    nxpMoldMaintenanceParams.setOverdueState(0);
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldMaintenanceService.insertNxpMoldMaintenance(nxpMoldMaintenanceParams);
                    DynamicDataSourceContextHolder.clearDataSourceType();

                    // 模具改为待维护状态，并与设备解绑
                    nxpMold.setStatus(5);
                    nxpMold.setFacilityId(0);
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldService.updateNxpMoldApi(nxpMold);
                    DynamicDataSourceContextHolder.clearDataSourceType();
                }
            }*/

            //
            Long newMoldId = replaced.getNewMoldId();
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            NxpMold newMold = nxpMoldService.selectNxpMoldById(newMoldId);
            DynamicDataSourceContextHolder.clearDataSourceType();
            if (newMold != null){
                // 先查询维护表有无待维护的模具
                NxpMoldMaintenance params = new NxpMoldMaintenance();
                params.setMoldId(newMold.getId());
                params.setStatus(1);
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                List<NxpMoldMaintenance> nxpMoldMaintenances = nxpMoldMaintenanceService.selectNxpMoldMaintenanceList(params);
                DynamicDataSourceContextHolder.clearDataSourceType();
                if (nxpMoldMaintenances == null || nxpMoldMaintenances.size() == 0){
                    // 插入待维护表
                    NxpMoldMaintenance nxpMoldMaintenanceParams = new NxpMoldMaintenance();
                    nxpMoldMaintenanceParams.setMoldId(newMold.getId());
                    nxpMoldMaintenanceParams.setMoldCode(newMold.getMoldCode());
                    nxpMoldMaintenanceParams.setMoldRmsCode(newMold.getMoldRmsCode());
                    nxpMoldMaintenanceParams.setMoldBoxCode(newMold.getMoldCode());
                    nxpMoldMaintenanceParams.setCheckTime(DateUtils.getCurrentByHour(12));
                    nxpMoldMaintenanceParams.setStatus(1);
                    nxpMoldMaintenanceParams.setMaintenanceType(4);
                    nxpMoldMaintenanceParams.setMoldType(newMold.getMoldType());
                    nxpMoldMaintenanceParams.setOverdueState(0);
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldMaintenanceService.insertNxpMoldMaintenance(nxpMoldMaintenanceParams);
                    DynamicDataSourceContextHolder.clearDataSourceType();

                    // 模具改为待维护状态，并与设备解绑
                    newMold.setStatus(5);
                    newMold.setFacilityId(0);
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldService.updateNxpMoldApi(newMold);
                    DynamicDataSourceContextHolder.clearDataSourceType();
                }
            }
        }

        // 归还
        NxpMoldReturn params = new NxpMoldReturn();
        params.setMoldType(2);
        params.setStatus(3);
        params.getParams().put("beginTime",yesterday7);
        params.getParams().put("endTime",today7);
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnService.selectNxpMoldReturnList(params);
        DynamicDataSourceContextHolder.clearDataSourceType();
        for (NxpMoldReturn nxpMoldReturn : nxpMoldReturns) {
            Long moldId = nxpMoldReturn.getMoldId();
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            NxpMold nxpMold = nxpMoldService.selectNxpMoldById(moldId);
            DynamicDataSourceContextHolder.clearDataSourceType();
            if (nxpMold != null){
                // 先查询维护表有无待维护的模具
                NxpMoldMaintenance moldMaintenanceParams = new NxpMoldMaintenance();
                params.setMoldId(moldId);
                params.setStatus(1);
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                List<NxpMoldMaintenance> nxpMoldMaintenances = nxpMoldMaintenanceService.selectNxpMoldMaintenanceList(moldMaintenanceParams);
                DynamicDataSourceContextHolder.clearDataSourceType();
                if (nxpMoldMaintenances == null || nxpMoldMaintenances.size() == 0){
                    // 插入待维护表
                    NxpMoldMaintenance nxpMoldMaintenanceParams = new NxpMoldMaintenance();
                    nxpMoldMaintenanceParams.setMoldId(nxpMold.getId());
                    nxpMoldMaintenanceParams.setMoldCode(nxpMold.getMoldCode());
                    nxpMoldMaintenanceParams.setMoldRmsCode(nxpMold.getMoldRmsCode());
                    nxpMoldMaintenanceParams.setMoldBoxCode(nxpMold.getMoldCode());
                    nxpMoldMaintenanceParams.setCheckTime(DateUtils.getCurrentByHour(12));
                    nxpMoldMaintenanceParams.setStatus(1);
                    nxpMoldMaintenanceParams.setMaintenanceType(4);
                    nxpMoldMaintenanceParams.setMoldType(nxpMold.getMoldType());
                    nxpMoldMaintenanceParams.setOverdueState(0);
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldMaintenanceService.insertNxpMoldMaintenance(nxpMoldMaintenanceParams);
                    DynamicDataSourceContextHolder.clearDataSourceType();

                    // 模具改为待维护状态，并与设备解绑
                    nxpMold.setStatus(5);
                    nxpMold.setFacilityId(0);
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldService.updateNxpMoldApi(nxpMold);
                    DynamicDataSourceContextHolder.clearDataSourceType();
                }
            }
        }
    }
}
