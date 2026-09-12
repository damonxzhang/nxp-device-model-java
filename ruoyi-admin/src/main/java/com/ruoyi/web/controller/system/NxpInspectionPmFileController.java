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
import com.ruoyi.system.domain.NxpInspectionPmFile;
import com.ruoyi.system.service.INxpInspectionPmFileService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * pm图片Controller
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/pmFile")
public class NxpInspectionPmFileController extends BaseController
{
    private String prefix = "system/pmFile";

    @Autowired
    private INxpInspectionPmFileService nxpInspectionPmFileService;

    @RequiresPermissions("system:pmFile:view")
    @GetMapping()
    public String pmFile()
    {
        return prefix + "/pmFile";
    }

    /**
     * 查询pm图片列表
     */
    @RequiresPermissions("system:pmFile:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPmFile nxpInspectionPmFile)
    {
        startPage();
        List<NxpInspectionPmFile> list = nxpInspectionPmFileService.selectNxpInspectionPmFileList(nxpInspectionPmFile);
        return getDataTable(list);
    }

    /**
     * 导出pm图片列表
     */
    @RequiresPermissions("system:pmFile:export")
    @Log(title = "pm图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPmFile nxpInspectionPmFile)
    {
        List<NxpInspectionPmFile> list = nxpInspectionPmFileService.selectNxpInspectionPmFileList(nxpInspectionPmFile);
        ExcelUtil<NxpInspectionPmFile> util = new ExcelUtil<NxpInspectionPmFile>(NxpInspectionPmFile.class);
        return util.exportExcel(list, "pmFile");
    }

    /**
     * 新增pm图片
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存pm图片
     */
    @RequiresPermissions("system:pmFile:add")
    @Log(title = "pm图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPmFile nxpInspectionPmFile)
    {
        return toAjax(nxpInspectionPmFileService.insertNxpInspectionPmFile(nxpInspectionPmFile));
    }

    /**
     * 修改pm图片
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPmFile nxpInspectionPmFile = nxpInspectionPmFileService.selectNxpInspectionPmFileById(id);
        mmap.put("nxpInspectionPmFile", nxpInspectionPmFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存pm图片
     */
    @RequiresPermissions("system:pmFile:edit")
    @Log(title = "pm图片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPmFile nxpInspectionPmFile)
    {
        return toAjax(nxpInspectionPmFileService.updateNxpInspectionPmFile(nxpInspectionPmFile));
    }

    /**
     * 删除pm图片
     */
    @RequiresPermissions("system:pmFile:remove")
    @Log(title = "pm图片", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPmFileService.deleteNxpInspectionPmFileByIds(ids));
    }
}
