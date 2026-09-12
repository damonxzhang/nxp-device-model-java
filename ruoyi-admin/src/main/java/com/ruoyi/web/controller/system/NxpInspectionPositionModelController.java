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
import com.ruoyi.system.domain.NxpInspectionPositionModel;
import com.ruoyi.system.service.INxpInspectionPositionModelService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维修位置&设备型号Controller
 * 
 * @author DamonZhang
 * @date 2021-04-02
 */
@Controller
@RequestMapping("/system/pisitionModel")
public class NxpInspectionPositionModelController extends BaseController
{
    private String prefix = "system/pisitionModel";

    @Autowired
    private INxpInspectionPositionModelService nxpInspectionPositionModelService;

    @RequiresPermissions("system:model:view")
    @GetMapping()
    public String model()
    {
        return prefix + "/model";
    }

    /**
     * 查询维修位置&设备型号列表
     */
    @RequiresPermissions("system:model:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        startPage();
        List<NxpInspectionPositionModel> list = nxpInspectionPositionModelService.selectNxpInspectionPositionModelList(nxpInspectionPositionModel);
        return getDataTable(list);
    }

    /**
     * 导出维修位置&设备型号列表
     */
    @RequiresPermissions("system:model:export")
    @Log(title = "维修位置&设备型号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        List<NxpInspectionPositionModel> list = nxpInspectionPositionModelService.selectNxpInspectionPositionModelList(nxpInspectionPositionModel);
        ExcelUtil<NxpInspectionPositionModel> util = new ExcelUtil<NxpInspectionPositionModel>(NxpInspectionPositionModel.class);
        return util.exportExcel(list, "model");
    }

    /**
     * 新增维修位置&设备型号
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存维修位置&设备型号
     */
    @RequiresPermissions("system:model:add")
    @Log(title = "维修位置&设备型号", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        return toAjax(nxpInspectionPositionModelService.insertNxpInspectionPositionModel(nxpInspectionPositionModel));
    }

    /**
     * 修改维修位置&设备型号
     */
    @GetMapping("/edit/{positionId}")
    public String edit(@PathVariable("positionId") Long positionId, ModelMap mmap)
    {
        NxpInspectionPositionModel nxpInspectionPositionModel = nxpInspectionPositionModelService.selectNxpInspectionPositionModelById(positionId);
        mmap.put("nxpInspectionPositionModel", nxpInspectionPositionModel);
        return prefix + "/edit";
    }

    /**
     * 修改保存维修位置&设备型号
     */
    @RequiresPermissions("system:model:edit")
    @Log(title = "维修位置&设备型号", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        return toAjax(nxpInspectionPositionModelService.updateNxpInspectionPositionModel(nxpInspectionPositionModel));
    }

    /**
     * 删除维修位置&设备型号
     */
    @RequiresPermissions("system:model:remove")
    @Log(title = "维修位置&设备型号", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPositionModelService.deleteNxpInspectionPositionModelByIds(ids));
    }
}
