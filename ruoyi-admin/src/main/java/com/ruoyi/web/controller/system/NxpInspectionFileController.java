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
import com.ruoyi.system.domain.NxpInspectionFile;
import com.ruoyi.system.service.INxpInspectionFileService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维修上传图片Controller
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Controller
@RequestMapping("/system/inspectionFile")
public class NxpInspectionFileController extends BaseController
{
    private String prefix = "system/inspectionFile";

    @Autowired
    private INxpInspectionFileService nxpInspectionFileService;

    @RequiresPermissions("system:inspectionFile:view")
    @GetMapping()
    public String inspectionFile()
    {
        return prefix + "/inspectionFile";
    }

    /**
     * 查询维修上传图片列表
     */
    @RequiresPermissions("system:inspectionFile:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionFile nxpInspectionFile)
    {
        startPage();
        List<NxpInspectionFile> list = nxpInspectionFileService.selectNxpInspectionFileList(nxpInspectionFile);
        return getDataTable(list);
    }

    /**
     * 导出维修上传图片列表
     */
    @RequiresPermissions("system:inspectionFile:export")
    @Log(title = "维修上传图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionFile nxpInspectionFile)
    {
        List<NxpInspectionFile> list = nxpInspectionFileService.selectNxpInspectionFileList(nxpInspectionFile);
        ExcelUtil<NxpInspectionFile> util = new ExcelUtil<NxpInspectionFile>(NxpInspectionFile.class);
        return util.exportExcel(list, "inspectionFile");
    }

    /**
     * 新增维修上传图片
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存维修上传图片
     */
    @RequiresPermissions("system:inspectionFile:add")
    @Log(title = "维修上传图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionFile nxpInspectionFile)
    {
        return toAjax(nxpInspectionFileService.insertNxpInspectionFile(nxpInspectionFile));
    }

    /**
     * 修改维修上传图片
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionFile nxpInspectionFile = nxpInspectionFileService.selectNxpInspectionFileById(id);
        mmap.put("nxpInspectionFile", nxpInspectionFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存维修上传图片
     */
    @RequiresPermissions("system:inspectionFile:edit")
    @Log(title = "维修上传图片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionFile nxpInspectionFile)
    {
        return toAjax(nxpInspectionFileService.updateNxpInspectionFile(nxpInspectionFile));
    }

    /**
     * 删除维修上传图片
     */
    @RequiresPermissions("system:inspectionFile:remove")
    @Log(title = "维修上传图片", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionFileService.deleteNxpInspectionFileByIds(ids));
    }
}
