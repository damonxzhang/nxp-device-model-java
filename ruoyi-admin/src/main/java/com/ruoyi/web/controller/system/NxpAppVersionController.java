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
import com.ruoyi.system.domain.NxpAppVersion;
import com.ruoyi.system.service.INxpAppVersionService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * App版本Controller
 * 
 * @author YanCheng Liu
 * @date 2021-03-23
 */
@Controller
@RequestMapping("/system/appVersion")
public class NxpAppVersionController extends BaseController
{
    private String prefix = "system/appVersion";

    @Autowired
    private INxpAppVersionService nxpAppVersionService;

    @RequiresPermissions("system:appVersion:view")
    @GetMapping()
    public String appVersion()
    {
        return prefix + "/appVersion";
    }

    /**
     * 查询App版本列表
     */
    @RequiresPermissions("system:appVersion:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpAppVersion nxpAppVersion)
    {
        startPage();
        List<NxpAppVersion> list = nxpAppVersionService.selectNxpAppVersionList(nxpAppVersion);
        return getDataTable(list);
    }

    /**
     * 导出App版本列表
     */
    @RequiresPermissions("system:appVersion:export")
    @Log(title = "App版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpAppVersion nxpAppVersion)
    {
        List<NxpAppVersion> list = nxpAppVersionService.selectNxpAppVersionList(nxpAppVersion);
        ExcelUtil<NxpAppVersion> util = new ExcelUtil<NxpAppVersion>(NxpAppVersion.class);
        return util.exportExcel(list, "appVersion");
    }

    /**
     * 新增App版本
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存App版本
     */
    @RequiresPermissions("system:appVersion:add")
    @Log(title = "App版本", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpAppVersion nxpAppVersion)
    {
        return toAjax(nxpAppVersionService.insertNxpAppVersion(nxpAppVersion));
    }

    /**
     * 修改App版本
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpAppVersion nxpAppVersion = nxpAppVersionService.selectNxpAppVersionById(id);
        mmap.put("nxpAppVersion", nxpAppVersion);
        return prefix + "/edit";
    }

    /**
     * 修改保存App版本
     */
    @RequiresPermissions("system:appVersion:edit")
    @Log(title = "App版本", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpAppVersion nxpAppVersion)
    {
        return toAjax(nxpAppVersionService.updateNxpAppVersion(nxpAppVersion));
    }

    /**
     * 删除App版本
     */
    @RequiresPermissions("system:appVersion:remove")
    @Log(title = "App版本", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpAppVersionService.deleteNxpAppVersionByIds(ids));
    }
}
