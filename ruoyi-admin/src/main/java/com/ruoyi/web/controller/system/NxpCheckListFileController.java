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
import com.ruoyi.system.domain.NxpCheckListFile;
import com.ruoyi.system.service.INxpCheckListFileService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查列表文件Controller
 * 
 * @author YanCheng Liu
 * @date 2022-09-04
 */
@Controller
@RequestMapping("/system/checkListFile")
public class NxpCheckListFileController extends BaseController
{
    private String prefix = "system/checkListFile";

    @Autowired
    private INxpCheckListFileService nxpCheckListFileService;

    @RequiresPermissions("system:checkListFile:view")
    @GetMapping()
    public String checkListFile()
    {
        return prefix + "/checkListFile";
    }

    /**
     * 查询检查列表文件列表
     */
    @RequiresPermissions("system:checkListFile:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpCheckListFile nxpCheckListFile)
    {
        startPage();
        List<NxpCheckListFile> list = nxpCheckListFileService.selectNxpCheckListFileList(nxpCheckListFile);
        return getDataTable(list);
    }

    /**
     * 导出检查列表文件列表
     */
    @RequiresPermissions("system:checkListFile:export")
    @Log(title = "检查列表文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpCheckListFile nxpCheckListFile)
    {
        List<NxpCheckListFile> list = nxpCheckListFileService.selectNxpCheckListFileList(nxpCheckListFile);
        ExcelUtil<NxpCheckListFile> util = new ExcelUtil<NxpCheckListFile>(NxpCheckListFile.class);
        return util.exportExcel(list, "checkListFile");
    }

    /**
     * 新增检查列表文件
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存检查列表文件
     */
    @RequiresPermissions("system:checkListFile:add")
    @Log(title = "检查列表文件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpCheckListFile nxpCheckListFile)
    {
        return toAjax(nxpCheckListFileService.insertNxpCheckListFile(nxpCheckListFile));
    }

    /**
     * 修改检查列表文件
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpCheckListFile nxpCheckListFile = nxpCheckListFileService.selectNxpCheckListFileById(id);
        mmap.put("nxpCheckListFile", nxpCheckListFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查列表文件
     */
    @RequiresPermissions("system:checkListFile:edit")
    @Log(title = "检查列表文件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpCheckListFile nxpCheckListFile)
    {
        return toAjax(nxpCheckListFileService.updateNxpCheckListFile(nxpCheckListFile));
    }

    /**
     * 删除检查列表文件
     */
    @RequiresPermissions("system:checkListFile:remove")
    @Log(title = "检查列表文件", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpCheckListFileService.deleteNxpCheckListFileByIds(ids));
    }
}
