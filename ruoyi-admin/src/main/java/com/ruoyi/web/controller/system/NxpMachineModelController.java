package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.NxpInspectionPositionMapper;
import com.ruoyi.system.service.*;
import org.apache.commons.lang3.StringUtils;
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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备型号Controller
 * 
 * @author YanCheng Liu
 * @date 2021-02-21
 */
@Controller
@RequestMapping("/system/model")
public class NxpMachineModelController extends BaseController
{
    private String prefix = "system/model";

    @Autowired
    private INxpMachineModelService nxpMachineModelService;
    @Autowired
    private INxpMachineVendorService nxpMachineVendorService;

    @Autowired
    private INxpInspectionPositionService nxpInspectionPositionService;

    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:model:view")
    @GetMapping()
    public String model(ModelMap mmap)
    {
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        NxpMachineVendor nxpMachineVendor = new NxpMachineVendor();
        nxpMachineVendor.setStatus(1);
        List<NxpMachineVendor> nxpMachineVendors = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);
        mmap.put("nxpMachineVendors",nxpMachineVendors);

        return prefix + "/model";
    }

    /**
     * 查询设备型号列表
     */
    @RequiresPermissions("system:model:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMachineModel nxpMachineModel)
    {
        startPage();
        // 1. 进入默认显示负责人为当前登录人的数据
        Long userId = ShiroUtils.getUserId();
        if (nxpMachineModel.getIsMe() == 1 && userId != 1){
            // 显示自己负责的数据
            nxpMachineModel.setOwner(userId.intValue());
        }
        List<NxpMachineModel> list = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        return getDataTable(list);
    }

    /**
     * 导出设备型号列表
     */
    @RequiresPermissions("system:model:export")
    @Log(title = "设备型号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMachineModel nxpMachineModel)
    {
        List<NxpMachineModel> list = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        ExcelUtil<NxpMachineModel> util = new ExcelUtil<NxpMachineModel>(NxpMachineModel.class);
        return util.exportExcel(list, "model");
    }

    /**
     * 新增设备型号
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
//        NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
//        nxpInspectionPosition.setStatus(1);
//        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionList(nxpInspectionPosition);
//        mmap.put("position",nxpInspectionPositions);
        NxpMachineVendor nxpMachineVendor = new NxpMachineVendor();
        nxpMachineVendor.setStatus(1);
        List<NxpMachineVendor> nxpMachineVendors = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);
        mmap.put("vendorList",nxpMachineVendors);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存设备型号
     */
    @RequiresPermissions("system:model:add")
    @Log(title = "设备型号", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMachineModel nxpMachineModel)
    {
        int i = nxpMachineModelService.insertNxpMachineModel(nxpMachineModel);
        if (i==2)
            return AjaxResult.error("该机型已存在，请重新命名");
        return toAjax(i);
    }

    /**
     * 修改设备型号
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMachineModel nxpMachineModel = nxpMachineModelService.selectNxpMachineModelById(id);
        NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
        nxpInspectionPosition.setStatus(1);
        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionByModelId(nxpInspectionPosition, id);
        //工序
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        // 人员
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        if (nxpMachineModel.getOwner() != null){
            nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpMachineModel.getOwner().longValue());
        }
        NxpUserInfo param = new NxpUserInfo();
        param.setStatus(1);
        if (StringUtils.isNoneBlank(nxpUserInfo.getArea()) && nxpUserInfo.getShift() != null){
            param.setArea(nxpUserInfo.getArea());
            param.setShift(nxpUserInfo.getShift());
        }
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(param);
        mmap.put("userInfo",nxpUserInfo);
        mmap.put("processes",nxpMachineProcesses);
        mmap.put("userInfos",nxpUserInfos);
        mmap.put("nxpMachineModel", nxpMachineModel);
        mmap.put("position",nxpInspectionPositions);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备型号
     */
    @RequiresPermissions("system:model:edit")
    @Log(title = "设备型号", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMachineModel nxpMachineModel)
    {
        int i = nxpMachineModelService.updateNxpMachineModel(nxpMachineModel);
        if (i==2)
            return AjaxResult.error("该机型已存在，请重新命名");

        return toAjax(i);
    }

    /**
     * 删除设备型号
     */
    @RequiresPermissions("system:model:remove")
    @Log(title = "设备型号", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMachineModelService.deleteNxpMachineModelByIds(ids));
    }

    /**
     * 根据工序筛选维修位置
     */
    @GetMapping( "/getPositionByProcess/{processId}")
    @ResponseBody
    public AjaxResult getPositionByProcess(@PathVariable("processId") Long processId){

        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionByProcessId(processId);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpInspectionPositions);
        return json;
//        return toAjax(1);
    }

    @PostMapping("/getUserByAreaAndShift")
    @ResponseBody
    public AjaxResult getUserByAreaAndShift(NxpMachineModel nxpMachineModel){
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setArea(nxpMachineModel.getArea().toString());
        nxpUserInfo.setShift(nxpMachineModel.getShift());
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        AjaxResult json = new AjaxResult();
        json.put("msg", nxpUserInfos);
        return json;
    }
}
