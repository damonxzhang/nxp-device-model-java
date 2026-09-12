package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMold;
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
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.service.INxpFacilityService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备管理Controller
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Controller
@RequestMapping("/system/facility")
public class NxpFacilityController extends BaseController
{
    private String prefix = "system/facility";

    @Autowired
    private INxpFacilityService nxpFacilityService;

    @RequiresPermissions("system:facility:view")
    @GetMapping()
    public String facility()
    {
        return prefix + "/facility";
    }

    /**
     * 查询设备管理列表
     */
    @RequiresPermissions("system:facility:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpFacility nxpFacility)
    {
        startPage();
        List<NxpFacility> list = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        return getDataTable(list);
    }

    /**
     * 导出设备管理列表
     */
    @RequiresPermissions("system:facility:export")
    @Log(title = "设备管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpFacility nxpFacility)
    {
        List<NxpFacility> list = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        ExcelUtil<NxpFacility> util = new ExcelUtil<NxpFacility>(NxpFacility.class);
        return util.exportExcel(list, "facility");
    }

    /**
     * 新增设备管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备管理
     */
    @RequiresPermissions("system:facility:add")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpFacility nxpFacility)
    {
        return toAjax(nxpFacilityService.insertNxpFacility(nxpFacility));
    }

    /**
     * 修改设备管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(id);
        mmap.put("nxpFacility", nxpFacility);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备管理
     */
    @RequiresPermissions("system:facility:edit")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpFacility nxpFacility)
    {
        return toAjax(nxpFacilityService.updateNxpFacility(nxpFacility));
    }

    /**
     * 删除设备管理
     */
    @RequiresPermissions("system:facility:remove")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpFacilityService.deleteNxpFacilityByIds(ids));
    }

    /**
     * 根据machineModel和productTypeId查询
     * @param nxpMold
     * @return
     */
    @PostMapping("/selectFacilityByModelAndProductTypeId")
    @ResponseBody
    public AjaxResult selectFacilityByModelAndProductTypeId(NxpMold nxpMold){
        return AjaxResult.success(nxpFacilityService.selectFacilityByModelAndProductTypeId(nxpMold));
    }
}
