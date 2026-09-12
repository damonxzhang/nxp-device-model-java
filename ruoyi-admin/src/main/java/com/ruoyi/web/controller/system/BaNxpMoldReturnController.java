package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.domain.NxpMoldReturn;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.service.INxpFacilityService;
import com.ruoyi.system.service.INxpMachineProcessService;
import com.ruoyi.system.service.INxpMoldReturnService;
import com.ruoyi.system.service.INxpUserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待归还模具Controller
 * 
 * @author YanCheng Liu
 * @date 2023-07-07
 */
@Controller
@RequestMapping("/system/baNxpMoldReturn")
public class BaNxpMoldReturnController extends BaseController
{
    private String prefix = "system/baNxpMoldReturn";

    @Autowired
    private INxpMoldReturnService nxpMoldReturnService;
    @Autowired
    private INxpFacilityService nxpFacilityService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:baNxpMoldReturn:view")
    @GetMapping()
    public String nxpMoldReturn(ModelMap mmap)
    {
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);

        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("nxpFacilities",nxpFacilities);
        return prefix + "/nxpMoldReturn";
    }

    /**
     * 查询待归还模具列表
     */
    @RequiresPermissions("system:baNxpMoldReturn:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldReturn nxpMoldReturn)
    {
        startPage();
        nxpMoldReturn.setMoldType(2);
        List<NxpMoldReturn> list = nxpMoldReturnService.selectNxpMoldReturnList(nxpMoldReturn);
        return getDataTable(list);
    }

    /**
     * 导出待归还模具列表
     */
    @RequiresPermissions("system:baNxpMoldReturn:export")
    @Log(title = "待归还模具", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldReturn nxpMoldReturn)
    {
        List<NxpMoldReturn> list = nxpMoldReturnService.selectNxpMoldReturnList(nxpMoldReturn);
        ExcelUtil<NxpMoldReturn> util = new ExcelUtil<NxpMoldReturn>(NxpMoldReturn.class);
        return util.exportExcel(list, "nxpMoldReturn");
    }

    /**
     * 新增待归还模具
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存待归还模具
     */
    @RequiresPermissions("system:baNxpMoldReturn:add")
    @Log(title = "待归还模具", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldReturn nxpMoldReturn)
    {
        return toAjax(nxpMoldReturnService.insertNxpMoldReturn(nxpMoldReturn));
    }

    /**
     * 修改待归还模具
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldReturn nxpMoldReturn = nxpMoldReturnService.selectNxpMoldReturnById(id);
        mmap.put("nxpMoldReturn", nxpMoldReturn);
        if (StringUtils.isNotEmpty(nxpMoldReturn.getProcessId())){
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(nxpMoldReturn.getProcessId()));
            if (nxpMachineProcess != null){
                nxpMoldReturn.setProcess(nxpMachineProcess.getName());
            }
        }
        if (nxpMoldReturn.getUserInfoId() != null){
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpMoldReturn.getUserInfoId());
            if (nxpUserInfo != null){
                nxpMoldReturn.setUserInfoName(nxpUserInfo.getChinese_name());
            }
        }
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        nxpFacility.setProcess("6");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("nxpFacilities", nxpFacilities);
        return prefix + "/edit";
    }

    /**
     * 修改待归还模具
     */
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldReturn nxpMoldReturn = nxpMoldReturnService.selectNxpMoldReturnById(id);
        if (StringUtils.isNotEmpty(nxpMoldReturn.getProcessId())){
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(nxpMoldReturn.getProcessId()));
            if (nxpMachineProcess != null){
                nxpMoldReturn.setProcess(nxpMachineProcess.getName());
            }
        }
        if (nxpMoldReturn.getUserInfoId() != null){
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpMoldReturn.getUserInfoId());
            if (nxpUserInfo != null){
                nxpMoldReturn.setUserInfoName(nxpUserInfo.getChinese_name());
            }
        }
        mmap.put("nxpMoldReturn", nxpMoldReturn);
        return prefix + "/details";
    }

    /**
     * 修改保存待归还模具
     */
    @RequiresPermissions("system:baNxpMoldReturn:edit")
    @Log(title = "待归还模具", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldReturn nxpMoldReturn)
    {
        return nxpMoldReturnService.updateNxpMoldReturn(nxpMoldReturn);
    }

    /**
     * 删除待归还模具
     */
    @RequiresPermissions("system:baNxpMoldReturn:remove")
    @Log(title = "待归还模具", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldReturnService.deleteNxpMoldReturnByIds(ids));
    }
}
