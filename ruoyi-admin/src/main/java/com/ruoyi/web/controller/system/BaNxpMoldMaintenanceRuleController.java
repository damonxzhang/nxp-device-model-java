package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldMaintenanceRule;
import com.ruoyi.system.service.INxpMoldMaintenanceRuleService;
import com.ruoyi.system.service.INxpMoldService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模具维护规则Controller
 * 
 * @author YanCheng Liu
 * @date 2023-07-24
 */
@Controller
@RequestMapping("/system/baNxpMoldMaintenanceRule")
public class BaNxpMoldMaintenanceRuleController extends BaseController
{
    private String prefix = "system/baNxpMoldMaintenanceRule";

    @Autowired
    private INxpMoldMaintenanceRuleService nxpMoldMaintenanceRuleService;
    @Autowired
    private INxpMoldService nxpMoldService;

    @RequiresPermissions("system:baNxpMoldMaintenanceRule:view")
    @GetMapping()
    public String nxpMoldMaintenanceRule()
    {
        return prefix + "/nxpMoldMaintenanceRule";
    }

    /**
     * 查询模具维护规则列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceRule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldMaintenanceRule nxpMoldMaintenanceRule)
    {
        startPage();
        nxpMoldMaintenanceRule.setMoldType(2L);
        List<NxpMoldMaintenanceRule> list = nxpMoldMaintenanceRuleService.selectNxpMoldMaintenanceRuleList(nxpMoldMaintenanceRule);
        return getDataTable(list);
    }

    /**
     * 导出模具维护规则列表
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceRule:export")
    @Log(title = "模具维护规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldMaintenanceRule nxpMoldMaintenanceRule)
    {
        List<NxpMoldMaintenanceRule> list = nxpMoldMaintenanceRuleService.selectNxpMoldMaintenanceRuleList(nxpMoldMaintenanceRule);
        ExcelUtil<NxpMoldMaintenanceRule> util = new ExcelUtil<NxpMoldMaintenanceRule>(NxpMoldMaintenanceRule.class);
        return util.exportExcel(list, "nxpMoldMaintenanceRule");
    }

    /**
     * 新增模具维护规则
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(2);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListNew(nxpMold);
        mmap.put("nxpMolds",nxpMolds);
        return prefix + "/add";
    }

    /**
     * 新增保存模具维护规则
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceRule:add")
    @Log(title = "模具维护规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldMaintenanceRule nxpMoldMaintenanceRule)
    {
        nxpMoldMaintenanceRule.setMoldType(2L);
        return toAjax(nxpMoldMaintenanceRuleService.insertNxpMoldMaintenanceRule(nxpMoldMaintenanceRule));
    }

    /**
     * 修改模具维护规则
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldMaintenanceRule nxpMoldMaintenanceRule = nxpMoldMaintenanceRuleService.selectNxpMoldMaintenanceRuleById(id);
        mmap.put("nxpMoldMaintenanceRule", nxpMoldMaintenanceRule);
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(2);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListNew(nxpMold);
        mmap.put("nxpMolds",nxpMolds);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具维护规则
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceRule:edit")
    @Log(title = "模具维护规则", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldMaintenanceRule nxpMoldMaintenanceRule)
    {
        return toAjax(nxpMoldMaintenanceRuleService.updateNxpMoldMaintenanceRule(nxpMoldMaintenanceRule));
    }

    /**
     * 删除模具维护规则
     */
    @RequiresPermissions("system:baNxpMoldMaintenanceRule:remove")
    @Log(title = "模具维护规则", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldMaintenanceRuleService.deleteNxpMoldMaintenanceRuleByIds(ids));
    }
}
