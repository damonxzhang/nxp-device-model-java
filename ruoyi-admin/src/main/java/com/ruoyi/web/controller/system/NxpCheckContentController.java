package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpCheckGroup;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.service.INxpCheckGroupService;
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
import com.ruoyi.system.domain.NxpCheckContent;
import com.ruoyi.system.service.INxpCheckContentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查内容Controller
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
@Controller
@RequestMapping("/system/checkContent")
public class NxpCheckContentController extends BaseController
{
    private String prefix = "system/checkContent";

    @Autowired
    private INxpCheckContentService nxpCheckContentService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpCheckGroupService nxpCheckGroupService;

    @RequiresPermissions("system:checkContent:view")
    @GetMapping()
    public String checkContent(ModelMap mmap)
    {
        // 工序
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(new NxpCheckGroup());
        mmap.put("processes",nxpMachineProcesses);
        mmap.put("nxpCheckGroups",nxpCheckGroups);
        return prefix + "/checkContent";
    }

    /**
     * 查询检查内容列表
     */
    @RequiresPermissions("system:checkContent:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpCheckContent nxpCheckContent)
    {
        startPage();
        List<NxpCheckContent> list = nxpCheckContentService.selectNxpCheckContentList(nxpCheckContent);
        for (NxpCheckContent checkContent : list) {
            Long processId = checkContent.getProcessId();
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(processId);
            checkContent.setProcessName(nxpMachineProcess.getName());

            Long checkGroupId = checkContent.getCheckGroupId();
            NxpCheckGroup nxpCheckGroup = nxpCheckGroupService.selectNxpCheckGroupById(checkGroupId);
            checkContent.setCheckGroupName(nxpCheckGroup.getGroupName());
        }
        return getDataTable(list);
    }

    /**
     * 导出检查内容列表
     */
    @RequiresPermissions("system:checkContent:export")
    @Log(title = "检查内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpCheckContent nxpCheckContent)
    {
        List<NxpCheckContent> list = nxpCheckContentService.selectNxpCheckContentList(nxpCheckContent);
        ExcelUtil<NxpCheckContent> util = new ExcelUtil<NxpCheckContent>(NxpCheckContent.class);
        return util.exportExcel(list, "checkContent");
    }

    /**
     * 新增检查内容
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        // 工序
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(new NxpCheckGroup());
        mmap.put("nxpCheckGroups",nxpCheckGroups);
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存检查内容
     */
    @RequiresPermissions("system:checkContent:add")
    @Log(title = "检查内容", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpCheckContent nxpCheckContent)
    {
        return toAjax(nxpCheckContentService.insertNxpCheckContent(nxpCheckContent));
    }

    /**
     * 修改检查内容
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpCheckContent nxpCheckContent = nxpCheckContentService.selectNxpCheckContentById(id);
        // 工序
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(new NxpCheckGroup());
        mmap.put("nxpCheckGroups",nxpCheckGroups);
        mmap.put("processes",nxpMachineProcesses);
        mmap.put("nxpCheckContent", nxpCheckContent);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查内容
     */
    @RequiresPermissions("system:checkContent:edit")
    @Log(title = "检查内容", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpCheckContent nxpCheckContent)
    {
        return toAjax(nxpCheckContentService.updateNxpCheckContent(nxpCheckContent));
    }

    /**
     * 删除检查内容
     */
    @RequiresPermissions("system:checkContent:remove")
    @Log(title = "检查内容", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpCheckContentService.deleteNxpCheckContentByIds(ids));
    }
}
