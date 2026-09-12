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
import com.ruoyi.system.domain.NxpCheckGroup;
import com.ruoyi.system.service.INxpCheckGroupService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查组管理Controller
 * 
 * @author YanCheng Liu
 * @date 2022-10-24
 */
@Controller
@RequestMapping("/system/checkGroup")
public class NxpCheckGroupController extends BaseController
{
    private String prefix = "system/checkGroup";

    @Autowired
    private INxpCheckGroupService nxpCheckGroupService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;

    @RequiresPermissions("system:checkGroup:view")
    @GetMapping()
    public String checkGroup(ModelMap mmap)
    {
        // 工序
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/checkGroup";
    }

    /**
     * 查询检查组管理列表
     */
    @RequiresPermissions("system:checkGroup:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpCheckGroup nxpCheckGroup)
    {
        startPage();
        List<NxpCheckGroup> list = nxpCheckGroupService.selectNxpCheckGroupList(nxpCheckGroup);
        for (NxpCheckGroup checkGroup : list) {
            Long processId = checkGroup.getProcessId();
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(processId);
            checkGroup.setProcessName(nxpMachineProcess.getName());
        }
        return getDataTable(list);
    }

    /**
     * 导出检查组管理列表
     */
    @RequiresPermissions("system:checkGroup:export")
    @Log(title = "检查组管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpCheckGroup nxpCheckGroup)
    {
        List<NxpCheckGroup> list = nxpCheckGroupService.selectNxpCheckGroupList(nxpCheckGroup);
        ExcelUtil<NxpCheckGroup> util = new ExcelUtil<NxpCheckGroup>(NxpCheckGroup.class);
        return util.exportExcel(list, "checkGroup");
    }

    /**
     * 新增检查组管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        // 工序
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存检查组管理
     */
    @RequiresPermissions("system:checkGroup:add")
    @Log(title = "检查组管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpCheckGroup nxpCheckGroup)
    {
        return toAjax(nxpCheckGroupService.insertNxpCheckGroup(nxpCheckGroup));
    }

    /**
     * 修改检查组管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpCheckGroup nxpCheckGroup = nxpCheckGroupService.selectNxpCheckGroupById(id);
        // 工序
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);
        mmap.put("nxpCheckGroup", nxpCheckGroup);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查组管理
     */
    @RequiresPermissions("system:checkGroup:edit")
    @Log(title = "检查组管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpCheckGroup nxpCheckGroup)
    {
        return toAjax(nxpCheckGroupService.updateNxpCheckGroup(nxpCheckGroup));
    }

    /**
     * 删除检查组管理
     */
    @RequiresPermissions("system:checkGroup:remove")
    @Log(title = "检查组管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpCheckGroupService.deleteNxpCheckGroupByIds(ids));
    }

    /**
     * 根据工序id查询检查组
     * @param processId
     * @return
     */
    @GetMapping("/getByProcessId/{processId}")
    @ResponseBody
    public AjaxResult getByProcessId(@PathVariable("processId") Long processId){
        return nxpCheckGroupService.getByProcessId(processId);
    }
}
