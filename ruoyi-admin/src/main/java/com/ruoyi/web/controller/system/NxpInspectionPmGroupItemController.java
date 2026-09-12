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
import com.ruoyi.system.domain.NxpInspectionPmGroupItem;
import com.ruoyi.system.service.INxpInspectionPmGroupItemService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 周PM检查项Controller
 * 
 * @author ruoyi
 * @date 2021-02-08
 */
@Controller
@RequestMapping("/system/inspectionPmGroupItem")
public class NxpInspectionPmGroupItemController extends BaseController
{
    private String prefix = "system/inspectionPmGroupItem";

    @Autowired
    private INxpInspectionPmGroupItemService nxpInspectionPmGroupItemService;

    @RequiresPermissions("system:inspectionPmGroupItem:view")
    @GetMapping()
    public String inspectionPmGroupItem()
    {
        return prefix + "/inspectionPmGroupItem";
    }

    /**
     * 查询周PM检查项列表
     */
    @RequiresPermissions("system:inspectionPmGroupItem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPmGroupItem nxpInspectionPmGroupItem)
    {
        startPage();
        List<NxpInspectionPmGroupItem> list = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemList(nxpInspectionPmGroupItem);
        return getDataTable(list);
    }

    /**
     * 导出周PM检查项列表
     */
    @RequiresPermissions("system:inspectionPmGroupItem:export")
    @Log(title = "周PM检查项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPmGroupItem nxpInspectionPmGroupItem)
    {
        List<NxpInspectionPmGroupItem> list = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemList(nxpInspectionPmGroupItem);
        ExcelUtil<NxpInspectionPmGroupItem> util = new ExcelUtil<NxpInspectionPmGroupItem>(NxpInspectionPmGroupItem.class);
        return util.exportExcel(list, "inspectionPmGroupItem");
    }

    /**
     * 新增周PM检查项
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存周PM检查项
     */
    @RequiresPermissions("system:inspectionPmGroupItem:add")
    @Log(title = "周PM检查项", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPmGroupItem nxpInspectionPmGroupItem)
    {
        return toAjax(nxpInspectionPmGroupItemService.insertNxpInspectionPmGroupItem(nxpInspectionPmGroupItem));
    }

    /**
     * 修改周PM检查项
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemById(id);
        mmap.put("nxpInspectionPmGroupItem", nxpInspectionPmGroupItem);
        return prefix + "/edit";
    }

    /**
     * 修改保存周PM检查项
     */
    @RequiresPermissions("system:inspectionPmGroupItem:edit")
    @Log(title = "周PM检查项", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPmGroupItem nxpInspectionPmGroupItem)
    {
        return toAjax(nxpInspectionPmGroupItemService.updateNxpInspectionPmGroupItem(nxpInspectionPmGroupItem));
    }

    /**
     * 删除周PM检查项
     */
    @RequiresPermissions("system:inspectionPmGroupItem:remove")
    @Log(title = "周PM检查项", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPmGroupItemService.deleteNxpInspectionPmGroupItemByIds(ids));
    }
}
