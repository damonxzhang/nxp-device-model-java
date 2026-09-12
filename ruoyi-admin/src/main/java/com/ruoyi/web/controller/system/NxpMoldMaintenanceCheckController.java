package com.ruoyi.web.controller.system;

import java.util.List;
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
import com.ruoyi.system.domain.NxpMoldMaintenanceCheck;
import com.ruoyi.system.service.INxpMoldMaintenanceCheckService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模具维护检查结果Controller
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Controller
@RequestMapping("/system/nxpMoldMaintenanceCheck")
public class NxpMoldMaintenanceCheckController extends BaseController
{
    private String prefix = "system/nxpMoldMaintenanceCheck";

    @Autowired
    private INxpMoldMaintenanceCheckService nxpMoldMaintenanceCheckService;

//    @RequiresPermissions("system:nxpMoldMaintenanceCheck:view")
    @GetMapping()
    public String nxpMoldMaintenanceCheck()
    {
        return prefix + "/nxpMoldMaintenanceCheck";
    }

    /**
     * 查询模具维护检查结果列表
     */
//    @RequiresPermissions("system:nxpMoldMaintenanceCheck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck)
    {
        startPage();
        List<NxpMoldMaintenanceCheck> list = nxpMoldMaintenanceCheckService.selectNxpMoldMaintenanceCheckList(nxpMoldMaintenanceCheck);
        return getDataTable(list);
    }

    /**
     * 导出模具维护检查结果列表
     */
    @RequiresPermissions("system:nxpMoldMaintenanceCheck:export")
    @Log(title = "模具维护检查结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck)
    {
        List<NxpMoldMaintenanceCheck> list = nxpMoldMaintenanceCheckService.selectNxpMoldMaintenanceCheckList(nxpMoldMaintenanceCheck);
        ExcelUtil<NxpMoldMaintenanceCheck> util = new ExcelUtil<NxpMoldMaintenanceCheck>(NxpMoldMaintenanceCheck.class);
        return util.exportExcel(list, "nxpMoldMaintenanceCheck");
    }

    /**
     * 新增模具维护检查结果
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存模具维护检查结果
     */
//    @RequiresPermissions("system:nxpMoldMaintenanceCheck:add")
    @Log(title = "模具维护检查结果", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck)
    {
        return toAjax(nxpMoldMaintenanceCheckService.insertNxpMoldMaintenanceCheck(nxpMoldMaintenanceCheck));
    }

    /**
     * 修改模具维护检查结果
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck = nxpMoldMaintenanceCheckService.selectNxpMoldMaintenanceCheckById(id);
        mmap.put("nxpMoldMaintenanceCheck", nxpMoldMaintenanceCheck);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具维护检查结果
     */
//    @RequiresPermissions("system:nxpMoldMaintenanceCheck:edit")
    @Log(title = "模具维护检查结果", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck)
    {
        return toAjax(nxpMoldMaintenanceCheckService.updateNxpMoldMaintenanceCheck(nxpMoldMaintenanceCheck));
    }

    /**
     * 删除模具维护检查结果
     */
//    @RequiresPermissions("system:nxpMoldMaintenanceCheck:remove")
    @Log(title = "模具维护检查结果", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldMaintenanceCheckService.deleteNxpMoldMaintenanceCheckByIds(ids));
    }
}
