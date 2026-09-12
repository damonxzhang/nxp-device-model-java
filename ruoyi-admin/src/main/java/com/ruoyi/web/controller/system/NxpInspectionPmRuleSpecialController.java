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
import com.ruoyi.system.domain.NxpInspectionPmRuleSpecial;
import com.ruoyi.system.service.INxpInspectionPmRuleSpecialService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 周PM规则加日期Controller
 * 
 * @author DamonZhang
 * @date 2021-06-28
 */
@Controller
@RequestMapping("/system/NxpInspectionPmRuleSpecial")
public class NxpInspectionPmRuleSpecialController extends BaseController
{
    private String prefix = "system/NxpInspectionPmRuleSpecial";

    @Autowired
    private INxpInspectionPmRuleSpecialService nxpInspectionPmRuleSpecialService;

    @RequiresPermissions("system:NxpInspectionPmRuleSpecial:view")
    @GetMapping()
    public String NxpInspectionPmRuleSpecial()
    {
        return prefix + "/NxpInspectionPmRuleSpecial";
    }

    /**
     * 查询周PM规则加日期列表
     */
    @RequiresPermissions("system:NxpInspectionPmRuleSpecial:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial)
    {
        startPage();
        List<NxpInspectionPmRuleSpecial> list = nxpInspectionPmRuleSpecialService.selectNxpInspectionPmRuleSpecialList(nxpInspectionPmRuleSpecial);
        return getDataTable(list);
    }

    /**
     * 导出周PM规则加日期列表
     */
    @RequiresPermissions("system:NxpInspectionPmRuleSpecial:export")
    @Log(title = "周PM规则加日期", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial)
    {
        List<NxpInspectionPmRuleSpecial> list = nxpInspectionPmRuleSpecialService.selectNxpInspectionPmRuleSpecialList(nxpInspectionPmRuleSpecial);
        ExcelUtil<NxpInspectionPmRuleSpecial> util = new ExcelUtil<NxpInspectionPmRuleSpecial>(NxpInspectionPmRuleSpecial.class);
        return util.exportExcel(list, "NxpInspectionPmRuleSpecial");
    }

    /**
     * 新增周PM规则加日期
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存周PM规则加日期
     */
    @RequiresPermissions("system:NxpInspectionPmRuleSpecial:add")
    @Log(title = "周PM规则加日期", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial)
    {
        return toAjax(nxpInspectionPmRuleSpecialService.insertNxpInspectionPmRuleSpecial(nxpInspectionPmRuleSpecial));
    }

    /**
     * 修改周PM规则加日期
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial = nxpInspectionPmRuleSpecialService.selectNxpInspectionPmRuleSpecialById(id);
        nxpInspectionPmRuleSpecial.setDateTime(nxpInspectionPmRuleSpecial.getDate().getTime());
        mmap.put("nxpInspectionPmRuleSpecial", nxpInspectionPmRuleSpecial);
        return prefix + "/edit";
    }

    /**
     * 修改保存周PM规则加日期
     */
    @RequiresPermissions("system:NxpInspectionPmRuleSpecial:edit")
    @Log(title = "周PM规则加日期", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPmRuleSpecial nxpInspectionPmRuleSpecial)
    {
        return toAjax(nxpInspectionPmRuleSpecialService.updateNxpInspectionPmRuleSpecial(nxpInspectionPmRuleSpecial));
    }

    /**
     * 删除周PM规则加日期
     */
    @RequiresPermissions("system:NxpInspectionPmRuleSpecial:remove")
    @Log(title = "周PM规则加日期", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPmRuleSpecialService.deleteNxpInspectionPmRuleSpecialByIds(ids));
    }
}
