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
import com.ruoyi.system.domain.NxpInspectionRevelantPeople;
import com.ruoyi.system.service.INxpInspectionRevelantPeopleService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备相关人员Controller
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/inspectionRevelantPeople")
public class NxpInspectionRevelantPeopleController extends BaseController
{
    private String prefix = "system/inspectionRevelantPeople";

    @Autowired
    private INxpInspectionRevelantPeopleService nxpInspectionRevelantPeopleService;

    @RequiresPermissions("system:inspectionRevelantPeople:view")
    @GetMapping()
    public String inspectionRevelantPeople()
    {
        return prefix + "/inspectionRevelantPeople";
    }

    /**
     * 查询设备相关人员列表
     */
    @RequiresPermissions("system:inspectionRevelantPeople:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionRevelantPeople nxpInspectionRevelantPeople)
    {
        startPage();
        List<NxpInspectionRevelantPeople> list = nxpInspectionRevelantPeopleService.selectNxpInspectionRevelantPeopleList(nxpInspectionRevelantPeople);
        return getDataTable(list);
    }

    /**
     * 导出设备相关人员列表
     */
    @RequiresPermissions("system:inspectionRevelantPeople:export")
    @Log(title = "设备相关人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionRevelantPeople nxpInspectionRevelantPeople)
    {
        List<NxpInspectionRevelantPeople> list = nxpInspectionRevelantPeopleService.selectNxpInspectionRevelantPeopleList(nxpInspectionRevelantPeople);
        ExcelUtil<NxpInspectionRevelantPeople> util = new ExcelUtil<NxpInspectionRevelantPeople>(NxpInspectionRevelantPeople.class);
        return util.exportExcel(list, "inspectionRevelantPeople");
    }

    /**
     * 新增设备相关人员
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备相关人员
     */
    @RequiresPermissions("system:inspectionRevelantPeople:add")
    @Log(title = "设备相关人员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionRevelantPeople nxpInspectionRevelantPeople)
    {
        return toAjax(nxpInspectionRevelantPeopleService.insertNxpInspectionRevelantPeople(nxpInspectionRevelantPeople));
    }

    /**
     * 修改设备相关人员
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionRevelantPeople nxpInspectionRevelantPeople = nxpInspectionRevelantPeopleService.selectNxpInspectionRevelantPeopleById(id);
        mmap.put("nxpInspectionRevelantPeople", nxpInspectionRevelantPeople);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备相关人员
     */
    @RequiresPermissions("system:inspectionRevelantPeople:edit")
    @Log(title = "设备相关人员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionRevelantPeople nxpInspectionRevelantPeople)
    {
        return toAjax(nxpInspectionRevelantPeopleService.updateNxpInspectionRevelantPeople(nxpInspectionRevelantPeople));
    }

    /**
     * 删除设备相关人员
     */
    @RequiresPermissions("system:inspectionRevelantPeople:remove")
    @Log(title = "设备相关人员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionRevelantPeopleService.deleteNxpInspectionRevelantPeopleByIds(ids));
    }
}
