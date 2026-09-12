package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.*;
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

import javax.annotation.Resource;

/**
 * 待更换模具Controller
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Controller
@RequestMapping("/system/replacedList")
public class ReplacedListController extends BaseController
{
    private String prefix = "system/replacedList";

    @Autowired
    private IReplacedListService replacedListService;
    @Autowired
    private INxpFacilityService nxpFacilityService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpProductTypeService nxpProductTypeService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;
    @Resource
    private INxpUserLabelService nxpUserLabelService;

    @RequiresPermissions("system:replacedList:view")
    @GetMapping()
    public String replacedList(ModelMap mmap)
    {
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);

        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        nxpFacility.setProcess("9");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("nxpFacilities",nxpFacilities);

        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        nxpProductType.setMoldType(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        mmap.put("nxpProductTypes",nxpProductTypes);

        NxpUserLabel nxpUserLabel = new NxpUserLabel();
        nxpUserLabel.setMoldType(1);
        List<NxpUserLabel> nxpUserLabels = nxpUserLabelService.selectNxpUserLabelList(nxpUserLabel);
        mmap.put("nxpUserLabels",nxpUserLabels);
        return prefix + "/replacedList";
    }

    /**
     * 查询待更换模具列表
     */
    @RequiresPermissions("system:replacedList:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReplacedList replacedList)
    {
        startPage();
        replacedList.setMoldType(1);
        replacedList.setProcessId("9");
        List<ReplacedList> list = replacedListService.selectReplacedListList(replacedList);
        return getDataTable(list);
    }

    /**
     * 导出待更换模具列表
     */
    @RequiresPermissions("system:replacedList:export")
    @Log(title = "待更换模具", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReplacedList replacedList)
    {
        List<ReplacedList> list = replacedListService.selectReplacedListList(replacedList);
        ExcelUtil<ReplacedList> util = new ExcelUtil<ReplacedList>(ReplacedList.class);
        return util.exportExcel(list, "SAW待更换模具");
    }

    /**
     * 新增待更换模具
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存待更换模具
     */
    @RequiresPermissions("system:replacedList:add")
    @Log(title = "待更换模具", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ReplacedList replacedList)
    {
        return toAjax(replacedListService.insertReplacedList(replacedList));
    }

    /**
     * 修改待更换模具
     */
    @GetMapping("/edit/{replaceId}")
    public String edit(@PathVariable("replaceId") Long replaceId, ModelMap mmap)
    {
        ReplacedList replacedList = replacedListService.selectReplacedListById(replaceId);
        NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(replacedList.getProcessId()));
        if (nxpMachineProcess != null){
            replacedList.setProcess(nxpMachineProcess.getName());
        }
        NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(replacedList.getUserInfoId());
        if (nxpUserInfo != null){
            replacedList.setUserInfoName(nxpUserInfo.getChinese_name());
        }
        mmap.put("replacedList", replacedList);
        return prefix + "/edit";
    }

    /**
     * 修改待更换模具
     */
    @GetMapping("/details/{replaceId}")
    public String details(@PathVariable("replaceId") Long replaceId, ModelMap mmap)
    {
        ReplacedList replacedList = replacedListService.selectReplacedListById(replaceId);
        NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(replacedList.getProcessId()));
        if (nxpMachineProcess != null){
            replacedList.setProcess(nxpMachineProcess.getName());
        }
        NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(replacedList.getUserInfoId());
        if (nxpUserInfo != null){
            replacedList.setUserInfoName(nxpUserInfo.getChinese_name());
        }
        mmap.put("replacedList", replacedList);
        return prefix + "/details";
    }

    /**
     * 修改保存待更换模具
     */
    @RequiresPermissions("system:replacedList:edit")
    @Log(title = "待更换模具", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ReplacedList replacedList)
    {
        return toAjax(replacedListService.updateReplacedList(replacedList));
    }

    /**
     * 删除待更换模具
     */
    @RequiresPermissions("system:replacedList:remove")
    @Log(title = "待更换模具", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(replacedListService.deleteReplacedListByIds(ids));
    }

    /**
     * 根据工序查询设备
     * @param nxpFacility
     * @return
     */
    @PostMapping("/getFacilityByProcess")
    @ResponseBody
    public AjaxResult getFacilityByProcess(NxpFacility nxpFacility){
        return replacedListService.getFacilityByProcess(nxpFacility);
    }
}
