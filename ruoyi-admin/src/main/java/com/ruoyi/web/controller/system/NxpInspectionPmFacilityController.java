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
import com.ruoyi.system.domain.NxpInspectionPmFacility;
import com.ruoyi.system.service.INxpInspectionPmFacilityService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 周PM检查项关联表Controller
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/inspectionPmFacility")
public class NxpInspectionPmFacilityController extends BaseController
{
    private String prefix = "system/inspectionPmFacility";

    @Autowired
    private INxpInspectionPmFacilityService nxpInspectionPmFacilityService;

    @RequiresPermissions("system:inspectionPmFacility:view")
    @GetMapping()
    public String inspectionPmFacility()
    {
        return prefix + "/inspectionPmFacility";
    }

    /**
     * 查询周PM检查项关联表列表
     */
    @RequiresPermissions("system:inspectionPmFacility:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        startPage();
        List<NxpInspectionPmFacility> list = nxpInspectionPmFacilityService.selectNxpInspectionPmFacilityList(nxpInspectionPmFacility);
        return getDataTable(list);
    }

    /**
     * 导出周PM检查项关联表列表
     */
    @RequiresPermissions("system:inspectionPmFacility:export")
    @Log(title = "周PM检查项关联表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        List<NxpInspectionPmFacility> list = nxpInspectionPmFacilityService.selectNxpInspectionPmFacilityList(nxpInspectionPmFacility);
        ExcelUtil<NxpInspectionPmFacility> util = new ExcelUtil<NxpInspectionPmFacility>(NxpInspectionPmFacility.class);
        return util.exportExcel(list, "inspectionPmFacility");
    }

    /**
     * 新增周PM检查项关联表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存周PM检查项关联表
     */
    @RequiresPermissions("system:inspectionPmFacility:add")
    @Log(title = "周PM检查项关联表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        return toAjax(nxpInspectionPmFacilityService.insertNxpInspectionPmFacility(nxpInspectionPmFacility));
    }

    /**
     * 修改周PM检查项关联表
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPmFacility nxpInspectionPmFacility = nxpInspectionPmFacilityService.selectNxpInspectionPmFacilityById(id);
        mmap.put("nxpInspectionPmFacility", nxpInspectionPmFacility);
        return prefix + "/edit";
    }

    /**
     * 修改保存周PM检查项关联表
     */
    @RequiresPermissions("system:inspectionPmFacility:edit")
    @Log(title = "周PM检查项关联表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPmFacility nxpInspectionPmFacility)
    {
        return toAjax(nxpInspectionPmFacilityService.updateNxpInspectionPmFacility(nxpInspectionPmFacility));
    }

    /**
     * 删除周PM检查项关联表
     */
    @RequiresPermissions("system:inspectionPmFacility:remove")
    @Log(title = "周PM检查项关联表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPmFacilityService.deleteNxpInspectionPmFacilityByIds(ids));
    }
}
