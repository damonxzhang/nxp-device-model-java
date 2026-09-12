package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpProductType;
import com.ruoyi.system.service.INxpFacilityService;
import com.ruoyi.system.service.INxpProductTypeService;
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
import com.ruoyi.system.domain.NxpFacilityProductType;
import com.ruoyi.system.service.INxpFacilityProductTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备&产品类型Controller
 * 
 * @author YanCheng Liu
 * @date 2023-06-27
 */
@Controller
@RequestMapping("/system/nxpFacilityProductType")
public class NxpFacilityProductTypeController extends BaseController
{
    private String prefix = "system/nxpFacilityProductType";

    @Autowired
    private INxpFacilityProductTypeService nxpFacilityProductTypeService;
    @Autowired
    private INxpProductTypeService nxpProductTypeService;
    @Autowired
    private INxpFacilityService nxpFacilityService;

    @RequiresPermissions("system:nxpFacilityProductType:view")
    @GetMapping()
    public String nxpFacilityProductType(ModelMap mmap)
    {
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(new NxpFacility());
        mmap.put("nxpFacilities",nxpFacilities);
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        mmap.put("nxpProductTypes",nxpProductTypes);
        return prefix + "/nxpFacilityProductType";
    }

    /**
     * 查询设备&产品类型列表
     */
    @RequiresPermissions("system:nxpFacilityProductType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpFacilityProductType nxpFacilityProductType)
    {
        startPage();
        List<NxpFacilityProductType> list = nxpFacilityProductTypeService.selectNxpFacilityProductTypeList(nxpFacilityProductType);
        return getDataTable(list);
    }

    /**
     * 导出设备&产品类型列表
     */
    @RequiresPermissions("system:nxpFacilityProductType:export")
    @Log(title = "设备&产品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpFacilityProductType nxpFacilityProductType)
    {
        List<NxpFacilityProductType> list = nxpFacilityProductTypeService.selectNxpFacilityProductTypeList(nxpFacilityProductType);
        ExcelUtil<NxpFacilityProductType> util = new ExcelUtil<NxpFacilityProductType>(NxpFacilityProductType.class);
        return util.exportExcel(list, "nxpFacilityProductType");
    }

    /**
     * 新增设备&产品类型
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(new NxpFacility());
        mmap.put("nxpFacilities",nxpFacilities);
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        mmap.put("nxpProductTypes",nxpProductTypes);
        return prefix + "/add";
    }

    /**
     * 新增保存设备&产品类型
     */
    @RequiresPermissions("system:nxpFacilityProductType:add")
    @Log(title = "设备&产品类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpFacilityProductType nxpFacilityProductType)
    {
        return toAjax(nxpFacilityProductTypeService.insertNxpFacilityProductType(nxpFacilityProductType));
    }

    /**
     * 修改设备&产品类型
     */
    @GetMapping("/edit/{facilityId}")
    public String edit(@PathVariable("facilityId") Long facilityId, ModelMap mmap)
    {
        NxpFacilityProductType nxpFacilityProductType = nxpFacilityProductTypeService.selectNxpFacilityProductTypeById(facilityId);
        mmap.put("nxpFacilityProductType", nxpFacilityProductType);
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(new NxpFacility());
        mmap.put("nxpFacilities",nxpFacilities);
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeByFacilityId(facilityId);
        mmap.put("nxpProductTypes",nxpProductTypes);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备&产品类型
     */
    @RequiresPermissions("system:nxpFacilityProductType:edit")
    @Log(title = "设备&产品类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpFacilityProductType nxpFacilityProductType)
    {
        return toAjax(nxpFacilityProductTypeService.updateNxpFacilityProductType(nxpFacilityProductType));
    }

    /**
     * 删除设备&产品类型
     */
    @RequiresPermissions("system:nxpFacilityProductType:remove")
    @Log(title = "设备&产品类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpFacilityProductTypeService.deleteNxpFacilityProductTypeByIds(ids));
    }
}
