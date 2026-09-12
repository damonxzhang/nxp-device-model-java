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
import com.ruoyi.system.domain.NxpInspectionPmCheck;
import com.ruoyi.system.service.INxpInspectionPmCheckService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 周PM复查Controller
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/inspectionPmCheck")
public class NxpInspectionPmCheckController extends BaseController
{
    private String prefix = "system/inspectionPmCheck";

    @Autowired
    private INxpInspectionPmCheckService nxpInspectionPmCheckService;

    @RequiresPermissions("system:inspectionPmCheck:view")
    @GetMapping()
    public String inspectionPmCheck()
    {
        return prefix + "/inspectionPmCheck";
    }

    /**
     * 查询周PM复查列表
     */
    @RequiresPermissions("system:inspectionPmCheck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPmCheck nxpInspectionPmCheck)
    {
        startPage();
        List<NxpInspectionPmCheck> list = nxpInspectionPmCheckService.selectNxpInspectionPmCheckList(nxpInspectionPmCheck);
        return getDataTable(list);
    }

    /**
     * 导出周PM复查列表
     */
    @RequiresPermissions("system:inspectionPmCheck:export")
    @Log(title = "周PM复查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPmCheck nxpInspectionPmCheck)
    {
        List<NxpInspectionPmCheck> list = nxpInspectionPmCheckService.selectNxpInspectionPmCheckList(nxpInspectionPmCheck);
        ExcelUtil<NxpInspectionPmCheck> util = new ExcelUtil<NxpInspectionPmCheck>(NxpInspectionPmCheck.class);
        return util.exportExcel(list, "inspectionPmCheck");
    }

    /**
     * 新增周PM复查
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存周PM复查
     */
    @RequiresPermissions("system:inspectionPmCheck:add")
    @Log(title = "周PM复查", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPmCheck nxpInspectionPmCheck)
    {
        return toAjax(nxpInspectionPmCheckService.insertNxpInspectionPmCheck(nxpInspectionPmCheck));
    }

    /**
     * 修改周PM复查
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPmCheck nxpInspectionPmCheck = nxpInspectionPmCheckService.selectNxpInspectionPmCheckById(id);
        mmap.put("nxpInspectionPmCheck", nxpInspectionPmCheck);
        return prefix + "/edit";
    }

    /**
     * 修改保存周PM复查
     */
    @RequiresPermissions("system:inspectionPmCheck:edit")
    @Log(title = "周PM复查", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPmCheck nxpInspectionPmCheck)
    {
        return toAjax(nxpInspectionPmCheckService.updateNxpInspectionPmCheck(nxpInspectionPmCheck));
    }

    /**
     * 删除周PM复查
     */
    @RequiresPermissions("system:inspectionPmCheck:remove")
    @Log(title = "周PM复查", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPmCheckService.deleteNxpInspectionPmCheckByIds(ids));
    }
}
