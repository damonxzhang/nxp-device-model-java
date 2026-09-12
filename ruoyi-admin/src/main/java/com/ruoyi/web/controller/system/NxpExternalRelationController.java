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
import com.ruoyi.system.domain.NxpExternalRelation;
import com.ruoyi.system.service.INxpExternalRelationService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外部权限Controller
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
@Controller
@RequestMapping("/system/external_relation")
public class NxpExternalRelationController extends BaseController
{
    private String prefix = "system/external_relation";

    @Autowired
    private INxpExternalRelationService nxpExternalRelationService;

    @RequiresPermissions("system:external_relation:view")
    @GetMapping()
    public String external_relation()
    {
        return prefix + "/external_relation";
    }

    /**
     * 查询外部权限列表
     */
    @RequiresPermissions("system:external_relation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpExternalRelation nxpExternalRelation)
    {
        startPage();
        List<NxpExternalRelation> list = nxpExternalRelationService.selectNxpExternalRelationList(nxpExternalRelation);
        return getDataTable(list);
    }

    /**
     * 导出外部权限列表
     */
    @RequiresPermissions("system:external_relation:export")
    @Log(title = "外部权限", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpExternalRelation nxpExternalRelation)
    {
        List<NxpExternalRelation> list = nxpExternalRelationService.selectNxpExternalRelationList(nxpExternalRelation);
        ExcelUtil<NxpExternalRelation> util = new ExcelUtil<NxpExternalRelation>(NxpExternalRelation.class);
        return util.exportExcel(list, "external_relation");
    }

    /**
     * 新增外部权限
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存外部权限
     */
    @RequiresPermissions("system:external_relation:add")
    @Log(title = "外部权限", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpExternalRelation nxpExternalRelation)
    {
        return toAjax(nxpExternalRelationService.insertNxpExternalRelation(nxpExternalRelation));
    }

    /**
     * 修改外部权限
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpExternalRelation nxpExternalRelation = nxpExternalRelationService.selectNxpExternalRelationById(id);
        mmap.put("nxpExternalRelation", nxpExternalRelation);
        return prefix + "/edit";
    }

    /**
     * 修改保存外部权限
     */
    @RequiresPermissions("system:external_relation:edit")
    @Log(title = "外部权限", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpExternalRelation nxpExternalRelation)
    {
        return toAjax(nxpExternalRelationService.updateNxpExternalRelation(nxpExternalRelation));
    }

    /**
     * 删除外部权限
     */
    @RequiresPermissions("system:external_relation:remove")
    @Log(title = "外部权限", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpExternalRelationService.deleteNxpExternalRelationByIds(ids));
    }
}
