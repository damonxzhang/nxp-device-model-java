package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldType;
import com.ruoyi.system.service.INxpMoldService;
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
import com.ruoyi.system.domain.NxpMoldComponent;
import com.ruoyi.system.service.INxpMoldComponentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模具部件管理Controller
 * 
 * @author YanCheng Liu
 * @date 2023-06-28
 */
@Controller
@RequestMapping("/system/nxpMoldComponent")
public class NxpMoldComponentController extends BaseController
{
    private String prefix = "system/nxpMoldComponent";

    @Autowired
    private INxpMoldComponentService nxpMoldComponentService;
    @Autowired
    private INxpMoldService nxpMoldService;
    @Autowired
    private INxpMoldTypeService nxpMoldTypeService;

    @RequiresPermissions("system:nxpMoldComponent:view")
    @GetMapping()
    public String nxpMoldComponent(ModelMap mmap)
    {
        NxpMoldType nxpMoldType = new NxpMoldType();
        nxpMoldType.setStatus(1);
        nxpMoldType.setMoldType(1);
        List<NxpMoldType> nxpMoldTypes = nxpMoldTypeService.selectNxpMoldTypeList(nxpMoldType);
        mmap.put("nxpMoldTypes",nxpMoldTypes);
        return prefix + "/nxpMoldComponent";
    }

    /**
     * 查询模具部件管理列表
     */
    @RequiresPermissions("system:nxpMoldComponent:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldComponent nxpMoldComponent)
    {
        startPage();
        nxpMoldComponent.setMoldType(1);
        List<NxpMoldComponent> list = nxpMoldComponentService.selectNxpMoldComponentList(nxpMoldComponent);
        return getDataTable(list);
    }

    /**
     * 导出模具部件管理列表
     */
    @RequiresPermissions("system:nxpMoldComponent:export")
    @Log(title = "模具部件管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldComponent nxpMoldComponent)
    {
        List<NxpMoldComponent> list = nxpMoldComponentService.selectNxpMoldComponentList(nxpMoldComponent);
        ExcelUtil<NxpMoldComponent> util = new ExcelUtil<NxpMoldComponent>(NxpMoldComponent.class);
        return util.exportExcel(list, "nxpMoldComponent");
    }

    /**
     * 新增模具部件管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(1);
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldList(nxpMold);
        mmap.put("molds",nxpMolds);
        NxpMoldType nxpMoldType = new NxpMoldType();
        nxpMoldType.setStatus(1);
        nxpMoldType.setMoldType(1);
        List<NxpMoldType> nxpMoldTypes = nxpMoldTypeService.selectNxpMoldTypeList(nxpMoldType);
        mmap.put("nxpMoldTypes",nxpMoldTypes);
        return prefix + "/add";
    }

    /**
     * 新增保存模具部件管理
     */
    @RequiresPermissions("system:nxpMoldComponent:add")
    @Log(title = "模具部件管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldComponent nxpMoldComponent)
    {
        return nxpMoldComponentService.insertNxpMoldComponent(nxpMoldComponent);
    }

    /**
     * 修改模具部件管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldComponent nxpMoldComponent = nxpMoldComponentService.selectNxpMoldComponentById(id);
        mmap.put("nxpMoldComponent", nxpMoldComponent);
        /*List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListAdmin(new NxpMold());
        mmap.put("molds",nxpMolds);
        NxpMoldType nxpMoldType = new NxpMoldType();
        nxpMoldType.setStatus(1);
        List<NxpMoldType> nxpMoldTypes = nxpMoldTypeService.selectNxpMoldTypeListAdmin(nxpMoldType);
        mmap.put("nxpMoldTypes",nxpMoldTypes);*/
        return prefix + "/edit";
    }

    /**
     * 修改保存模具部件管理
     */
    @RequiresPermissions("system:nxpMoldComponent:edit")
    @Log(title = "模具部件管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldComponent nxpMoldComponent)
    {
        return toAjax(nxpMoldComponentService.updateNxpMoldComponent(nxpMoldComponent));
    }

    /**
     * 删除模具部件管理
     */
    @RequiresPermissions("system:nxpMoldComponent:remove")
    @Log(title = "模具部件管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldComponentService.deleteNxpMoldComponentByIds(ids));
    }
}
