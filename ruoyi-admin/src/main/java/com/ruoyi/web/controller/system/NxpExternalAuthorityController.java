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
import com.ruoyi.system.domain.NxpExternalAuthority;
import com.ruoyi.system.service.INxpExternalAuthorityService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外部关联Controller
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
@Controller
@RequestMapping("/system/external_authority")
public class NxpExternalAuthorityController extends BaseController
{
    private String prefix = "system/external_authority";

    @Autowired
    private INxpExternalAuthorityService nxpExternalAuthorityService;

    @RequiresPermissions("system:external_authority:view")
    @GetMapping()
    public String external_authority()
    {
        return prefix + "/external_authority";
    }

    /**
     * 查询外部关联列表
     */
    @RequiresPermissions("system:external_authority:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpExternalAuthority nxpExternalAuthority)
    {
        startPage();
        List<NxpExternalAuthority> list = nxpExternalAuthorityService.selectNxpExternalAuthorityList(nxpExternalAuthority);
        return getDataTable(list);
    }

    /**
     * 导出外部关联列表
     */
    @RequiresPermissions("system:external_authority:export")
    @Log(title = "外部关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpExternalAuthority nxpExternalAuthority)
    {
        List<NxpExternalAuthority> list = nxpExternalAuthorityService.selectNxpExternalAuthorityList(nxpExternalAuthority);
        ExcelUtil<NxpExternalAuthority> util = new ExcelUtil<NxpExternalAuthority>(NxpExternalAuthority.class);
        return util.exportExcel(list, "external_authority");
    }

    /**
     * 新增外部关联
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存外部关联
     */
    @RequiresPermissions("system:external_authority:add")
    @Log(title = "外部关联", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpExternalAuthority nxpExternalAuthority)
    {
        return toAjax(nxpExternalAuthorityService.insertNxpExternalAuthority(nxpExternalAuthority));
    }

    /**
     * 修改外部关联
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpExternalAuthority nxpExternalAuthority = nxpExternalAuthorityService.selectNxpExternalAuthorityById(id);
        mmap.put("nxpExternalAuthority", nxpExternalAuthority);
        return prefix + "/edit";
    }

    /**
     * 修改保存外部关联
     */
    @RequiresPermissions("system:external_authority:edit")
    @Log(title = "外部关联", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpExternalAuthority nxpExternalAuthority)
    {
        return toAjax(nxpExternalAuthorityService.updateNxpExternalAuthority(nxpExternalAuthority));
    }

    /**
     * 删除外部关联
     */
    @RequiresPermissions("system:external_authority:remove")
    @Log(title = "外部关联", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpExternalAuthorityService.deleteNxpExternalAuthorityByIds(ids));
    }
}
