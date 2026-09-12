package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.annotation.Resource;

/**
 * 模具管理Controller
 * 
 * @author YanCheng Liu
 * @date 2023-06-23
 */
@Controller
@RequestMapping("/system/nxpMold")
public class NxpMoldController extends BaseController
{
    private String prefix = "system/nxpMold";

    @Autowired
    private INxpMoldService nxpMoldService;
    @Resource
    private INxpProductTypeService nxpProductTypeService;
    @Resource
    private INxpSmartCabinetService nxpSmartCabinetService;
    @Autowired
    private INxpMachineModelService nxpMachineModelService;
    @Resource
    private INxpFacilityService nxpFacilityService;

    @RequiresPermissions("system:nxpMold:view")
    @GetMapping()
    public String nxpMold(ModelMap mmap)
    {
        NxpSmartCabinet nxpSmartCabinet = new NxpSmartCabinet();
        nxpSmartCabinet.setStatus(1);
        nxpSmartCabinet.setMoldType(1);
        List<NxpSmartCabinet> nxpSmartCabinets = nxpSmartCabinetService.selectNxpSmartCabinetList(nxpSmartCabinet);
        mmap.put("nxpSmartCabinets",nxpSmartCabinets);
        return prefix + "/nxpMold";
    }

    /**
     * 查询模具管理列表
     */
    @RequiresPermissions("system:nxpMold:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMold nxpMold)
    {
        startPage();
        nxpMold.setMoldType(1);
        List<NxpMold> list = nxpMoldService.selectNxpMoldList(nxpMold);
        return getDataTable(list);
    }

    /**
     * 导出模具管理列表
     */
    @RequiresPermissions("system:nxpMold:export")
    @Log(title = "模具管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMold nxpMold)
    {
        List<NxpMold> list = nxpMoldService.selectNxpMoldList(nxpMold);
        ExcelUtil<NxpMold> util = new ExcelUtil<NxpMold>(NxpMold.class);
        return util.exportExcel(list, "nxpMold");
    }

    /**
     * 新增模具管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setMoldType(1);
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        mmap.put("productTypes",nxpProductTypes);
        NxpSmartCabinet nxpSmartCabinet = new NxpSmartCabinet();
        nxpSmartCabinet.setStatus(1);
        nxpSmartCabinet.setMoldType(1);
        List<NxpSmartCabinet> nxpSmartCabinets = nxpSmartCabinetService.selectNxpSmartCabinetList(nxpSmartCabinet);
        mmap.put("nxpSmartCabinets",nxpSmartCabinets);
        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelListMold(nxpMachineModel);
        mmap.put("machineModels",nxpMachineModels);
        return prefix + "/add";
    }

    /**
     * 新增保存模具管理
     */
    @RequiresPermissions("system:nxpMold:add")
    @Log(title = "模具管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMold nxpMold)
    {
        return toAjax(nxpMoldService.insertNxpMold(nxpMold));
    }

    /**
     * 修改模具管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMold nxpMold = nxpMoldService.selectNxpMoldById(id);
        mmap.put("nxpMold", nxpMold);
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        nxpProductType.setMoldType(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeByMoldId(id);
        mmap.put("productTypes",nxpProductTypes);
        NxpSmartCabinet nxpSmartCabinet = new NxpSmartCabinet();
        nxpSmartCabinet.setStatus(1);
        nxpSmartCabinet.setMoldType(1);
        List<NxpSmartCabinet> nxpSmartCabinets = nxpSmartCabinetService.selectNxpSmartCabinetList(nxpSmartCabinet);
        mmap.put("nxpSmartCabinets",nxpSmartCabinets);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelListByMold(nxpMold);
        mmap.put("machineModels",nxpMachineModels);
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        nxpFacility.setProcess("9");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("nxpFacilities",nxpFacilities);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具管理
     */
    @RequiresPermissions("system:nxpMold:edit")
    @Log(title = "模具管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMold nxpMold)
    {
        return toAjax(nxpMoldService.updateNxpMold(nxpMold));
    }

    /**
     * 删除模具管理
     */
    @RequiresPermissions("system:nxpMold:remove")
    @Log(title = "模具管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldService.deleteNxpMoldByIds(ids));
    }

    @GetMapping("/openComponentList/{id}")
    public String openMaintenanceMessage(@PathVariable("id") Long id, ModelMap mmap){
        mmap.put("moldId",id);
        return prefix + "/nxpMoldComponent";
    }
}
