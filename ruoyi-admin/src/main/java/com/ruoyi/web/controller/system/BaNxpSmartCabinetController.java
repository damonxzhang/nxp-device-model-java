package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.NxpSmartCabinet;
import com.ruoyi.system.service.INxpSmartCabinetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 智能柜管理Controller
 * 
 * @author YanCheng Liu
 * @date 2023-06-24
 */
@Controller
@RequestMapping("/system/baNxpSmartCabinet")
public class BaNxpSmartCabinetController extends BaseController
{
    private String prefix = "system/baNxpSmartCabinet";

    @Autowired
    private INxpSmartCabinetService nxpSmartCabinetService;

    @RequiresPermissions("system:baNxpSmartCabinet:view")
    @GetMapping()
    public String nxpSmartCabinet()
    {
        return prefix + "/nxpSmartCabinet";
    }

    /**
     * 查询智能柜管理列表
     */
    @RequiresPermissions("system:baNxpSmartCabinet:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpSmartCabinet nxpSmartCabinet)
    {
        startPage();
        nxpSmartCabinet.setMoldType(2);
        List<NxpSmartCabinet> list = nxpSmartCabinetService.selectNxpSmartCabinetList(nxpSmartCabinet);
        return getDataTable(list);
    }

    /**
     * 导出智能柜管理列表
     */
    @RequiresPermissions("system:baNxpSmartCabinet:export")
    @Log(title = "智能柜管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpSmartCabinet nxpSmartCabinet)
    {
        List<NxpSmartCabinet> list = nxpSmartCabinetService.selectNxpSmartCabinetList(nxpSmartCabinet);
        ExcelUtil<NxpSmartCabinet> util = new ExcelUtil<NxpSmartCabinet>(NxpSmartCabinet.class);
        return util.exportExcel(list, "nxpSmartCabinet");
    }

    /**
     * 新增智能柜管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存智能柜管理
     */
    @RequiresPermissions("system:baNxpSmartCabinet:add")
    @Log(title = "智能柜管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpSmartCabinet nxpSmartCabinet)
    {
        return toAjax(nxpSmartCabinetService.insertNxpSmartCabinet(nxpSmartCabinet));
    }

    /**
     * 修改智能柜管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpSmartCabinet nxpSmartCabinet = nxpSmartCabinetService.selectNxpSmartCabinetById(id);
        mmap.put("nxpSmartCabinet", nxpSmartCabinet);
        return prefix + "/edit";
    }

    /**
     * 修改保存智能柜管理
     */
    @RequiresPermissions("system:baNxpSmartCabinet:edit")
    @Log(title = "智能柜管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpSmartCabinet nxpSmartCabinet)
    {
        return toAjax(nxpSmartCabinetService.updateNxpSmartCabinet(nxpSmartCabinet));
    }

    /**
     * 删除智能柜管理
     */
    @RequiresPermissions("system:baNxpSmartCabinet:remove")
    @Log(title = "智能柜管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpSmartCabinetService.deleteNxpSmartCabinetByIds(ids));
    }
}
