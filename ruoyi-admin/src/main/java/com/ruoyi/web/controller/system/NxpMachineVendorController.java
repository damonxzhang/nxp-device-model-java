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
import com.ruoyi.system.domain.NxpMachineVendor;
import com.ruoyi.system.service.INxpMachineVendorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备供应商Controller
 * 
 * @author DamonZhang
 * @date 2021-05-07
 */
@Controller
@RequestMapping("/system/MachineVendor")
public class NxpMachineVendorController extends BaseController
{
    private String prefix = "system/MachineVendor";

    @Autowired
    private INxpMachineVendorService nxpMachineVendorService;

    @RequiresPermissions("system:MachineVendor:view")
    @GetMapping()
    public String MachineVendor()
    {
        return prefix + "/MachineVendor";
    }

    /**
     * 查询设备供应商列表
     */
    @RequiresPermissions("system:MachineVendor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMachineVendor nxpMachineVendor)
    {
        startPage();
        List<NxpMachineVendor> list = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);
        return getDataTable(list);
    }

    /**
     * 导出设备供应商列表
     */
    @RequiresPermissions("system:MachineVendor:export")
    @Log(title = "设备供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMachineVendor nxpMachineVendor)
    {
        List<NxpMachineVendor> list = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);
        ExcelUtil<NxpMachineVendor> util = new ExcelUtil<NxpMachineVendor>(NxpMachineVendor.class);
        return util.exportExcel(list, "MachineVendor");
    }

    /**
     * 新增设备供应商
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备供应商
     */
    @RequiresPermissions("system:MachineVendor:add")
    @Log(title = "设备供应商", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMachineVendor nxpMachineVendor)
    {
        return toAjax(nxpMachineVendorService.insertNxpMachineVendor(nxpMachineVendor));
    }

    /**
     * 修改设备供应商
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMachineVendor nxpMachineVendor = nxpMachineVendorService.selectNxpMachineVendorById(id);
        mmap.put("nxpMachineVendor", nxpMachineVendor);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备供应商
     */
    @RequiresPermissions("system:MachineVendor:edit")
    @Log(title = "设备供应商", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMachineVendor nxpMachineVendor)
    {
        return toAjax(nxpMachineVendorService.updateNxpMachineVendor(nxpMachineVendor));
    }

    /**
     * 删除设备供应商
     */
    @RequiresPermissions("system:MachineVendor:remove")
    @Log(title = "设备供应商", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMachineVendorService.deleteNxpMachineVendorByIds(ids));
    }
}
