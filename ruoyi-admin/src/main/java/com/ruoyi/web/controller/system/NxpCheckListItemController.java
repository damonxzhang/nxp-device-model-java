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
import com.ruoyi.system.domain.NxpCheckListItem;
import com.ruoyi.system.service.INxpCheckListItemService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查列表检查项Controller
 * 
 * @author YanCheng Liu
 * @date 2022-10-02
 */
@Controller
@RequestMapping("/system/nxpCheckListItem")
public class NxpCheckListItemController extends BaseController
{
    private String prefix = "system/nxpCheckListItem";

    @Autowired
    private INxpCheckListItemService nxpCheckListItemService;

    @RequiresPermissions("system:nxpCheckListItem:view")
    @GetMapping()
    public String nxpCheckListItem()
    {
        return prefix + "/nxpCheckListItem";
    }

    /**
     * 查询检查列表检查项列表
     */
    @RequiresPermissions("system:nxpCheckListItem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpCheckListItem nxpCheckListItem)
    {
        startPage();
        List<NxpCheckListItem> list = nxpCheckListItemService.selectNxpCheckListItemList(nxpCheckListItem);
        return getDataTable(list);
    }

    /**
     * 导出检查列表检查项列表
     */
    @RequiresPermissions("system:nxpCheckListItem:export")
    @Log(title = "检查列表检查项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpCheckListItem nxpCheckListItem)
    {
        List<NxpCheckListItem> list = nxpCheckListItemService.selectNxpCheckListItemList(nxpCheckListItem);
        ExcelUtil<NxpCheckListItem> util = new ExcelUtil<NxpCheckListItem>(NxpCheckListItem.class);
        return util.exportExcel(list, "nxpCheckListItem");
    }

    /**
     * 新增检查列表检查项
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存检查列表检查项
     */
    @RequiresPermissions("system:nxpCheckListItem:add")
    @Log(title = "检查列表检查项", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpCheckListItem nxpCheckListItem)
    {
        return toAjax(nxpCheckListItemService.insertNxpCheckListItem(nxpCheckListItem));
    }

    /**
     * 修改检查列表检查项
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpCheckListItem nxpCheckListItem = nxpCheckListItemService.selectNxpCheckListItemById(id);
        mmap.put("nxpCheckListItem", nxpCheckListItem);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查列表检查项
     */
    @RequiresPermissions("system:nxpCheckListItem:edit")
    @Log(title = "检查列表检查项", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpCheckListItem nxpCheckListItem)
    {
        return toAjax(nxpCheckListItemService.updateNxpCheckListItem(nxpCheckListItem));
    }

    /**
     * 删除检查列表检查项
     */
    @RequiresPermissions("system:nxpCheckListItem:remove")
    @Log(title = "检查列表检查项", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpCheckListItemService.deleteNxpCheckListItemByIds(ids));
    }
}
