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
import com.ruoyi.system.domain.NxpInspectionMaintenanceReason;
import com.ruoyi.system.service.INxpInspectionMaintenanceReasonService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 无需维修原因Controller
 * 
 * @author YanCheng Liu
 * @date 2021-03-14
 */
@Controller
@RequestMapping("/system/maintenanceReason")
public class NxpInspectionMaintenanceReasonController extends BaseController
{
    private String prefix = "system/maintenanceReason";

    @Autowired
    private INxpInspectionMaintenanceReasonService nxpInspectionMaintenanceReasonService;

    @RequiresPermissions("system:maintenanceReason:view")
    @GetMapping()
    public String maintenanceReason()
    {
        return prefix + "/maintenanceReason";
    }

    /**
     * 查询无需维修原因列表
     */
    @RequiresPermissions("system:maintenanceReason:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason)
    {
        startPage();
        List<NxpInspectionMaintenanceReason> list = nxpInspectionMaintenanceReasonService.selectNxpInspectionMaintenanceReasonList(nxpInspectionMaintenanceReason);
        return getDataTable(list);
    }

    /**
     * 导出无需维修原因列表
     */
    @RequiresPermissions("system:maintenanceReason:export")
    @Log(title = "无需维修原因", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason)
    {
        List<NxpInspectionMaintenanceReason> list = nxpInspectionMaintenanceReasonService.selectNxpInspectionMaintenanceReasonList(nxpInspectionMaintenanceReason);
        ExcelUtil<NxpInspectionMaintenanceReason> util = new ExcelUtil<NxpInspectionMaintenanceReason>(NxpInspectionMaintenanceReason.class);
        return util.exportExcel(list, "maintenanceReason");
    }

    /**
     * 新增无需维修原因
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存无需维修原因
     */
    @RequiresPermissions("system:maintenanceReason:add")
    @Log(title = "无需维修原因", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason)
    {
        return toAjax(nxpInspectionMaintenanceReasonService.insertNxpInspectionMaintenanceReason(nxpInspectionMaintenanceReason));
    }

    /**
     * 修改无需维修原因
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason = nxpInspectionMaintenanceReasonService.selectNxpInspectionMaintenanceReasonById(id);
        mmap.put("nxpInspectionMaintenanceReason", nxpInspectionMaintenanceReason);
        return prefix + "/edit";
    }

    /**
     * 修改保存无需维修原因
     */
    @RequiresPermissions("system:maintenanceReason:edit")
    @Log(title = "无需维修原因", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason)
    {
        return toAjax(nxpInspectionMaintenanceReasonService.updateNxpInspectionMaintenanceReason(nxpInspectionMaintenanceReason));
    }

    /**
     * 删除无需维修原因
     */
    @RequiresPermissions("system:maintenanceReason:remove")
    @Log(title = "无需维修原因", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionMaintenanceReasonService.deleteNxpInspectionMaintenanceReasonByIds(ids));
    }
}
