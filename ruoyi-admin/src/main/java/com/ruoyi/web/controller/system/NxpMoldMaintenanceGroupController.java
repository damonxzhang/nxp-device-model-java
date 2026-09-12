package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.core.text.Convert;
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
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模具维护分组Controller
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Controller
@RequestMapping("/system/nxpMoldMaintenanceGroup")
public class NxpMoldMaintenanceGroupController extends BaseController
{
    private String prefix = "system/nxpMoldMaintenanceGroup";

    @Autowired
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;

    @RequiresPermissions("system:nxpMoldMaintenanceGroup:view")
    @GetMapping()
    public String nxpMoldMaintenanceGroup()
    {
        return prefix + "/nxpMoldMaintenanceGroup";
    }

    /**
     * 查询模具维护分组列表
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroup:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        startPage();
        nxpMoldMaintenanceGroup.setMoldType(1);
        List<NxpMoldMaintenanceGroup> list = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        return getDataTable(list);
    }

    /**
     * 导出模具维护分组列表
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroup:export")
    @Log(title = "模具维护分组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        List<NxpMoldMaintenanceGroup> list = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupList(nxpMoldMaintenanceGroup);
        ExcelUtil<NxpMoldMaintenanceGroup> util = new ExcelUtil<NxpMoldMaintenanceGroup>(NxpMoldMaintenanceGroup.class);
        return util.exportExcel(list, "nxpMoldMaintenanceGroup");
    }

    /**
     * 新增模具维护分组
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存模具维护分组
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroup:add")
    @Log(title = "模具维护分组", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        return toAjax(nxpMoldMaintenanceGroupService.insertNxpMoldMaintenanceGroup(nxpMoldMaintenanceGroup));
    }

    /**
     * 修改模具维护分组
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupById(id);
        mmap.put("nxpMoldMaintenanceGroup", nxpMoldMaintenanceGroup);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具维护分组
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroup:edit")
    @Log(title = "模具维护分组", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup)
    {
        return toAjax(nxpMoldMaintenanceGroupService.updateNxpMoldMaintenanceGroup(nxpMoldMaintenanceGroup));
    }

    /**
     * 删除模具维护分组
     */
    @RequiresPermissions("system:nxpMoldMaintenanceGroup:remove")
    @Log(title = "模具维护分组", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        String[] strings = Convert.toStrArray(ids);
        for (String id : strings) {
            NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupById(Long.valueOf(id));
            if (nxpMoldMaintenanceGroup.getNxpMoldMaintenanceGroupItemList() != null && nxpMoldMaintenanceGroup.getNxpMoldMaintenanceGroupItemList().size() > 0){
                return AjaxResult.error("该分组下有检查项，请先删除检查项");
            }
        }
        return toAjax(nxpMoldMaintenanceGroupService.deleteNxpMoldMaintenanceGroupByIds(ids));
    }
}
