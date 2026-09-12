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
import com.ruoyi.system.domain.NxpMoldComponentAbnormalLogs;
import com.ruoyi.system.service.INxpMoldComponentAbnormalLogsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模具异常信息Controller
 * 
 * @author YanCheng Liu
 * @date 2023-07-12
 */
@Controller
@RequestMapping("/system/nxpMoldComponentAbnormalLogs")
public class NxpMoldComponentAbnormalLogsController extends BaseController
{
    private String prefix = "system/nxpMoldComponentAbnormalLogs";

    @Autowired
    private INxpMoldComponentAbnormalLogsService nxpMoldComponentAbnormalLogsService;

    @RequiresPermissions("system:nxpMoldComponentAbnormalLogs:view")
    @GetMapping()
    public String nxpMoldComponentAbnormalLogs()
    {
        return prefix + "/nxpMoldComponentAbnormalLogs";
    }

    /**
     * 查询模具异常信息列表
     */
    @RequiresPermissions("system:nxpMoldComponentAbnormalLogs:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs)
    {
        startPage();
        nxpMoldComponentAbnormalLogs.setMoldType(1);
        List<NxpMoldComponentAbnormalLogs> list = nxpMoldComponentAbnormalLogsService.selectNxpMoldComponentAbnormalLogsList(nxpMoldComponentAbnormalLogs);
        return getDataTable(list);
    }

    /**
     * 导出模具异常信息列表
     */
    @RequiresPermissions("system:nxpMoldComponentAbnormalLogs:export")
    @Log(title = "模具异常信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs)
    {
        List<NxpMoldComponentAbnormalLogs> list = nxpMoldComponentAbnormalLogsService.selectNxpMoldComponentAbnormalLogsList(nxpMoldComponentAbnormalLogs);
        ExcelUtil<NxpMoldComponentAbnormalLogs> util = new ExcelUtil<NxpMoldComponentAbnormalLogs>(NxpMoldComponentAbnormalLogs.class);
        return util.exportExcel(list, "nxpMoldComponentAbnormalLogs");
    }

    /**
     * 新增模具异常信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存模具异常信息
     */
    @RequiresPermissions("system:nxpMoldComponentAbnormalLogs:add")
    @Log(title = "模具异常信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs)
    {
        return toAjax(nxpMoldComponentAbnormalLogsService.insertNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs));
    }

    /**
     * 修改模具异常信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs = nxpMoldComponentAbnormalLogsService.selectNxpMoldComponentAbnormalLogsById(id);
        mmap.put("nxpMoldComponentAbnormalLogs", nxpMoldComponentAbnormalLogs);
        return prefix + "/edit";
    }

    /**
     * 修改保存模具异常信息
     */
    @RequiresPermissions("system:nxpMoldComponentAbnormalLogs:edit")
    @Log(title = "模具异常信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs)
    {
        return toAjax(nxpMoldComponentAbnormalLogsService.updateNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs));
    }

    /**
     * 删除模具异常信息
     */
    @RequiresPermissions("system:nxpMoldComponentAbnormalLogs:remove")
    @Log(title = "模具异常信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMoldComponentAbnormalLogsService.deleteNxpMoldComponentAbnormalLogsByIds(ids));
    }
}
