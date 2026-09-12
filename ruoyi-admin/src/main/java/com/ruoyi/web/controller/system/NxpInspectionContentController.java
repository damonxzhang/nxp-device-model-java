package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
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
 * 维修内容Controller
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Controller
@RequestMapping("/system/InspectionContent")
public class NxpInspectionContentController extends BaseController
{
    private String prefix = "system/InspectionContent";

    @Autowired
    private INxpInspectionContentService nxpInspectionContentService;
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
    @Autowired
    private INxpCheckGroupService nxpCheckGroupService;

    @RequiresPermissions("system:InspectionContent:view")
    @GetMapping()
    public String InspectionContent(ModelMap mmap)
    {
//        NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
//        nxpInspectionPosition.setStatus(1);
//        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionList(nxpInspectionPosition);
//        mmap.put("positions",nxpInspectionPositions);

//        NxpMachineModel nxpMachineModel = new NxpMachineModel();
//        nxpMachineModel.setStatus(1);
//        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
//        mmap.put("nxpMachineModels",nxpMachineModels);

        NxpMachineVendor nxpMachineVendor = new NxpMachineVendor();
        nxpMachineVendor.setStatus(1);
        List<NxpMachineVendor> nxpMachineVendors = nxpMachineVendorService.selectNxpMachineVendorList(nxpMachineVendor);
        mmap.put("nxpMachineVendors",nxpMachineVendors);
//        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
//        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/InspectionContent";
    }

