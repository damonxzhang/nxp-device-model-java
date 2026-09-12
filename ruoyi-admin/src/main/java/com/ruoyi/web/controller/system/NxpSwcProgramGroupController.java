package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.service.INxpMachineProcessService;
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
import com.ruoyi.system.domain.NxpSwcProgramGroup;
import com.ruoyi.system.service.INxpSwcProgramGroupService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查组管理Controller
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/system/program_group")
public class NxpSwcProgramGroupController extends BaseController
{
    private String prefix = "system/program_group";

    @Autowired
    private INxpSwcProgramGroupService nxpSwcProgramGroupService;

    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;

    @RequiresPermissions("system:program_group:view")
    @GetMapping()
    public String program_group(ModelMap mmap)
    {
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/program_group";
    }

    /**
     * 查询检查组管理列表
     */
    @RequiresPermissions("system:program_group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpSwcProgramGroup nxpSwcProgramGroup)
    {
        startPage();
        List<NxpSwcProgramGroup> list = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(nxpSwcProgramGroup);
        return getDataTable(list);
    }

    /**
     * 导出检查组管理列表
     */
    @RequiresPermissions("system:program_group:export")
    @Log(title = "检查组管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpSwcProgramGroup nxpSwcProgramGroup)
    {
        List<NxpSwcProgramGroup> list = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(nxpSwcProgramGroup);
        ExcelUtil<NxpSwcProgramGroup> util = new ExcelUtil<NxpSwcProgramGroup>(NxpSwcProgramGroup.class);
        return util.exportExcel(list, "program_group");
    }

    /**
     * 新增检查组管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存检查组管理
     */
    @RequiresPermissions("system:program_group:add")
    @Log(title = "检查组管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpSwcProgramGroup nxpSwcProgramGroup)
    {
        return toAjax(nxpSwcProgramGroupService.insertNxpSwcProgramGroup(nxpSwcProgramGroup));
    }

    /**
     * 修改检查组管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpSwcProgramGroup nxpSwcProgramGroup = nxpSwcProgramGroupService.selectNxpSwcProgramGroupById(id);
        mmap.put("nxpSwcProgramGroup", nxpSwcProgramGroup);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查组管理
     */
    @RequiresPermissions("system:program_group:edit")
    @Log(title = "检查组管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpSwcProgramGroup nxpSwcProgramGroup)
    {
        return toAjax(nxpSwcProgramGroupService.updateNxpSwcProgramGroup(nxpSwcProgramGroup));
    }

    /**
     * 删除检查组管理
     */
    @RequiresPermissions("system:program_group:remove")
    @Log(title = "检查组管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpSwcProgramGroupService.deleteNxpSwcProgramGroupByIds(ids));
    }
}
