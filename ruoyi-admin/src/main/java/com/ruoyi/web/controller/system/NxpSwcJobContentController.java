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
import com.ruoyi.system.domain.NxpSwcJobContent;
import com.ruoyi.system.service.INxpSwcJobContentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作业内容管理Controller
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
@Controller
@RequestMapping("/system/nxpSwcJobContent")
public class NxpSwcJobContentController extends BaseController
{
    private String prefix = "system/nxpSwcJobContent";

    @Autowired
    private INxpSwcJobContentService nxpSwcJobContentService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpCheckGroupService nxpCheckGroupService;

    @RequiresPermissions("system:nxpSwcJobContent:view")
    @GetMapping()
    public String nxpSwcJobContent()
    {
        return prefix + "/nxpSwcJobContent";
    }

    /**
     * 查询作业内容管理列表
     */
    @RequiresPermissions("system:nxpSwcJobContent:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpSwcJobContent nxpSwcJobContent)
    {
        startPage();
        List<NxpSwcJobContent> list = nxpSwcJobContentService.selectNxpSwcJobContentList(nxpSwcJobContent);
        for (NxpSwcJobContent swcJobContent : list) {
            if (swcJobContent.getCheckGroupId() != null){
                Long checkGroupId = swcJobContent.getCheckGroupId();
                NxpCheckGroup nxpCheckGroup = nxpCheckGroupService.selectNxpCheckGroupById(checkGroupId);
                if (nxpCheckGroup != null){
                    swcJobContent.setCheckGroupName(nxpCheckGroup.getGroupName());
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出作业内容管理列表
     */
    @RequiresPermissions("system:nxpSwcJobContent:export")
    @Log(title = "作业内容管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpSwcJobContent nxpSwcJobContent)
    {
        List<NxpSwcJobContent> list = nxpSwcJobContentService.selectNxpSwcJobContentList(nxpSwcJobContent);
        ExcelUtil<NxpSwcJobContent> util = new ExcelUtil<NxpSwcJobContent>(NxpSwcJobContent.class);
        return util.exportExcel(list, "nxpSwcJobContent");
    }

    /**
     * 新增作业内容管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存作业内容管理
     */
    @RequiresPermissions("system:nxpSwcJobContent:add")
    @Log(title = "作业内容管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpSwcJobContent nxpSwcJobContent)
    {
        return toAjax(nxpSwcJobContentService.insertNxpSwcJobContent(nxpSwcJobContent));
    }

    /**
     * 修改作业内容管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpSwcJobContent nxpSwcJobContent = nxpSwcJobContentService.selectNxpSwcJobContentById(id);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        if (nxpSwcJobContent.getCheckGroupId() != null){
            Long checkProcessId = nxpSwcJobContent.getCheckProcessId();
            NxpCheckGroup nxpCheckGroup = new NxpCheckGroup();
            nxpCheckGroup.setProcessId(checkProcessId);
            List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(nxpCheckGroup);
            mmap.put("checkGroups",nxpCheckGroups);
        }
        mmap.put("processes",nxpMachineProcesses);
        mmap.put("nxpSwcJobContent", nxpSwcJobContent);
        return prefix + "/edit";
    }

    /**
     * 修改保存作业内容管理
     */
    @RequiresPermissions("system:nxpSwcJobContent:edit")
    @Log(title = "作业内容管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpSwcJobContent nxpSwcJobContent)
    {
        return toAjax(nxpSwcJobContentService.updateNxpSwcJobContent(nxpSwcJobContent));
    }

    /**
     * 删除作业内容管理
     */
    @RequiresPermissions("system:nxpSwcJobContent:remove")
    @Log(title = "作业内容管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpSwcJobContentService.deleteNxpSwcJobContentByIds(ids));
    }
}