    /**
     * 查询维修内容列表
     */
    @RequiresPermissions("system:InspectionContent:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionContent nxpInspectionContent)
    {
        // 1. 进入默认显示负责人为当前登录人的数据
        Long userId = ShiroUtils.getUserId();
        List<NxpInspectionContent> list = new ArrayList<>();
        List<Integer> positionIds = new ArrayList<>();

        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        if (nxpInspectionContent.getModelId() != null)
            nxpMachineModel.setId(nxpInspectionContent.getModelId());

        if (nxpInspectionContent.getIsMe() == 1 && userId != 1)
            nxpMachineModel.setOwner(userId.intValue());

        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        List<NxpInspectionPositionModel> nxpInspectionPositionModels = new ArrayList<>();
        for (NxpMachineModel machineModel : nxpMachineModels) {
            NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
            nxpInspectionPositionModel.setModelId(machineModel.getId());
            nxpInspectionPositionModels.addAll(nxpInspectionPositionModelService.selectNxpInspectionPositionModelList(nxpInspectionPositionModel));
        }
        for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {
            positionIds.add(nxpInspectionPositionModel.getPositionId().intValue());
        }

        if (nxpInspectionContent.getIsMe() == 1 && userId != 1){
            // 根据当前登录人查询匹配的设备型号
            nxpInspectionContent.setPositionIds(positionIds);
            startPage();
            list = nxpInspectionContentService.selectNxpInspectionContentList(nxpInspectionContent);
            for (NxpInspectionContent inspectionContent : list) {
                inspectionContent.setIsMenu(1);
                NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(inspectionContent.getPosition_id());
                if (nxpInspectionPosition.getProcessId() != null){
                    NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(nxpInspectionPosition.getProcessId());
                    if (nxpMachineProcess1 != null){
                        inspectionContent.setProcessOwner(nxpMachineProcess1.getOwner());
                        inspectionContent.setCheckProcessName(nxpMachineProcess1.getName());
                    }
                }
                if (inspectionContent.getCheck_group_id() != null){
                    NxpCheckGroup nxpCheckGroup = nxpCheckGroupService.selectNxpCheckGroupById(inspectionContent.getCheck_group_id());
                    if (nxpCheckGroup != null){
                        inspectionContent.setCheckGroupName(nxpCheckGroup.getGroupName());
                    }
                }
            }
        }else {
            startPage();
            list = nxpInspectionContentService.selectNxpInspectionContentList(nxpInspectionContent);
            if (userId != 1){
                for (NxpInspectionContent inspectionContent : list) {
                    for (Integer positionId : positionIds) {
                        if (inspectionContent.getPosition_id().intValue() == positionId){
                            inspectionContent.setIsMenu(1);
                            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(inspectionContent.getPosition_id());
                            if (nxpInspectionPosition.getProcessId() != null){
                                NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(nxpInspectionPosition.getProcessId());
                                if (nxpMachineProcess1 != null){
                                    inspectionContent.setProcessOwner(nxpMachineProcess1.getOwner());
                                    inspectionContent.setCheckProcessName(nxpMachineProcess1.getName());
                                }
                            }
                        }else {
                            inspectionContent.setIsMenu(0);
                            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(inspectionContent.getPosition_id());
                            if (nxpInspectionPosition.getProcessId() != null){
                                NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(nxpInspectionPosition.getProcessId());
                                if (nxpMachineProcess1 != null){
                                    inspectionContent.setProcessOwner(nxpMachineProcess1.getOwner());
                                }
                            }
                        }
                    }
                    if (inspectionContent.getCheck_group_id() != null){
                        NxpCheckGroup nxpCheckGroup = nxpCheckGroupService.selectNxpCheckGroupById(inspectionContent.getCheck_group_id());
                        if (nxpCheckGroup != null){
                            inspectionContent.setCheckGroupName(nxpCheckGroup.getGroupName());
                        }
                    }
                }
            }else {
                for (NxpInspectionContent inspectionContent : list) {
                    NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(inspectionContent.getProcessId());
                    if (nxpMachineProcess != null){
                        inspectionContent.setCheckProcessName(nxpMachineProcess.getName());
                    }
                    if (inspectionContent.getCheck_group_id() != null){
                        NxpCheckGroup nxpCheckGroup = nxpCheckGroupService.selectNxpCheckGroupById(inspectionContent.getCheck_group_id());
                        if (nxpCheckGroup != null){
                            inspectionContent.setCheckGroupName(nxpCheckGroup.getGroupName());
                        }
                    }
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出维修内容列表
     */
    @RequiresPermissions("system:InspectionContent:export")
    @Log(title = "维修内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionContent nxpInspectionContent)
    {
        List<NxpInspectionContent> list = nxpInspectionContentService.selectNxpInspectionContentList(nxpInspectionContent);
        ExcelUtil<NxpInspectionContent> util = new ExcelUtil<NxpInspectionContent>(NxpInspectionContent.class);
        return util.exportExcel(list, "InspectionContent");
    }

    /**
     * 新增维修内容
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
        nxpInspectionPosition.setStatus(1);
        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionList(nxpInspectionPosition);
        mmap.put("positions",nxpInspectionPositions);

        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        mmap.put("machineModels",nxpMachineModels);

        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存维修内容
     */
    @RequiresPermissions("system:InspectionContent:add")
    @Log(title = "维修内容", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionContent nxpInspectionContent)
    {
        //判断是否重名
        NxpInspectionContent nxpInspectionContent1 = new NxpInspectionContent();
        nxpInspectionContent1.setPosition_id(nxpInspectionContent.getPosition_id());
        List<NxpInspectionContent> nxpInspectionContents = nxpInspectionContentService.selectNxpInspectionContentList(nxpInspectionContent1);
        for (NxpInspectionContent inspectionContent : nxpInspectionContents) {
            if (inspectionContent.getContent().equals(nxpInspectionContent.getContent()))
                return AjaxResult.error("该维修位置下-维修内容已存在，请重新命名");
        }


        return toAjax(nxpInspectionContentService.insertNxpInspectionContent(nxpInspectionContent));
    }

    /**
     * 修改维修内容
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Long modelId = 0L;
        NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(id);
        if (nxpInspectionContent.getPosition_id() != null){
            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(nxpInspectionContent.getPosition_id());
            nxpInspectionContent.setPositionName(nxpInspectionPosition.getPosition());
            NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
            nxpInspectionPositionModel.setPositionId(nxpInspectionContent.getPosition_id());
            List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelService.selectNxpInspectionPositionModelList(nxpInspectionPositionModel);
            for (NxpInspectionPositionModel inspectionPositionModel : nxpInspectionPositionModels) {
                nxpInspectionContent.setModelId(inspectionPositionModel.getModelId());
                modelId = inspectionPositionModel.getModelId();
            }
        }
        List<Integer> positionIds = new ArrayList<>();

        if (modelId != 0L){

                NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
                nxpInspectionPositionModel.setModelId(modelId);
                List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelService.selectNxpInspectionPositionModelListByModelId(nxpInspectionPositionModel);
                for (NxpInspectionPositionModel inspectionPositionModel : nxpInspectionPositionModels) {
                    positionIds.add(inspectionPositionModel.getId().intValue());
                }

        }

        NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
        nxpInspectionPosition.setStatus(1);
        nxpInspectionPosition.setIds(positionIds);
        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionList(nxpInspectionPosition);
        mmap.put("positions",nxpInspectionPositions);

        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setStatus(1);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);
        mmap.put("machineModels",nxpMachineModels);

        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);

        if (nxpInspectionContent.getCheck_group_id() != null){
            Long check_process_id = nxpInspectionContent.getCheck_process_id();
            NxpCheckGroup nxpCheckGroup = new NxpCheckGroup();
            nxpCheckGroup.setProcessId(check_process_id);
            List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(nxpCheckGroup);
            mmap.put("checkGroups",nxpCheckGroups);
        }
        mmap.put("nxpInspectionContent", nxpInspectionContent);
        return prefix + "/edit";
    }

    /**
     * 修改保存维修内容
     */
    @RequiresPermissions("system:InspectionContent:edit")
    @Log(title = "维修内容", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionContent nxpInspectionContent)
    {
        //判断是否重名
        NxpInspectionContent nxpInspectionContent1 = new NxpInspectionContent();
//        nxpInspectionContent1.setProcessId(nxpInspectionContent.getProcessId());
        nxpInspectionContent1.setModelId(nxpInspectionContent.getModelId());
        nxpInspectionContent1.setContent(nxpInspectionContent.getContent());
        nxpInspectionContent1.setPosition_id(nxpInspectionContent.getPosition_id());
        nxpInspectionContent1.setStatus(1);
        List<NxpInspectionContent> nxpInspectionContents = nxpInspectionContentService.selectNxpInspectionContentList(nxpInspectionContent1);
        for (NxpInspectionContent inspectionContent : nxpInspectionContents) {
            if (inspectionContent.getContent().equals(nxpInspectionContent.getContent())){
//                return AjaxResult.error("该维修位置下-维修内容已存在，请重新命名");
            }
        }


        return toAjax(nxpInspectionContentService.updateNxpInspectionContent(nxpInspectionContent));
    }

    /**
     * 删除维修内容
     */
    @RequiresPermissions("system:InspectionContent:remove")
    @Log(title = "维修内容", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionContentService.deleteNxpInspectionContentByIds(ids));
    }

    @GetMapping( "/getPositionByModel/{machineModelId}")
    @ResponseBody
    public AjaxResult getPositionByModel(@PathVariable("machineModelId") Long machineModelId)
    {
        NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
        nxpInspectionPositionModel.setModelId(machineModelId);
        List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelService.selectNxpInspectionPositionModelListByModelId(nxpInspectionPositionModel);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpInspectionPositionModels);
        return json;
    }


    @PostMapping("/getModelByVendor")
    @ResponseBody
    public AjaxResult getModelByVendor(NxpInspectionContent nxpInspectionContent){
        NxpMachineModel nxpMachineModel = new NxpMachineModel();
        nxpMachineModel.setVendorId(nxpInspectionContent.getVendorId());
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelList(nxpMachineModel);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpMachineModels);
        return json;
    }

    @PostMapping("/getPositionByModel")
    @ResponseBody
    public AjaxResult getPositionByModel(NxpInspectionContent nxpInspectionContent){

        NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
        nxpInspectionPositionModel.setModelId(nxpInspectionContent.getModelId());
        List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelService.selectNxpInspectionPositionModelListByModelId(nxpInspectionPositionModel);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpInspectionPositionModels);
        return json;
    }


}
