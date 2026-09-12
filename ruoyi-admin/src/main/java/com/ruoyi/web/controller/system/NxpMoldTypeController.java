package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMachineModel;
import com.ruoyi.system.domain.NxpMoldTypeComponent;
import com.ruoyi.system.service.INxpMachineModelService;
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
import com.ruoyi.system.domain.NxpMoldType;
import com.ruoyi.system.service.INxpMoldTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 部件种类Controller
 * 
 * @author YanCheng Liu
 * @date 2023-10-06
 */
@Controller
@RequestMapping("/system/nxpMoldType")
public class NxpMoldTypeController extends BaseController
{
    private String prefix = "system/nxpMoldType";

    @Autowired
    private INxpMoldTypeService nxpMoldTypeService;
    @Autowired
    private INxpMachineModelService nxpMachineModelService;

    @RequiresPermissions("system:nxpMoldType:view")
    @GetMapping()
    public String nxpMoldType()
    {
        return prefix + "/nxpMoldType";
    }

    /**
     * 查询部件种类列表
     */
    @RequiresPermissions("system:nxpMoldType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldType nxpMoldType)
    {
        startPage();
        nxpMoldType.setMoldType(1);
        List<NxpMoldType> list = nxpMoldTypeService.selectNxpMoldTypeList(nxpMoldType);
        return getDataTable(list);
    }

    /**
     * 导出部件种类列表
     */
    @RequiresPermissions("system:nxpMoldType:export")
    @Log(title = "部件种类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldType nxpMoldType)
    {
        List<NxpMoldType> list = nxpMoldTypeService.selectNxpMoldTypeList(nxpMoldType);
        ExcelUtil<NxpMoldType> util = new ExcelUtil<NxpMoldType>(NxpMoldType.class);
        return util.exportExcel(list, "nxpMoldType");
    }

    /**
     * 新增部件种类
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelListMold(nxpMachineModel);
        mmap.put("machineModels",nxpMachineModels);
        return prefix + "/add";
    }

    /**
     * 新增保存部件种类
     */
    @RequiresPermissions("system:nxpMoldType:add")
    @Log(title = "部件种类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldType nxpMoldType)
    {
        return toAjax(nxpMoldTypeService.insertNxpMoldType(nxpMoldType));
    }

    /**
     * 修改部件种类
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldType nxpMoldType = nxpMoldTypeService.selectNxpMoldTypeById(id);
        mmap.put("nxpMoldType", nxpMoldType);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelListByMoldType(nxpMoldType);
        mmap.put("machineModels",nxpMachineModels);
        return prefix + "/edit";
    }

    /**
     * 修改保存部件种类
     */
    @RequiresPermissions("system:nxpMoldType:edit")
    @Log(title = "部件种类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldType nxpMoldType)
    {
        return toAjax(nxpMoldTypeService.updateNxpMoldType(nxpMoldType));
    }

    /**
     * 删除部件种类
     */
    @RequiresPermissions("system:nxpMoldType:remove")
    @Log(title = "部件种类", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldTypeService.deleteNxpMoldTypeByIds(ids));
    }

    @PostMapping("/getByMoldTypeId")
    @ResponseBody
    public AjaxResult getByMoldTypeId(NxpMoldTypeComponent nxpMoldTypeComponent){
        return AjaxResult.success(nxpMoldTypeService.getByMoldTypeId(nxpMoldTypeComponent));
    }
}
