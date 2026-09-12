package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldComponent;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.domain.NxpMoldMaintenanceRule;
import com.ruoyi.system.service.INxpMoldComponentService;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupService;
import com.ruoyi.system.service.INxpMoldMaintenanceRuleService;
import com.ruoyi.system.service.INxpMoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author snkj
 * @create 2023-08-23 17:57
 */
@RestController
@RequestMapping("/api/test")
public class ApiTestController {

    @Autowired
    private INxpMoldService nxpMoldService;
    @Autowired
    private INxpMoldComponentService nxpMoldComponentService;
    @Resource
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;
    @Resource
    private INxpMoldMaintenanceRuleService nxpMoldMaintenanceRuleService;

    @GetMapping("/test01")
    public AjaxResult test01() {
        //推荐模具 去掉自身编号推荐模具，用模具盒编号查询模具，拿到模具机型，反查模具，有推荐标识走推荐，无推荐标识走统计
        NxpMold paramsMold = new NxpMold();
        paramsMold.setMachineModel("51");
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListByMachineModel(paramsMold);

        // 这里通过模具再次查询部件，如果部件有异常则不推荐，优先推荐正常部件的模具
        List<NxpMold> paramsList = new ArrayList<>();

        for (NxpMold mold : nxpMolds) {
            List<NxpMoldComponent> components = nxpMoldComponentService.selectNxpMoldComponentByMoldCode(mold.getMoldCode());
            boolean b = false;
            loop:
            for (NxpMoldComponent moldComponent : components) {
                Integer status = moldComponent.getStatus();
                if (status != 1) {
                    b = false;
                    break loop;
                }
                b = true;
            }

            if (b) {
                paramsList.add(mold);
            }
        }

        Collections.sort(paramsList, (obj1, obj2) -> {
            if (obj1.getIsRecommend() > obj2.getIsRecommend()) {
                return 1;
            } else {
                return -1;
            }
        });

        System.out.println(paramsList.size());
        return AjaxResult.success(paramsList);
    }

    @PostMapping("/test02")
    public AjaxResult test02(){
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupById(3L);
        return AjaxResult.success(nxpMoldMaintenanceGroup);

    }

    @PostMapping("/test03")
    public AjaxResult test03(){
        List<NxpMoldMaintenanceRule> nxpMoldMaintenanceRules = nxpMoldMaintenanceRuleService.selectNxpMoldMaintenanceRuleList(new NxpMoldMaintenanceRule());
        for (NxpMoldMaintenanceRule moldMaintenanceRule : nxpMoldMaintenanceRules) {
            Long cycle = moldMaintenanceRule.getCycle();
            Date beginTime = moldMaintenanceRule.getBeginTime();
            LocalDate beginLocalDate = LocalDate.parse(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, beginTime));
            // 提前时间
            Long advance = moldMaintenanceRule.getAdvanceNum();
            LocalDate shouldExecuteDate = beginLocalDate.plusDays(cycle - advance);
            Date convertDate = Date.from(shouldExecuteDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String s = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, convertDate);
            System.out.println(s);
            moldMaintenanceRule.setShouldExecuteTime(convertDate);

            nxpMoldMaintenanceRuleService.updateNxpMoldMaintenanceRule(moldMaintenanceRule);
        }
        return AjaxResult.success();
    }
}
