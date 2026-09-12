package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.domain.NxpSwcProgramGroup;
import com.ruoyi.system.service.INxpSwcProgramGroupService;
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
import com.ruoyi.system.domain.NxpSwcProgramType;
import com.ruoyi.system.service.INxpSwcProgramTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查问题类型Controller
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/system/problem_type")
public class NxpSwcProgramTypeController extends BaseController
{
    private String prefix = "system/problem_type";

    @Autowired
    private INxpSwcProgramTypeService nxpSwcProgramTypeService;
    @Autowired
    private INxpSwcProgramGroupService nxpSwcProgramGroupService;

    @RequiresPermissions("system:problem_type:view")
    @GetMapping()
    public String problem_type(ModelMap mmap)
    {
        NxpSwcProgramGroup nxpSwcProgramGroup = new NxpSwcProgramGroup();
        nxpSwcProgramGroup.setStatus(1L);
        List<NxpSwcProgramGroup> nxpSwcProgramGroups = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(nxpSwcProgramGroup);
        mmap.put("groups",nxpSwcProgramGroups);
        return prefix + "/problem_type";
    }

    /**
     * 查询检查问题类型列表
     */
    @RequiresPermissions("system:problem_type:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpSwcProgramType nxpSwcProgramType)
    {
        startPage();
        List<NxpSwcProgramType> list = nxpSwcProgramTypeService.selectNxpSwcProgramTypeList(nxpSwcProgramType);
        return getDataTable(list);
    }

    /**
     * 导出检查问题类型列表
     */
    @RequiresPermissions("system:problem_type:export")
    @Log(title = "检查问题类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpSwcProgramType nxpSwcProgramType)
    {
        List<NxpSwcProgramType> list = nxpSwcProgramTypeService.selectNxpSwcProgramTypeList(nxpSwcProgramType);
        ExcelUtil<NxpSwcProgramType> util = new ExcelUtil<NxpSwcProgramType>(NxpSwcProgramType.class);
        return util.exportExcel(list, "problem_type");
    }

    /**
     * 新增检查问题类型
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpSwcProgramGroup nxpSwcProgramGroup1 = new NxpSwcProgramGroup();
        nxpSwcProgramGroup1.setStatus(1L);
        List<NxpSwcProgramGroup> nxpSwcProgramGroup = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(nxpSwcProgramGroup1);
        mmap.put("groups",nxpSwcProgramGroup);
        return prefix + "/add";
    }

    /**
     * 新增保存检查问题类型
     */
    @RequiresPermissions("system:problem_type:add")
    @Log(title = "检查问题类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpSwcProgramType nxpSwcProgramType)
    {
        return toAjax(nxpSwcProgramTypeService.insertNxpSwcProgramType(nxpSwcProgramType));
    }

    /**
     * 修改检查问题类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpSwcProgramType nxpSwcProgramType = nxpSwcProgramTypeService.selectNxpSwcProgramTypeById(id);
        List<NxpSwcProgramGroup> nxpSwcProgramGroup = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(new NxpSwcProgramGroup());
        mmap.put("nxpSwcProgramType", nxpSwcProgramType);
        mmap.put("groups",nxpSwcProgramGroup);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查问题类型
     */
    @RequiresPermissions("system:problem_type:edit")
    @Log(title = "检查问题类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpSwcProgramType nxpSwcProgramType)
    {
        return toAjax(nxpSwcProgramTypeService.updateNxpSwcProgramType(nxpSwcProgramType));
    }

    /**
     * 删除检查问题类型
     */
    @RequiresPermissions("system:problem_type:remove")
    @Log(title = "检查问题类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpSwcProgramTypeService.deleteNxpSwcProgramTypeByIds(ids));
    }
}
