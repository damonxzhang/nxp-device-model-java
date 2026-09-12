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
import com.ruoyi.system.domain.NxpInspectionAboutReason;
import com.ruoyi.system.service.INxpInspectionAboutReasonService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 中止维修原因Controller
 * 
 * @author YanCheng Liu
 * @date 2021-03-01
 */
@Controller
@RequestMapping("/system/reason")
public class NxpInspectionAboutReasonController extends BaseController
{
    private String prefix = "system/reason";

    @Autowired
    private INxpInspectionAboutReasonService nxpInspectionAboutReasonService;

    @RequiresPermissions("system:reason:view")
    @GetMapping()
    public String reason()
    {
        return prefix + "/reason";
    }

    /**
     * 查询中止维修原因列表
     */
    @RequiresPermissions("system:reason:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionAboutReason nxpInspectionAboutReason)
    {
        startPage();
        List<NxpInspectionAboutReason> list = nxpInspectionAboutReasonService.selectNxpInspectionAboutReasonList(nxpInspectionAboutReason);
        return getDataTable(list);
    }

    /**
     * 导出中止维修原因列表
     */
    @RequiresPermissions("system:reason:export")
    @Log(title = "中止维修原因", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionAboutReason nxpInspectionAboutReason)
    {
        List<NxpInspectionAboutReason> list = nxpInspectionAboutReasonService.selectNxpInspectionAboutReasonList(nxpInspectionAboutReason);
        ExcelUtil<NxpInspectionAboutReason> util = new ExcelUtil<NxpInspectionAboutReason>(NxpInspectionAboutReason.class);
        return util.exportExcel(list, "reason");
    }

    /**
     * 新增中止维修原因
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存中止维修原因
     */
    @RequiresPermissions("system:reason:add")
    @Log(title = "中止维修原因", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionAboutReason nxpInspectionAboutReason)
    {
        return toAjax(nxpInspectionAboutReasonService.insertNxpInspectionAboutReason(nxpInspectionAboutReason));
    }

    /**
     * 修改中止维修原因
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionAboutReason nxpInspectionAboutReason = nxpInspectionAboutReasonService.selectNxpInspectionAboutReasonById(id);
        mmap.put("nxpInspectionAboutReason", nxpInspectionAboutReason);
        return prefix + "/edit";
    }

    /**
     * 修改保存中止维修原因
     */
    @RequiresPermissions("system:reason:edit")
    @Log(title = "中止维修原因", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionAboutReason nxpInspectionAboutReason)
    {
        return toAjax(nxpInspectionAboutReasonService.updateNxpInspectionAboutReason(nxpInspectionAboutReason));
    }

    /**
     * 删除中止维修原因
     */
    @RequiresPermissions("system:reason:remove")
    @Log(title = "中止维修原因", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionAboutReasonService.deleteNxpInspectionAboutReasonByIds(ids));
    }
}
