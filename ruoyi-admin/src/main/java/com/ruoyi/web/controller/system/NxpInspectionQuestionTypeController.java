package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.service.INxpMachineProcessService;
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
import com.ruoyi.system.domain.NxpInspectionQuestionType;
import com.ruoyi.system.service.INxpInspectionQuestionTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备问题类型Controller
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/questionType")
public class NxpInspectionQuestionTypeController extends BaseController
{
    private String prefix = "system/questionType";

    @Autowired
    private INxpInspectionQuestionTypeService nxpInspectionQuestionTypeService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;

    @RequiresPermissions("system:questionType:view")
    @GetMapping()
    public String questionType(ModelMap mmap)
    {
        NxpMachineProcess machineProcess = new NxpMachineProcess();
        machineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(machineProcess);
        mmap.put("process",nxpMachineProcesses);
        return prefix + "/questionType";
    }

    /**
     * 查询设备问题类型列表
     */
    @RequiresPermissions("system:questionType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionQuestionType nxpInspectionQuestionType)
    {
        // 1. 进入默认显示负责人为当前登录人的数据
        Long userId = ShiroUtils.getUserId();
        List<NxpInspectionQuestionType> list = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        // 根据当前登录人查询匹配的问题类型
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();

        if (nxpInspectionQuestionType.getIsMe() == 1 && userId != 1)
            nxpMachineProcess.setOwner(userId);

        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        for (NxpMachineProcess machineProcess : nxpMachineProcesses) {
            NxpInspectionQuestionType nxpInspectionQuestionType1 = new NxpInspectionQuestionType();
            nxpInspectionQuestionType1.setProcess_id(machineProcess.getId().intValue());
            List<NxpInspectionQuestionType> nxpInspectionQuestionTypes = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType1);
            for (NxpInspectionQuestionType inspectionQuestionType : nxpInspectionQuestionTypes) {
                ids.add(inspectionQuestionType.getId().intValue());
            }
        }

        if (userId == 1){
            startPage();

            list = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);
            for (NxpInspectionQuestionType inspectionQuestionType : list) {
                inspectionQuestionType.setIsMenu(1);
                if (inspectionQuestionType.getProcess_id() != null){
                    NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(inspectionQuestionType.getProcess_id()));
                    if (nxpMachineProcess1 != null){
                        inspectionQuestionType.setProcessOwner(nxpMachineProcess1.getOwner());

                    }
                }
            }
        }else {
            if (nxpInspectionQuestionType.getIsMe() == 1){
                startPage();
                nxpInspectionQuestionType.setIds(ids);
                list = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);
                for (NxpInspectionQuestionType inspectionQuestionType : list) {
                    inspectionQuestionType.setIsMenu(1);
                    if (inspectionQuestionType.getProcess_id() != null){
                        NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(inspectionQuestionType.getProcess_id()));
                        if (nxpMachineProcess1 != null){
                            inspectionQuestionType.setProcessOwner(nxpMachineProcess1.getOwner());

                        }
                    }
                }
            }else {
                startPage();
                list = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);

                for (NxpInspectionQuestionType inspectionQuestionType : list) {
                    inspectionQuestionType.setIsMenu(0);
                    for (Integer id : ids) {
                        if (inspectionQuestionType.getId().intValue() == id){
                            inspectionQuestionType.setIsMenu(1);
                            if (inspectionQuestionType.getProcess_id() != null){
                                NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(inspectionQuestionType.getProcess_id()));
                                if (nxpMachineProcess1 != null){
                                    inspectionQuestionType.setProcessOwner(nxpMachineProcess1.getOwner());

                                }
                            }
                        }
                    }
                }
            }


        }

//        startPage();
//        List<NxpInspectionQuestionType> list = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);
        return getDataTable(list);
    }

    /**
     * 导出设备问题类型列表
     */
    @RequiresPermissions("system:questionType:export")
    @Log(title = "设备问题类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionQuestionType nxpInspectionQuestionType)
    {
        List<NxpInspectionQuestionType> list = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);
        ExcelUtil<NxpInspectionQuestionType> util = new ExcelUtil<NxpInspectionQuestionType>(NxpInspectionQuestionType.class);
        return util.exportExcel(list, "questionType");
    }

    /**
     * 新增设备问题类型
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpMachineProcess machineProcess = new NxpMachineProcess();
        machineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(machineProcess);
        mmap.put("process",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存设备问题类型
     */
    @RequiresPermissions("system:questionType:add")
    @Log(title = "设备问题类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionQuestionType nxpInspectionQuestionType)
    {
        AjaxResult json = new AjaxResult();

        if (nxpInspectionQuestionType.getProcess_id() == null){
            json.put("msg", "请选择工序");
            return json;
        }

        return toAjax(nxpInspectionQuestionTypeService.insertNxpInspectionQuestionType(nxpInspectionQuestionType));
    }

    /**
     * 修改设备问题类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeById(id);
        NxpMachineProcess machineProcess = new NxpMachineProcess();
        machineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(machineProcess);
        mmap.put("process",nxpMachineProcesses);
        mmap.put("nxpInspectionQuestionType", nxpInspectionQuestionType);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备问题类型
     */
    @RequiresPermissions("system:questionType:edit")
    @Log(title = "设备问题类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionQuestionType nxpInspectionQuestionType)
    {
        AjaxResult json = new AjaxResult();

        if (nxpInspectionQuestionType.getProcess_id() == null){
            json.put("msg", "请选择工序");
            return json;
        }


        return toAjax(nxpInspectionQuestionTypeService.updateNxpInspectionQuestionType(nxpInspectionQuestionType));
    }

    /**
     * 删除设备问题类型
     */
    @RequiresPermissions("system:questionType:remove")
    @Log(title = "设备问题类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionQuestionTypeService.deleteNxpInspectionQuestionTypeByIds(ids));
    }
}
