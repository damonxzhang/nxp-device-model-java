package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.domain.NxpMoldType;
import com.ruoyi.system.domain.NxpMoldTypeComponent;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupService;
import com.ruoyi.system.service.INxpMoldTypeService;
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
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupItemService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模具维护检查项Controller
 * 
 * @author YanCheng Liu
 * @date 2023-08-22
 */
@Controller
@RequestMapping("/system/nxpMoldMaintenanceGroupItem")
public class NxpMoldMaintenanceGroupItemController extends BaseController
{
    private String prefix = "system/nxpMoldMaintenanceGroupItem";

    @Autowired
    private INxpMoldMaintenanceGroupItemService nxpMoldMaintenanceGroupItemService;
    @Autowired
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;
    @Autowired
    private INxpMoldTypeService nxpMoldTypeService;

    @RequiresPermissions("system:nxpMoldMaintenanceGroupItem:view")
    @GetMapping()
    public String nxpMoldMaintenanceGroupItem(ModelMap mmap)
    {
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setStatus(1);
        nxpMoldMaintenanceGroup.setMoldType(1);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("groups",nxpMoldMaintenanceGroups);
        NxpMoldType nxpMoldType = new NxpMoldType();
        nxpMoldType.setStatus(1);
        nxpMoldType.setMoldType(1);
        List<NxpMoldType> nxpMoldTypes = nxpMoldTypeService.selectNxpMoldTypeList(nxpMoldType);
        mmap.put("nxpMoldTypes",nxpMoldTypes);
        return prefix + "/nxpMoldMaintenanceGroupItem";
    }

    /**
     * 查询模具维护检查项列表
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroupItem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem)
    {
        startPage();
        nxpMoldMaintenanceGroupItem.setMoldType(1);
        List<NxpMoldMaintenanceGroupItem> list = nxpMoldMaintenanceGroupItemService.selectNxpMoldMaintenanceGroupItemList(nxpMoldMaintenanceGroupItem);
        return getDataTable(list);
    }

    /**
     * 导出模具维护检查项列表
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroupItem:export")
    @Log(title = "模具维护检查项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem)
    {
        List<NxpMoldMaintenanceGroupItem> list = nxpMoldMaintenanceGroupItemService.selectNxpMoldMaintenanceGroupItemList(nxpMoldMaintenanceGroupItem);
        ExcelUtil<NxpMoldMaintenanceGroupItem> util = new ExcelUtil<NxpMoldMaintenanceGroupItem>(NxpMoldMaintenanceGroupItem.class);
        return util.exportExcel(list, "nxpMoldMaintenanceGroupItem");
    }

    /**
     * 新增模具维护检查项
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setStatus(1);
        nxpMoldMaintenanceGroup.setMoldType(1);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("groups",nxpMoldMaintenanceGroups);
        NxpMoldType nxpMoldType = new NxpMoldType();
        nxpMoldType.setStatus(1);
        nxpMoldType.setMoldType(1);
        List<NxpMoldType> nxpMoldTypes = nxpMoldTypeService.selectNxpMoldTypeList(nxpMoldType);
        mmap.put("nxpMoldTypes",nxpMoldTypes);
        return prefix + "/add";
    }

    /**
     * 新增保存模具维护检查项
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroupItem:add")
    @Log(title = "模具维护检查项", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem)
    {
        return toAjax(nxpMoldMaintenanceGroupItemService.insertNxpMoldMaintenanceGroupItem(nxpMoldMaintenanceGroupItem));
    }

    /**
     * 修改模具维护检查项
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem = nxpMoldMaintenanceGroupItemService.selectNxpMoldMaintenanceGroupItemById(id);
        mmap.put("nxpMoldMaintenanceGroupItem", nxpMoldMaintenanceGroupItem);
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = new NxpMoldMaintenanceGroup();
        nxpMoldMaintenanceGroup.setStatus(1);
        nxpMoldMaintenanceGroup.setMoldType(1);
        List<NxpMoldMaintenanceGroup> nxpMoldMaintenanceGroups = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        mmap.put("groups",nxpMoldMaintenanceGroups);
        NxpMoldType nxpMoldType = new NxpMoldType();
        nxpMoldType.setStatus(1);
        nxpMoldType.setMoldType(1);
        List<NxpMoldType> nxpMoldTypes = nxpMoldTypeService.selectNxpMoldTypeList(nxpMoldType);
        mmap.put("nxpMoldTypes",nxpMoldTypes);
        Long moldTypeId = nxpMoldMaintenanceGroupItem.getMoldTypeId();
        if (moldTypeId != null){
            // 这里查询部件
            NxpMoldTypeComponent nxpMoldTypeComponent = new NxpMoldTypeComponent();
            nxpMoldTypeComponent.setMoldTypeId(moldTypeId);
            nxpMoldTypeComponent.setStatus(1);
            List<NxpMoldTypeComponent> nxpMoldTypeComponents = nxpMoldTypeService.getByMoldTypeId(nxpMoldTypeComponent);
            mmap.put("nxpMoldTypeComponents",nxpMoldTypeComponents);
        }
        return prefix + "/edit";
    }

    /**
     * 修改保存模具维护检查项
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroupItem:edit")
    @Log(title = "模具维护检查项", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem)
    {
        return toAjax(nxpMoldMaintenanceGroupItemService.updateNxpMoldMaintenanceGroupItem(nxpMoldMaintenanceGroupItem));
    }

    /**
     * 删除模具维护检查项
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroupItem:remove")
    @Log(title = "模具维护检查项", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldMaintenanceGroupItemService.deleteNxpMoldMaintenanceGroupItemByIds(ids));
    }
}
