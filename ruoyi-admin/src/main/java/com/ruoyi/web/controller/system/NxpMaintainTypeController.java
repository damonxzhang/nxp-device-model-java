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
import com.ruoyi.system.domain.NxpMaintainType;
import com.ruoyi.system.service.INxpMaintainTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备问题类型Controller
 * 
 * @author YanCheng Liu
 * @date 2021-03-04
 */
@Controller
@RequestMapping("/system/type")
public class NxpMaintainTypeController extends BaseController
{
    private String prefix = "system/type";

    @Autowired
    private INxpMaintainTypeService nxpMaintainTypeService;

    @RequiresPermissions("system:type:view")
    @GetMapping()
    public String type()
    {
        return prefix + "/type";
    }

    /**
     * 查询设备问题类型列表
     */
    @RequiresPermissions("system:type:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMaintainType nxpMaintainType)
    {
        startPage();
        List<NxpMaintainType> list = nxpMaintainTypeService.selectNxpMaintainTypeList(nxpMaintainType);
        return getDataTable(list);
    }

    /**
     * 导出设备问题类型列表
     */
    @RequiresPermissions("system:type:export")
    @Log(title = "设备问题类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMaintainType nxpMaintainType)
    {
        List<NxpMaintainType> list = nxpMaintainTypeService.selectNxpMaintainTypeList(nxpMaintainType);
        ExcelUtil<NxpMaintainType> util = new ExcelUtil<NxpMaintainType>(NxpMaintainType.class);
        return util.exportExcel(list, "type");
    }

    /**
     * 新增设备问题类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备问题类型
     */
    @RequiresPermissions("system:type:add")
    @Log(title = "设备问题类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMaintainType nxpMaintainType)
    {
        return toAjax(nxpMaintainTypeService.insertNxpMaintainType(nxpMaintainType));
    }

    /**
     * 修改设备问题类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMaintainType nxpMaintainType = nxpMaintainTypeService.selectNxpMaintainTypeById(id);
        mmap.put("nxpMaintainType", nxpMaintainType);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备问题类型
     */
    @RequiresPermissions("system:type:edit")
    @Log(title = "设备问题类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMaintainType nxpMaintainType)
    {
        return toAjax(nxpMaintainTypeService.updateNxpMaintainType(nxpMaintainType));
    }

    /**
     * 删除设备问题类型
     */
    @RequiresPermissions("system:type:remove")
    @Log(title = "设备问题类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMaintainTypeService.deleteNxpMaintainTypeByIds(ids));
    }
}
