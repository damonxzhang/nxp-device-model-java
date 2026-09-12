package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 模具维护列表Controller
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Controller
@RequestMapping("/system/baNxpMoldMaintenance")
public class BaNxpMoldMaintenanceController extends BaseController
{
    private String prefix = "system/baNxpMoldMaintenance";

    @Autowired
    private INxpMoldMaintenanceService nxpMoldMaintenanceService;
    @Autowired
    private INxpMoldMaintenanceCheckService nxpMoldMaintenanceCheckService;
    @Autowired
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;
    @Autowired
    private INxpMoldMaintenanceFileService nxpMoldMaintenanceFileService;

    @RequiresPermissions("system:baNxpMoldMaintenance:view")
    @GetMapping()
    public String nxpMoldMaintenance()
    {
        return prefix + "/nxpMoldMaintenance";
    }

    /**
     * 查询模具维护列表列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenance:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldMaintenance nxpMoldMaintenance)
    {
        startPage();
        nxpMoldMaintenance.setMoldType(2);
        List<NxpMoldMaintenance> list = nxpMoldMaintenanceService.selectNxpMoldMaintenanceList(nxpMoldMaintenance);
        return getDataTable(list);
    }

    /**
     * 导出模具维护列表列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenance:export")
    @Log(title = "模具维护列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldMaintenance nxpMoldMaintenance)
    {
        List<NxpMoldMaintenance> list = nxpMoldMaintenanceService.selectNxpMoldMaintenanceList(nxpMoldMaintenance);
        ExcelUtil<NxpMoldMaintenance> util = new ExcelUtil<NxpMoldMaintenance>(NxpMoldMaintenance.class);
        return util.exportExcel(list, "nxpMoldMaintenance");
    }

    /**
     * 新增模具维护列表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存模具维护列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenance:add")
    @Log(title = "模具维护列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldMaintenance nxpMoldMaintenance)
    {
        return toAjax(nxpMoldMaintenanceService.insertNxpMoldMaintenance(nxpMoldMaintenance));
    }

    /**
     * 修改模具维护列表
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldMaintenance nxpMoldMaintenance = nxpMoldMaintenanceService.selectNxpMoldMaintenanceById(id);
        mmap.put("nxpMoldMaintenance", nxpMoldMaintenance);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具维护列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenance:edit")
    @Log(title = "模具维护列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldMaintenance nxpMoldMaintenance)
    {
        return toAjax(nxpMoldMaintenanceService.updateNxpMoldMaintenance(nxpMoldMaintenance));
    }

    /**
     * 删除模具维护列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenance:remove")
    @Log(title = "模具维护列表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldMaintenanceService.deleteNxpMoldMaintenanceByIds(ids));
    }


    @GetMapping("/openMaintenanceMessage/{id}")
    public String openMaintenanceMessage(@PathVariable("id") Long id, ModelMap mmap){
        startPage();
        NxpMoldMaintenanceCheck params = new NxpMoldMaintenanceCheck();
        params.setMoldMaintenanceId(id);
        List<NxpMoldMaintenanceCheck> nxpMoldMaintenanceChecks = nxpMoldMaintenanceCheckService.selectNxpMoldMaintenanceCheckList(params);
        for (NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck : nxpMoldMaintenanceChecks) {
            NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem = nxpMoldMaintenanceGroupService.selectGroupItemById(nxpMoldMaintenanceCheck.getGroupItemId());
            nxpMoldMaintenanceCheck.setGroupItemName(nxpMoldMaintenanceGroupItem.getItemName());
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpMoldMaintenanceCheck.getUserInfoId());
            nxpMoldMaintenanceCheck.setUserInfoName(nxpUserInfo.getChinese_name());
        }
        mmap.put("list",nxpMoldMaintenanceChecks);
        mmap.put("moldMaintenanceId",id);
        return prefix + "/nxpMoldMaintenanceCheck";
    }

    @GetMapping("/maintenanceMessageInfo/{id}")
    public String serviceShowInfo(@PathVariable("id") Long id, ModelMap mmap){
        NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck = nxpMoldMaintenanceCheckService.selectNxpMoldMaintenanceCheckById(id);
        NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem = nxpMoldMaintenanceGroupService.selectGroupItemById(nxpMoldMaintenanceCheck.getGroupItemId());
        nxpMoldMaintenanceCheck.setGroupItemName(nxpMoldMaintenanceGroupItem.getItemName());
        NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpMoldMaintenanceCheck.getUserInfoId());
        nxpMoldMaintenanceCheck.setUserInfoName(nxpUserInfo.getChinese_name());

        List<NxpMoldMaintenanceFile> files = new ArrayList<>();
        if (StringUtils.isNotEmpty(nxpMoldMaintenanceCheck.getFileId())){
            String fileId = nxpMoldMaintenanceCheck.getFileId();
            if (fileId.contains(",")){
                String[] split = fileId.split(",");
                for (String s : split) {
                    NxpMoldMaintenanceFile nxpMoldMaintenanceFile = nxpMoldMaintenanceFileService.selectNxpMoldMaintenanceFileById(Long.valueOf(s));
                    files.add(nxpMoldMaintenanceFile);
                }
            }else {
                NxpMoldMaintenanceFile nxpMoldMaintenanceFile = nxpMoldMaintenanceFileService.selectNxpMoldMaintenanceFileById(Long.valueOf(fileId));
                files.add(nxpMoldMaintenanceFile);
            }
        }

        nxpMoldMaintenanceCheck.setFiles(files);
        mmap.put("nxpMoldMaintenanceCheck",nxpMoldMaintenanceCheck);
        mmap.put("files",files);
        return prefix + "/checkInfo";
    }
}
