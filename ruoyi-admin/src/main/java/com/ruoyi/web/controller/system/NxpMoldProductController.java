package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpProductType;
import com.ruoyi.system.service.INxpMoldService;
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
import com.ruoyi.system.domain.NxpMoldProduct;
import com.ruoyi.system.service.INxpMoldProductService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模具&产品类型Controller
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Controller
@RequestMapping("/system/product")
public class NxpMoldProductController extends BaseController
{
    private String prefix = "system/product";

    @Autowired
    private INxpMoldProductService nxpMoldProductService;
    @Autowired
    private INxpMoldService nxpMoldService;
    @Autowired
    private INxpProductTypeService nxpProductTypeService;

    @RequiresPermissions("system:product:view")
    @GetMapping()
    public String product(ModelMap mmap)
    {
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListNew(new NxpMold());
        mmap.put("nxpMolds",nxpMolds);
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        mmap.put("nxpProductTypes",nxpProductTypes);
        return prefix + "/product";
    }

    /**
     * 查询模具&产品类型列表
     */
    @RequiresPermissions("system:product:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldProduct nxpMoldProduct)
    {
        startPage();
        List<NxpMoldProduct> list = nxpMoldProductService.selectNxpMoldProductList(nxpMoldProduct);
        return getDataTable(list);
    }

    /**
     * 导出模具&产品类型列表
     */
    @RequiresPermissions("system:product:export")
    @Log(title = "模具&产品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldProduct nxpMoldProduct)
    {
        List<NxpMoldProduct> list = nxpMoldProductService.selectNxpMoldProductList(nxpMoldProduct);
        ExcelUtil<NxpMoldProduct> util = new ExcelUtil<NxpMoldProduct>(NxpMoldProduct.class);
        return util.exportExcel(list, "product");
    }

    /**
     * 新增模具&产品类型
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListNew(new NxpMold());
        mmap.put("nxpMolds",nxpMolds);
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        mmap.put("nxpProductTypes",nxpProductTypes);
        return prefix + "/add";
    }

    /**
     * 新增保存模具&产品类型
     */
    @RequiresPermissions("system:product:add")
    @Log(title = "模具&产品类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldProduct nxpMoldProduct)
    {
        return toAjax(nxpMoldProductService.insertNxpMoldProduct(nxpMoldProduct));
    }

    /**
     * 修改模具&产品类型
     */
    @GetMapping("/edit/{moldId}")
    public String edit(@PathVariable("moldId") Long moldId, ModelMap mmap)
    {
        NxpMoldProduct nxpMoldProduct = nxpMoldProductService.selectNxpMoldProductById(moldId);
        mmap.put("nxpMoldProduct", nxpMoldProduct);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListNew(new NxpMold());
        mmap.put("nxpMolds",nxpMolds);
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeService.selectNxpProductTypeList(nxpProductType);
        mmap.put("nxpProductTypes",nxpProductTypes);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具&产品类型
     */
    @RequiresPermissions("system:product:edit")
    @Log(title = "模具&产品类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldProduct nxpMoldProduct)
    {
        return toAjax(nxpMoldProductService.updateNxpMoldProduct(nxpMoldProduct));
    }

    /**
     * 删除模具&产品类型
     */
    @RequiresPermissions("system:product:remove")
    @Log(title = "模具&产品类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldProductService.deleteNxpMoldProductByIds(ids));
    }
}
