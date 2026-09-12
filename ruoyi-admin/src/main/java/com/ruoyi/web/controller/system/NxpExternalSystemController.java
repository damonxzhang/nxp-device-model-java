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
import com.ruoyi.system.domain.NxpExternalSystem;
import com.ruoyi.system.service.INxpExternalSystemService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外部系统Controller
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
@Controller
@RequestMapping("/system/external_system")
public class NxpExternalSystemController extends BaseController
{
    private String prefix = "system/external_system";

    @Autowired
    private INxpExternalSystemService nxpExternalSystemService;

    @RequiresPermissions("system:external_system:view")
    @GetMapping()
    public String external_system()
    {
        return prefix + "/external_system";
    }

    /**
     * 查询外部系统列表
     */
    @RequiresPermissions("system:external_system:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpExternalSystem nxpExternalSystem)
    {
        startPage();
        List<NxpExternalSystem> list = nxpExternalSystemService.selectNxpExternalSystemList(nxpExternalSystem);
        return getDataTable(list);
    }

    /**
     * 导出外部系统列表
     */
    @RequiresPermissions("system:external_system:export")
    @Log(title = "外部系统", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpExternalSystem nxpExternalSystem)
    {
        List<NxpExternalSystem> list = nxpExternalSystemService.selectNxpExternalSystemList(nxpExternalSystem);
        ExcelUtil<NxpExternalSystem> util = new ExcelUtil<NxpExternalSystem>(NxpExternalSystem.class);
        return util.exportExcel(list, "external_system");
    }

    /**
     * 新增外部系统
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存外部系统
     */
    @RequiresPermissions("system:external_system:add")
    @Log(title = "外部系统", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpExternalSystem nxpExternalSystem)
    {
        return toAjax(nxpExternalSystemService.insertNxpExternalSystem(nxpExternalSystem));
    }

    /**
     * 修改外部系统
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpExternalSystem nxpExternalSystem = nxpExternalSystemService.selectNxpExternalSystemById(id);
        mmap.put("nxpExternalSystem", nxpExternalSystem);
        return prefix + "/edit";
    }

    /**
     * 修改保存外部系统
     */
    @RequiresPermissions("system:external_system:edit")
    @Log(title = "外部系统", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpExternalSystem nxpExternalSystem)
    {
        return toAjax(nxpExternalSystemService.updateNxpExternalSystem(nxpExternalSystem));
    }

    /**
     * 删除外部系统
     */
    @RequiresPermissions("system:external_system:remove")
    @Log(title = "外部系统", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpExternalSystemService.deleteNxpExternalSystemByIds(ids));
    }
}
