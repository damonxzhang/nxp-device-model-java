package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.NxpProductType;
import com.ruoyi.system.service.INxpProductTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品类型Controller
 * 
 * @author YanCheng Liu
 * @date 2023-06-23
 */
@Controller
@RequestMapping("/system/baNxpProductType")
public class BaNxpProductTypeController extends BaseController
{
    private String prefix = "system/baNxpProductType";

    @Autowired
    private INxpProductTypeService nxpProductTypeService;

    @RequiresPermissions("system:baNxpProductType:view")
    @GetMapping()
    public String nxpProductType()
    {
        return prefix + "/nxpProductType";
    }

    /**
     * 查询产品类型列表
     */
    @RequiresPermissions("system:baNxpProductType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpProductType nxpProductType)
    {
        startPage();
        nxpProductType.setMoldType(2);
        List<NxpProductType> list = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        return getDataTable(list);
    }

    /**
     * 导出产品类型列表
     */
    @RequiresPermissions("system:baNxpProductType:export")
    @Log(title = "产品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpProductType nxpProductType)
    {
        List<NxpProductType> list = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        ExcelUtil<NxpProductType> util = new ExcelUtil<NxpProductType>(NxpProductType.class);
        return util.exportExcel(list, "nxpProductType");
    }

    /**
     * 新增产品类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存产品类型
     */
    @RequiresPermissions("system:baNxpProductType:add")
    @Log(title = "产品类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpProductType nxpProductType)
    {
        return toAjax(nxpProductTypeService.insertNxpProductType(nxpProductType));
    }

    /**
     * 修改产品类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpProductType nxpProductType = nxpProductTypeService.selectNxpProductTypeById(id);
        mmap.put("nxpProductType", nxpProductType);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品类型
     */
    @RequiresPermissions("system:baNxpProductType:edit")
    @Log(title = "产品类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpProductType nxpProductType)
    {
        return toAjax(nxpProductTypeService.updateNxpProductType(nxpProductType));
    }

    /**
     * 删除产品类型
     */
    @RequiresPermissions("system:baNxpProductType:remove")
    @Log(title = "产品类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpProductTypeService.deleteNxpProductTypeByIds(ids));
    }
}
