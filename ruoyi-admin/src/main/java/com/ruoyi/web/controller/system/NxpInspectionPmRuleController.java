package com.ruoyi.web.controller.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.VO.NxpFacilityVO;
import com.ruoyi.system.mapper.NxpInspectionPmFacilityMapper;
import com.ruoyi.system.mapper.NxpInspectionPmRuleMapper;
import com.ruoyi.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 周PM检查规则Controller
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
@Controller
@RequestMapping("/system/inspectionPmRule")
public class NxpInspectionPmRuleController extends BaseController
{
    private String prefix = "system/inspectionPmRule";

    @Autowired
    private INxpInspectionPmRuleService nxpInspectionPmRuleService;
    @Autowired
    private INxpFacilityService nxpFacilityService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;
    @Autowired
    private INxpInspectionPmGroupService nxpInspectionPmGroupService;
    @Autowired
    private INxpInspectionPmFacilityService nxpInspectionPmFacilityService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private NxpInspectionPmFacilityMapper nxpInspectionPmFacilityMapper;
    @Autowired
    private NxpInspectionPmRuleMapper nxpInspectionPmRuleMapper;

    @RequiresPermissions("system:inspectionPmRule:view")
    @GetMapping()
    public String inspectionPmRule(ModelMap mmap)
    {

        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);


