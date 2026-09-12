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
import com.ruoyi.system.domain.NxpInspectionServiceDetail;
import com.ruoyi.system.service.INxpInspectionServiceDetailService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维修详情备件Controller
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Controller
@RequestMapping("/system/inspectionServiceDetail")
public class NxpInspectionServiceDetailController extends BaseController
{
    private String prefix = "system/inspectionServiceDetail";

    @Autowired
    private INxpInspectionServiceDetailService nxpInspectionServiceDetailService;

    @RequiresPermissions("system:inspectionServiceDetail:view")
    @GetMapping()
    public String inspectionServiceDetail()
    {
        return prefix + "/inspectionServiceDetail";
    }

    /**
     * 查询维修详情备件列表
     */
    @RequiresPermissions("system:inspectionServiceDetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionServiceDetail nxpInspectionServiceDetail)
    {
        startPage();
        List<NxpInspectionServiceDetail> list = nxpInspectionServiceDetailService.selectNxpInspectionServiceDetailList(nxpInspectionServiceDetail);
        return getDataTable(list);
    }

    /**
     * 导出维修详情备件列表
     */
    @RequiresPermissions("system:inspectionServiceDetail:export")
    @Log(title = "维修详情备件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionServiceDetail nxpInspectionServiceDetail)
    {
        List<NxpInspectionServiceDetail> list = nxpInspectionServiceDetailService.selectNxpInspectionServiceDetailList(nxpInspectionServiceDetail);
        ExcelUtil<NxpInspectionServiceDetail> util = new ExcelUtil<NxpInspectionServiceDetail>(NxpInspectionServiceDetail.class);
        return util.exportExcel(list, "inspectionServiceDetail");
    }

    /**
     * 新增维修详情备件
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存维修详情备件
     */
    @RequiresPermissions("system:inspectionServiceDetail:add")
    @Log(title = "维修详情备件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionServiceDetail nxpInspectionServiceDetail)
    {
        return toAjax(nxpInspectionServiceDetailService.insertNxpInspectionServiceDetail(nxpInspectionServiceDetail));
    }

    /**
     * 修改维修详情备件
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionServiceDetail nxpInspectionServiceDetail = nxpInspectionServiceDetailService.selectNxpInspectionServiceDetailById(id);
        mmap.put("nxpInspectionServiceDetail", nxpInspectionServiceDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存维修详情备件
     */
    @RequiresPermissions("system:inspectionServiceDetail:edit")
    @Log(title = "维修详情备件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionServiceDetail nxpInspectionServiceDetail)
    {
        return toAjax(nxpInspectionServiceDetailService.updateNxpInspectionServiceDetail(nxpInspectionServiceDetail));
    }

    /**
     * 删除维修详情备件
     */
    @RequiresPermissions("system:inspectionServiceDetail:remove")
    @Log(title = "维修详情备件", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionServiceDetailService.deleteNxpInspectionServiceDetailByIds(ids));
    }
}
