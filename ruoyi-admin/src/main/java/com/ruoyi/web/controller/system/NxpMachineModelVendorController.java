package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMachineModel;
import com.ruoyi.system.domain.NxpMachineVendor;
import com.ruoyi.system.service.INxpMachineModelService;
import com.ruoyi.system.service.INxpMachineVendorService;
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
import com.ruoyi.system.domain.NxpMachineModelVendor;
import com.ruoyi.system.service.INxpMachineModelVendorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机型&vendor中间表Controller
 * 
 * @author DamonZhang
 * @date 2021-05-07
 */
@Controller
@RequestMapping("/system/MachineModelVendor")
public class NxpMachineModelVendorController extends BaseController
{
    private String prefix = "system/MachineModelVendor";

    @Autowired
    private INxpMachineModelVendorService nxpMachineModelVendorService;
    @Autowired
    private INxpMachineModelService nxpMachineModelService;
    @Autowired
    private INxpMachineVendorService nxpMachineVendorService;

    @RequiresPermissions("system:MachineModelVendor:view")
    @GetMapping()
    public String MachineModelVendor(ModelMap mmap)
    {
        NxpMachineVendor nxpMachineVendor = new NxpMachineVendor();
        nxpMachineVendor.setStatus(1);
        List<NxpMachineVendor> nxpMachineVendors = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);
        mmap.put("nxpMachineVendors",nxpMachineVendors);

        return prefix + "/MachineModelVendor";
    }

    /**
     * 查询机型&vendor中间表列表
     */
    @RequiresPermissions("system:MachineModelVendor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMachineModelVendor nxpMachineModelVendor)
    {
        startPage();
        List<NxpMachineModelVendor> list = nxpMachineModelVendorService.selectNxpMachineModelVendorList(nxpMachineModelVendor);

        for (NxpMachineModelVendor machineModelVendor : list) {
            NxpMachineModel nxpMachineModel = nxpMachineModelService.selectNxpMachineModelById(machineModelVendor.getModelId());
            if (nxpMachineModel != null)
                machineModelVendor.setModelName(nxpMachineModel.getName());

            NxpMachineVendor nxpMachineVendor = nxpMachineVendorService.selectNxpMachineVendorById(machineModelVendor.getVendorId());
            if (nxpMachineVendor != null)
                machineModelVendor.setVendorName(nxpMachineVendor.getName());
        }


        return getDataTable(list);
    }

    /**
     * 导出机型&vendor中间表列表
     */
    @RequiresPermissions("system:MachineModelVendor:export")
    @Log(title = "机型&vendor中间表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMachineModelVendor nxpMachineModelVendor)
    {
        List<NxpMachineModelVendor> list = nxpMachineModelVendorService.selectNxpMachineModelVendorList(nxpMachineModelVendor);
        ExcelUtil<NxpMachineModelVendor> util = new ExcelUtil<NxpMachineModelVendor>(NxpMachineModelVendor.class);
        return util.exportExcel(list, "MachineModelVendor");
    }

    /**
     * 新增机型&vendor中间表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMachineVendor nxpMachineVendor = new NxpMachineVendor();
        nxpMachineVendor.setStatus(1);
        List<NxpMachineVendor> nxpMachineVendors = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);

        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);

        mmap.put("vendorList",nxpMachineVendors);
        mmap.put("modelList",nxpMachineModels);

        return prefix + "/add";
    }

    /**
     * 新增保存机型&vendor中间表
     */
    @RequiresPermissions("system:MachineModelVendor:add")
    @Log(title = "机型&vendor中间表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMachineModelVendor nxpMachineModelVendor)
    {
        return toAjax(nxpMachineModelVendorService.insertNxpMachineModelVendor(nxpMachineModelVendor));
    }

    /**
     * 修改机型&vendor中间表
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMachineModelVendor nxpMachineModelVendor = nxpMachineModelVendorService.selectNxpMachineModelVendorById(id);
        mmap.put("nxpMachineModelVendor", nxpMachineModelVendor);

        NxpMachineVendor nxpMachineVendor = new NxpMachineVendor();
        nxpMachineVendor.setStatus(1);
        List<NxpMachineVendor> nxpMachineVendors = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);

        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);

        mmap.put("machineModel", nxpMachineModels);
        mmap.put("machineVendor", nxpMachineVendors);

        return prefix + "/edit";
    }

    /**
     * 修改保存机型&vendor中间表
     */
    @RequiresPermissions("system:MachineModelVendor:edit")
    @Log(title = "机型&vendor中间表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMachineModelVendor nxpMachineModelVendor)
    {
        return toAjax(nxpMachineModelVendorService.updateNxpMachineModelVendor(nxpMachineModelVendor));
    }

    /**
     * 删除机型&vendor中间表
     */
    @RequiresPermissions("system:MachineModelVendor:remove")
    @Log(title = "机型&vendor中间表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMachineModelVendorService.deleteNxpMachineModelVendorByIds(ids));
    }
}
