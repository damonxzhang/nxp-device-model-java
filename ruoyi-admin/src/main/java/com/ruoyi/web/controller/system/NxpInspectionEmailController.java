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
import com.ruoyi.system.domain.NxpInspectionEmail;
import com.ruoyi.system.service.INxpInspectionEmailService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备维修邮件名单Controller
 * 
 * @author YanCheng Liu
 * @date 2021-08-30
 */
@Controller
@RequestMapping("/system/InspectionEmail")
public class NxpInspectionEmailController extends BaseController
{
    private String prefix = "system/InspectionEmail";

    @Autowired
    private INxpInspectionEmailService nxpInspectionEmailService;

    @RequiresPermissions("system:InspectionEmail:view")
    @GetMapping()
    public String InspectionEmail()
    {
        return prefix + "/InspectionEmail";
    }

    /**
     * 查询设备维修邮件名单列表
     */
    @RequiresPermissions("system:InspectionEmail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionEmail nxpInspectionEmail)
    {
        startPage();
        List<NxpInspectionEmail> list = nxpInspectionEmailService.selectNxpInspectionEmailList(nxpInspectionEmail);
        return getDataTable(list);
    }

    /**
     * 导出设备维修邮件名单列表
     */
    @RequiresPermissions("system:InspectionEmail:export")
    @Log(title = "设备维修邮件名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionEmail nxpInspectionEmail)
    {
        List<NxpInspectionEmail> list = nxpInspectionEmailService.selectNxpInspectionEmailList(nxpInspectionEmail);
        ExcelUtil<NxpInspectionEmail> util = new ExcelUtil<NxpInspectionEmail>(NxpInspectionEmail.class);
        return util.exportExcel(list, "InspectionEmail");
    }

    /**
     * 新增设备维修邮件名单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备维修邮件名单
     */
    @RequiresPermissions("system:InspectionEmail:add")
    @Log(title = "设备维修邮件名单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionEmail nxpInspectionEmail)
    {
        return toAjax(nxpInspectionEmailService.insertNxpInspectionEmail(nxpInspectionEmail));
    }

    /**
     * 修改设备维修邮件名单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionEmail nxpInspectionEmail = nxpInspectionEmailService.selectNxpInspectionEmailById(id);
        mmap.put("nxpInspectionEmail", nxpInspectionEmail);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备维修邮件名单
     */
    @RequiresPermissions("system:InspectionEmail:edit")
    @Log(title = "设备维修邮件名单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionEmail nxpInspectionEmail)
    {
        return toAjax(nxpInspectionEmailService.updateNxpInspectionEmail(nxpInspectionEmail));
    }

    /**
     * 删除设备维修邮件名单
     */
    @RequiresPermissions("system:InspectionEmail:remove")
    @Log(title = "设备维修邮件名单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionEmailService.deleteNxpInspectionEmailByIds(ids));
    }
}
