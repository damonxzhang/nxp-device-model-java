package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import io.swagger.models.auth.In;
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
 * 维修位置Controller
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Controller
@RequestMapping("/system/inspectionPosition")
public class NxpInspectionPositionController extends BaseController
{
    private String prefix = "system/inspectionPosition";

    @Autowired
    private INxpInspectionPositionService nxpInspectionPositionService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpMachineModelService nxpMachineModelService;
    @Autowired
    private INxpMachineVendorService nxpMachineVendorService;
    @Autowired
    private INxpInspectionPositionModelService nxpInspectionPositionModelService;

    @RequiresPermissions("system:inspectionPosition:view")
    @GetMapping()
    public String inspectionPosition(ModelMap mmap)
    {
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);

        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        mmap.put("nxpMachineModels",nxpMachineModels);

        NxpMachineVendor nxpMachineVendor = new NxpMachineVendor();
        nxpMachineVendor.setStatus(1);
        List<NxpMachineVendor> nxpMachineVendors = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);
        mmap.put("nxpMachineVendors",nxpMachineVendors);

        return prefix + "/inspectionPosition";
    }

    /**
     * 查询维修位置列表
     */
    @RequiresPermissions("system:inspectionPosition:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPosition nxpInspectionPosition)
    {
        // 1. 进入默认显示负责人为当前登录人的数据
        Long userId = ShiroUtils.getUserId();
        List<NxpInspectionPosition> list = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        // 根据当前登录人查询匹配的设备型号
        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        if (nxpInspectionPosition.getIsMe() == 1 && userId != 1)
            nxpMachineModel.setOwner(userId.intValue());
        nxpMachineModel.setId(nxpInspectionPosition.getModelId());
        nxpMachineModel.setVendorId(nxpInspectionPosition.getVendorId());
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        List<NxpInspectionPositionModel> nxpInspectionPositionModels = new ArrayList<>();
        for (NxpMachineModel machineModel : nxpMachineModels) {
            NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
            nxpInspectionPositionModel.setModelId(machineModel.getId());
            nxpInspectionPositionModels.addAll(nxpInspectionPositionModelService.selectNxpInspectionPositionModelList(nxpInspectionPositionModel));
        }
        for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {
            ids.add(nxpInspectionPositionModel.getPositionId().intValue());
        }

        if (nxpInspectionPosition.getIsMe() == 1 && userId != 1){
            // 显示自己负责的数据
            nxpInspectionPosition.setIds(ids);
            startPage();
            list = nxpInspectionPositionService.selectNxpInspectionPositionList(nxpInspectionPosition);
            for (NxpInspectionPosition inspectionPosition : list) {
                inspectionPosition.setIsMenu(1);
                NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(inspectionPosition.getProcessId());
                if (nxpMachineProcess1 != null){
                    inspectionPosition.setProcessOwner(nxpMachineProcess1.getOwner());
                }
            }
        }else {
            startPage();
//            if (nxpInspectionPosition.getModelId() != null || nxpInspectionPosition.getVendorId() != null)
//                nxpInspectionPosition.setIds(ids);

            list = nxpInspectionPositionService.selectNxpInspectionPositionList(nxpInspectionPosition);
            if (userId != 1){
                for (NxpInspectionPosition inspectionPosition : list) {
                    for (Integer id : ids) {
                        if (inspectionPosition.getId().intValue() == id){
                            inspectionPosition.setIsMenu(1);
                            NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(inspectionPosition.getProcessId());
                            if (nxpMachineProcess1 != null){
                                inspectionPosition.setProcessOwner(nxpMachineProcess1.getOwner());
                            }
                        }else {
                            inspectionPosition.setIsMenu(0);
                            NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(inspectionPosition.getProcessId());
                            if (nxpMachineProcess1 != null){
                                inspectionPosition.setProcessOwner(nxpMachineProcess1.getOwner());
                            }
                        }
                    }
                }
            }
        }
//        if (ids.isEmpty()){
//            List<NxpInspectionPosition> list1 = new ArrayList<>();
//            return getDataTable(list1);
//        }
        return getDataTable(list);
    }

    @PostMapping("/getModelByVendor")
    @ResponseBody
    public AjaxResult getMathineByProcess(NxpInspectionPosition nxpInspectionPosition){
        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setVendorId(nxpInspectionPosition.getVendorId());
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpMachineModels);
        return json;
    }

    /**
     * 导出维修位置列表
     */
    @RequiresPermissions("system:inspectionPosition:export")
    @Log(title = "维修位置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPosition nxpInspectionPosition)
    {
        List<NxpInspectionPosition> list = nxpInspectionPositionService.selectNxpInspectionPositionList(nxpInspectionPosition);
        ExcelUtil<NxpInspectionPosition> util = new ExcelUtil<NxpInspectionPosition>(NxpInspectionPosition.class);
        return util.exportExcel(list, "inspectionPosition");
    }

    /**
     * 新增维修位置
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        mmap.put("model",nxpMachineModels);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存维修位置
     */
    @RequiresPermissions("system:inspectionPosition:add")
    @Log(title = "维修位置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPosition nxpInspectionPosition)
    {
        int i = nxpInspectionPositionService.insertNxpInspectionPosition(nxpInspectionPosition);
        if (i==2)
            return AjaxResult.error("该机型下-维修位置已存在，请重新命名");

        return toAjax(i);
    }

    /**
     * 修改维修位置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelListByPositionId(nxpMachineModel,id);
        mmap.put("model",nxpMachineModels);

        NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(id);
        mmap.put("nxpInspectionPosition", nxpInspectionPosition);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/edit";
    }

    /**
     * 修改保存维修位置
     */
    @RequiresPermissions("system:inspectionPosition:edit")
    @Log(title = "维修位置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPosition nxpInspectionPosition)
    {
        int i = nxpInspectionPositionService.updateNxpInspectionPosition(nxpInspectionPosition);
        if (i==2)
            return AjaxResult.error("该机型下-维修位置已存在，请重新命名");

        return toAjax(i);
    }

    /**
     * 删除维修位置
     */
    @RequiresPermissions("system:inspectionPosition:remove")
    @Log(title = "维修位置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPositionService.deleteNxpInspectionPositionByIds(ids));
    }

    /**
     * 根据工序筛选机型
     */
    @GetMapping( "/getModelByProcess/{processId}")
    @ResponseBody
    public AjaxResult getModelByProcess(@PathVariable("processId") Long processId){

        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelByProcessId(processId);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpMachineModels);
        return json;
//        return toAjax(1);
    }
}