        NxpFacilityVO nxpFacility = new NxpFacilityVO();
        nxpFacility.setStatus("1");
        List<NxpFacility> facilityList = nxpFacilityService.selectNxpFacilityByNotInIds(nxpFacility);
        NxpUserInfo userInfo = new NxpUserInfo();
        userInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(userInfo);
        mmap.put("nxpUserInfos",nxpUserInfos);
        mmap.put("facilityList",facilityList);
        return prefix + "/inspectionPmRule";
    }

    /**
     * 查询周PM检查规则列表
     */
    @RequiresPermissions("system:inspectionPmRule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPmRule nxpInspectionPmRule)
    {
        startPage();
        List<NxpInspectionPmRule> list = nxpInspectionPmRuleService.selectNxpInspectionPmRuleList(nxpInspectionPmRule);
        return getDataTable(list);
    }

    /**
     * 导出周PM检查规则列表
     */
    @RequiresPermissions("system:inspectionPmRule:export")
    @Log(title = "周PM检查规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPmRule nxpInspectionPmRule)
    {
        List<NxpInspectionPmRule> list = nxpInspectionPmRuleService.selectNxpInspectionPmRuleList(nxpInspectionPmRule);
        ExcelUtil<NxpInspectionPmRule> util = new ExcelUtil<NxpInspectionPmRule>(NxpInspectionPmRule.class);
        return util.exportExcel(list, "inspectionPmRule");
    }

    /**
     * 新增周PM检查规则
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        // 1. 查询所有已配置规则的设备
        List<String> facilityIds = new ArrayList<>();
        NxpInspectionPmRule nxpInspectionPmRule = new NxpInspectionPmRule();
        nxpInspectionPmRule.setStatus(1);
        List<NxpInspectionPmRule> nxpInspectionPmRules = nxpInspectionPmRuleService.selectNxpInspectionPmRuleListNew(nxpInspectionPmRule);
        for (NxpInspectionPmRule inspectionPmRule : nxpInspectionPmRules) {
            facilityIds.add(inspectionPmRule.getFacility_id());
        }
        NxpFacilityVO nxpFacility = new NxpFacilityVO();
        nxpFacility.setStatus("1");
        nxpFacility.setIds(facilityIds);
        List<NxpFacility> facilityList = nxpFacilityService.selectNxpFacilityByNotInIds(nxpFacility);
        // 2. 查询检查人
        NxpUserInfo userInfo = new NxpUserInfo();
        userInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(userInfo);

        NxpInspectionPmGroup nxpInspectionPmGroup = new NxpInspectionPmGroup();
        nxpInspectionPmGroup.setStatus(1);
        List<NxpInspectionPmGroup> nxpInspectionPmGroups = nxpInspectionPmGroupService.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);
        mmap.put("nxpInspectionPmGroups",nxpInspectionPmGroups);

        mmap.put("facilityList",facilityList);
        mmap.put("nxpUserInfos",nxpUserInfos);
        return prefix + "/add";
    }

    /**
     * 新增保存周PM检查规则
     */
    @RequiresPermissions("system:inspectionPmRule:add")
    @Log(title = "周PM检查规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPmRule nxpInspectionPmRule)
    {
        int i = nxpInspectionPmRuleService.insertNxpInspectionPmRule(nxpInspectionPmRule);
        if (i==2)
            return AjaxResult.error("该设备规则已存在");

        return toAjax(i);
    }

    /**
     * 修改周PM检查规则
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPmRule nxpInspectionPmRule = nxpInspectionPmRuleService.selectNxpInspectionPmRuleById(id);
        if (nxpInspectionPmRule.getCycle() != null){
            nxpInspectionPmRule.setCycle(BigDecimal.valueOf(nxpInspectionPmRule.getCycle().intValue()/10));
        }

//        设备信息
        NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
        if (nxpFacility != null){
            nxpInspectionPmRule.setFacilityNumber(nxpFacility.getNumber());
            NxpInspectionPmFacility nxpInspectionPmFacility1 = new NxpInspectionPmFacility();
            nxpInspectionPmFacility1.setFacility_id(nxpFacility.getId());
            List<NxpInspectionPmFacility> nxpInspectionPmFacilities = nxpInspectionPmFacilityService.selectNxpInspectionPmFacilityListByFacilityId(nxpInspectionPmFacility1);
            if (nxpInspectionPmFacilities != null){
                for (NxpInspectionPmFacility nxpInspectionPmFacility : nxpInspectionPmFacilities) {
                    nxpInspectionPmRule.setGroup_id(nxpInspectionPmFacility.getGroup_id());

                }
            }
        }


        // 2. 查询检查人
        NxpUserInfo userInfo = new NxpUserInfo();
        userInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(userInfo);

        // 人员
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        if (nxpInspectionPmRule.getUser_id() != null){
            nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpInspectionPmRule.getUser_id());
        }

        // 人员
        NxpUserInfo nxpReviewUserInfo = new NxpUserInfo();
        NxpFacility nxpFacility1 = nxpFacilityService.selectNxpFacilityById(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
        if (nxpFacility1.getReview_user() != null){
            nxpReviewUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpFacility1.getReview_user());
            nxpInspectionPmRule.setReview_user_id(nxpFacility1.getReview_user());
        }

        NxpInspectionPmGroup nxpInspectionPmGroup = new NxpInspectionPmGroup();
        nxpInspectionPmGroup.setStatus(1);
        List<NxpInspectionPmGroup> nxpInspectionPmGroups = nxpInspectionPmGroupService.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);


        mmap.put("nxpUserInfos",nxpUserInfos);
        mmap.put("nxpInspectionPmGroups",nxpInspectionPmGroups);
        mmap.put("userInfo",nxpUserInfo);
        mmap.put("reviewUserInfo",nxpReviewUserInfo);
        mmap.put("nxpInspectionPmRule", nxpInspectionPmRule);
        return prefix + "/edit";
    }
//
//    /**
//     * 修改周PM检查规则
//     */
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable("id") Long id, ModelMap mmap)
//    {
//        NxpInspectionPmRule nxpInspectionPmRule = nxpInspectionPmRuleService.selectNxpInspectionPmRuleById(id);
//        // 1. 查询设备
//        NxpFacility nxpFacility = new NxpFacility();
//        nxpFacility.setStatus("1");
//        List<NxpFacility> nxpFacilityList = nxpFacilityService.selectNxpFacilityList(nxpFacility);
//        String facilityId = nxpInspectionPmRule.getFacility_id();
//        List<String> facilityIds = new ArrayList<>();
//        if (facilityId.indexOf(",") != -1){
//            String[] split = facilityId.split(",");
//            facilityIds = Arrays.asList(split);
//        }else {
//            facilityIds.add(facilityId);
//        }
//        for (NxpFacility facility : nxpFacilityList) {
//            for (String deviceId : facilityIds) {
//                if (facility.getId().toString().equals(deviceId)){
//                    facility.setFlag(true);
//                    break;
//                }
//            }
//        }
//        // 2. 查询检查人
//        NxpUserInfo userInfo = new NxpUserInfo();
//        userInfo.setStatus(1);
//        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(userInfo);
//
//        // 人员
//        NxpUserInfo nxpUserInfo = new NxpUserInfo();
//        if (nxpInspectionPmRule.getUser_id() != null){
//            nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpInspectionPmRule.getUser_id());
//        }
//
//        // 人员
//        NxpUserInfo nxpReviewUserInfo = new NxpUserInfo();
//        NxpFacility nxpFacility1 = nxpFacilityService.selectNxpFacilityById(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
//        if (nxpFacility1.getReview_user() != null){
//            nxpReviewUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpFacility1.getReview_user());
//            nxpInspectionPmRule.setReview_user_id(nxpFacility1.getReview_user());
//        }
//
//        mmap.put("facilityList",nxpFacilityList);
//        mmap.put("nxpUserInfos",nxpUserInfos);
//        mmap.put("userInfo",nxpUserInfo);
//        mmap.put("reviewUserInfo",nxpReviewUserInfo);
//        mmap.put("nxpInspectionPmRule", nxpInspectionPmRule);
//        return prefix + "/edit";
//    }

    /**
     * 修改保存周PM检查规则
     */
    @RequiresPermissions("system:inspectionPmRule:edit")
    @Log(title = "周PM检查规则", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPmRule nxpInspectionPmRule)
    {
        int i = nxpInspectionPmRuleService.updateNxpInspectionPmRule(nxpInspectionPmRule);
        if (i==2)
            return AjaxResult.error("如果修改时间，时间必须大于今天日期！");
        return toAjax(i);
    }

    /**
     * 删除周PM检查规则
     */
    @RequiresPermissions("system:inspectionPmRule:remove")
    @Log(title = "周PM检查规则", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        NxpInspectionPmRule nxpInspectionPmRule = nxpInspectionPmRuleService.selectNxpInspectionPmRuleById(Long.valueOf(ids));
        if (nxpInspectionPmRule != null){
                if (nxpInspectionPmRule.getFacility_id() != null){
                    NxpInspectionPmFacility nxpInspectionPmFacility = new NxpInspectionPmFacility();
                    nxpInspectionPmFacility.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                    int i = nxpInspectionPmFacilityService.deleteNxpInspectionPmFacilityByFacilityId(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                }
        }
        int i = nxpInspectionPmRuleService.deleteNxpInspectionPmRuleById(Long.valueOf(ids));
        return toAjax(i);
    }
}
