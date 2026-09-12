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
import com.ruoyi.system.domain.NxpInspectionServiceSparepart;
import com.ruoyi.system.service.INxpInspectionServiceSparepartService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维修设备备件Controller
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/inspectionServiceSparepart")
public class NxpInspectionServiceSparepartController extends BaseController
{
    private String prefix = "system/inspectionServiceSparepart";

    @Autowired
    private INxpInspectionServiceSparepartService nxpInspectionServiceSparepartService;

    @RequiresPermissions("system:inspectionServiceSparepart:view")
    @GetMapping()
    public String inspectionServiceSparepart()
    {
        return prefix + "/inspectionServiceSparepart";
    }

    /**
     * 查询维修设备备件列表
     */
    @RequiresPermissions("system:inspectionServiceSparepart:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionServiceSparepart nxpInspectionServiceSparepart)
    {
        startPage();
        List<NxpInspectionServiceSparepart> list = nxpInspectionServiceSparepartService.selectNxpInspectionServiceSparepartList(nxpInspectionServiceSparepart);
        return getDataTable(list);
    }

    /**
     * 导出维修设备备件列表
     */
    @RequiresPermissions("system:inspectionServiceSparepart:export")
    @Log(title = "维修设备备件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionServiceSparepart nxpInspectionServiceSparepart)
    {
        List<NxpInspectionServiceSparepart> list = nxpInspectionServiceSparepartService.selectNxpInspectionServiceSparepartList(nxpInspectionServiceSparepart);
        ExcelUtil<NxpInspectionServiceSparepart> util = new ExcelUtil<NxpInspectionServiceSparepart>(NxpInspectionServiceSparepart.class);
        return util.exportExcel(list, "inspectionServiceSparepart");
    }

    /**
     * 新增维修设备备件
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存维修设备备件
     */
    @RequiresPermissions("system:inspectionServiceSparepart:add")
    @Log(title = "维修设备备件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionServiceSparepart nxpInspectionServiceSparepart)
    {
        return toAjax(nxpInspectionServiceSparepartService.insertNxpInspectionServiceSparepart(nxpInspectionServiceSparepart));
    }

    /**
     * 修改维修设备备件
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionServiceSparepart nxpInspectionServiceSparepart = nxpInspectionServiceSparepartService.selectNxpInspectionServiceSparepartById(id);
        mmap.put("nxpInspectionServiceSparepart", nxpInspectionServiceSparepart);
        return prefix + "/edit";
    }

    /**
     * 修改保存维修设备备件
     */
    @RequiresPermissions("system:inspectionServiceSparepart:edit")
    @Log(title = "维修设备备件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionServiceSparepart nxpInspectionServiceSparepart)
    {
        return toAjax(nxpInspectionServiceSparepartService.updateNxpInspectionServiceSparepart(nxpInspectionServiceSparepart));
    }

    /**
     * 删除维修设备备件
     */
    @RequiresPermissions("system:inspectionServiceSparepart:remove")
    @Log(title = "维修设备备件", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionServiceSparepartService.deleteNxpInspectionServiceSparepartByIds(ids));
    }
}
