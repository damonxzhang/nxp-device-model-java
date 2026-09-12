package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupBind;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupBindService;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupService;
import com.ruoyi.system.service.INxpMoldService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模具检查组绑定Controller
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Controller
@RequestMapping("/system/baNxpMoldMaintenanceGroupBind")
public class BaNxpMoldMaintenanceGroupBindController extends BaseController
{
    private String prefix = "system/baNxpMoldMaintenanceGroupBind";

    @Autowired
    private INxpMoldMaintenanceGroupBindService nxpMoldMaintenanceGroupBindService;
    @Autowired
    private INxpMoldService nxpMoldService;
    @Autowired
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;

    @RequiresPermissions("system:baNxpMoldMaintenanceGroupBind:view")
    @GetMapping()
    public String nxpMoldMaintenanceGroupBind(ModelMap mmap)
    {
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(2);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldList(nxpMold);
        mmap.put("nxpMolds",nxpMolds);
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setMoldType(2);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("nxpMoldMaintenanceGroups",nxpMoldMaintenanceGroups);
        return prefix + "/nxpMoldMaintenanceGroupBind";
    }

    /**
     * 查询模具检查组绑定列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceGroupBind:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind)
    {
        startPage();
        nxpMoldMaintenanceGroupBind.setMoldType(2);
        List<NxpMoldMaintenanceGroupBind> list = nxpMoldMaintenanceGroupBindService.selectNxpMoldMaintenanceGroupBindList(nxpMoldMaintenanceGroupBind);
        return getDataTable(list);
    }

    /**
     * 导出模具检查组绑定列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceGroupBind:export")
    @Log(title = "模具检查组绑定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind)
    {
        List<NxpMoldMaintenanceGroupBind> list = nxpMoldMaintenanceGroupBindService.selectNxpMoldMaintenanceGroupBindList(nxpMoldMaintenanceGroupBind);
        ExcelUtil<NxpMoldMaintenanceGroupBind> util = new ExcelUtil<NxpMoldMaintenanceGroupBind>(NxpMoldMaintenanceGroupBind.class);
        return util.exportExcel(list, "nxpMoldMaintenanceGroupBind");
    }

    /**
     * 新增模具检查组绑定
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(2);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldList(nxpMold);
        mmap.put("nxpMolds",nxpMolds);
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setMoldType(2);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("nxpMoldMaintenanceGroups",nxpMoldMaintenanceGroups);
        return prefix + "/add";
    }

    /**
     * 新增保存模具检查组绑定
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceGroupBind:add")
    @Log(title = "模具检查组绑定", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind)
    {
        return toAjax(nxpMoldMaintenanceGroupBindService.insertNxpMoldMaintenanceGroupBind(nxpMoldMaintenanceGroupBind));
    }

    /**
     * 修改模具检查组绑定
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind = nxpMoldMaintenanceGroupBindService.selectNxpMoldMaintenanceGroupBindById(id);
        mmap.put("nxpMoldMaintenanceGroupBind", nxpMoldMaintenanceGroupBind);
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(2);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldList(nxpMold);
        mmap.put("nxpMolds",nxpMolds);
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setMoldType(2);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("nxpMoldMaintenanceGroups",nxpMoldMaintenanceGroups);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具检查组绑定
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceGroupBind:edit")
    @Log(title = "模具检查组绑定", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind)
    {
        return toAjax(nxpMoldMaintenanceGroupBindService.updateNxpMoldMaintenanceGroupBind(nxpMoldMaintenanceGroupBind));
    }

    /**
     * 删除模具检查组绑定
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceGroupBind:remove")
    @Log(title = "模具检查组绑定", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldMaintenanceGroupBindService.deleteNxpMoldMaintenanceGroupBindByIds(ids));
    }
}
