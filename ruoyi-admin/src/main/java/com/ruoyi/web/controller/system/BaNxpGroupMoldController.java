package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.NxpGroupMold;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.service.INxpGroupMoldService;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupService;
import com.ruoyi.system.service.INxpMoldService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模具&检查组Controller
 * 
 * @author YanCheng Liu
 * @date 2023-10-13
 */
@Controller
@RequestMapping("/system/baNxpGroupMold")
public class BaNxpGroupMoldController extends BaseController
{
    private String prefix = "system/baNxpGroupMold";

    @Autowired
    private INxpGroupMoldService nxpGroupMoldService;
    @Autowired
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;
    @Autowired
    private INxpMoldService nxpMoldService;

    @RequiresPermissions("system:baNxpGroupMold:view")
    @GetMapping()
    public String nxpGroupMold(ModelMap mmap)
    {
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setStatus(1);
        nxpMoldMaintenanceGroup.setMoldType(2);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("groups",nxpMoldMaintenanceGroups);
        return prefix + "/nxpGroupMold";
    }

    /**
     * 查询模具&检查组列表
     */
    @RequiresPermissions("system:baNxpGroupMold:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpGroupMold nxpGroupMold)
    {
        startPage();
        nxpGroupMold.setMoldType(2);
        List<NxpGroupMold> list = nxpGroupMoldService.selectNxpGroupMoldList(nxpGroupMold);
        return getDataTable(list);
    }

    /**
     * 导出模具&检查组列表
     */
    @RequiresPermissions("system:baNxpGroupMold:export")
    @Log(title = "模具&检查组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpGroupMold nxpGroupMold)
    {
        List<NxpGroupMold> list = nxpGroupMoldService.selectNxpGroupMoldList(nxpGroupMold);
        ExcelUtil<NxpGroupMold> util = new ExcelUtil<NxpGroupMold>(NxpGroupMold.class);
        return util.exportExcel(list, "nxpGroupMold");
    }

    /**
     * 新增模具&检查组
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setStatus(1);
        nxpMoldMaintenanceGroup.setMoldType(2);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("groups",nxpMoldMaintenanceGroups);
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(2);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldList(nxpMold);
        mmap.put("molds",nxpMolds);
        return prefix + "/add";
    }

    /**
     * 新增保存模具&检查组
     */
    @RequiresPermissions("system:baNxpGroupMold:add")
    @Log(title = "模具&检查组", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpGroupMold nxpGroupMold)
    {
        return nxpGroupMoldService.insertNxpGroupMold(nxpGroupMold);
    }

    /**
     * 修改模具&检查组
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpGroupMold nxpGroupMold = nxpGroupMoldService.selectNxpGroupMoldById(id);
        mmap.put("nxpGroupMold", nxpGroupMold);
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setStatus(1);
        nxpMoldMaintenanceGroup.setMoldType(2);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("groups",nxpMoldMaintenanceGroups);
        List<NxpMold> molds = nxpMoldService.selectMoldListByGroup(nxpGroupMold.getMoldIds(),2);
        mmap.put("molds",molds);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具&检查组
     */
    @RequiresPermissions("system:baNxpGroupMold:edit")
    @Log(title = "模具&检查组", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpGroupMold nxpGroupMold)
    {
        return nxpGroupMoldService.updateNxpGroupMold(nxpGroupMold);
    }

    /**
     * 删除模具&检查组
     */
    @RequiresPermissions("system:baNxpGroupMold:remove")
    @Log(title = "模具&检查组", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpGroupMoldService.deleteNxpGroupMoldByIds(ids));
    }
}
