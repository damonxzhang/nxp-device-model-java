package com.ruoyi.web.controller.system;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.NxpInspectionFileMapper;
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
 * 待处理设备Controller
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Controller
@RequestMapping("/system/inspection")
public class NxpInspectionController extends BaseController
{
    private String prefix = "system/inspection";

    @Autowired
    private INxpInspectionService nxpInspectionService;
    @Autowired
    private INxpInspectionPositionService nxpInspectionPositionService;
    @Autowired
    private INxpInspectionContentService nxpInspectionContentService;
    @Autowired
    private INxpInspectionServiceService nxpInspectionServiceService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpInspectionQuestionTypeService nxpInspectionQuestionTypeService;
    @Autowired
    private INxpFacilityService nxpFacilityService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;
    @Autowired
    private INxpInspectionFileService nxpInspectionFileService;
    @Autowired
    private INxpInspectionService nxpInspectionService1;

    @RequiresPermissions("system:inspection:view")
    @GetMapping()
    public String inspection(ModelMap mmap)
    {

        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);

        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("nxpFacilities",nxpFacilities);

        NxpInspectionQuestionType nxpInspectionQuestionType = new NxpInspectionQuestionType();
        nxpInspectionQuestionType.setStatus(1);
        List<NxpInspectionQuestionType> nxpInspectionQuestionTypes = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);
        mmap.put("nxpInspectionQuestionTypes",nxpInspectionQuestionTypes);


        return prefix + "/inspection";
    }

    /**
     * 查询待处理设备列表
     */
    @RequiresPermissions("system:inspection:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspection nxpInspection)
    {
        startPage();
        List<NxpInspection> list = nxpInspectionService.selectNxpInspectionList(nxpInspection);
        return getDataTable(list);
    }

    /**
     * 导出待处理设备列表
     */
    @RequiresPermissions("system:inspection:export")
    @Log(title = "待处理设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspection nxpInspection)
    {
        List<NxpInspection> list = nxpInspectionService.selectNxpInspectionList(nxpInspection);
        ExcelUtil<NxpInspection> util = new ExcelUtil<NxpInspection>(NxpInspection.class);
        return util.exportExcel(list, "inspection");
    }

    /**
     * 新增待处理设备
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {

        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        return prefix + "/add";
    }

    /**
     * 新增保存待处理设备
     */
    @RequiresPermissions("system:inspection:add")
    @Log(title = "待处理设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspection nxpInspection)
    {
        return toAjax(nxpInspectionService.insertNxpInspection(nxpInspection));
    }

    /**
     * 修改待处理设备
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspection nxpInspection = nxpInspectionService.selectNxpInspectionById(id);
        // 处理设备名称
        Long facility_id = nxpInspection.getFacility_id();
        NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facility_id);
        nxpInspection.setFacility_name(nxpFacility.getName());
        // 处理问题类型
        NxpInspectionQuestionType nxpInspectionQuestionType = new NxpInspectionQuestionType();
        nxpInspectionQuestionType.setStatus(1);
        List<NxpInspectionQuestionType> nxpInspectionQuestionTypes = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);
        // 处理设备相关人员
        String relevant_people = nxpInspection.getRelevant_people();
        String relevant_people_name = "";
        if (StringUtils.isNoneBlank(relevant_people)){
            if (relevant_people.indexOf(",") != -1){
                String[] peopleId = relevant_people.split(",");
                for (String s : peopleId) {
                    NxpUserInfo userInfo = nxpUserInfoService.selectNxpUserInfoById(Long.valueOf(s));
                    if (userInfo != null){
                        relevant_people_name = relevant_people_name + userInfo.getChinese_name() + ",";
                    }
                }
            }
        }
        if (StringUtils.isNoneBlank(relevant_people_name)){
            nxpInspection.setRelevant_people_name(relevant_people_name.substring(0,relevant_people_name.length()-1));
        }
        // 处理提建议人
        Long user_info_id = nxpInspection.getUser_info_id();
        if (user_info_id != null){
            NxpUserInfo userInfo = nxpUserInfoService.selectNxpUserInfoById(user_info_id);
            if (userInfo != null){
                nxpInspection.setHead_user(userInfo.getChinese_name());
            }
        }

//        获取图片

        List<NxpInspectionFile> nxpInspectionFile = new ArrayList<>();
        if (nxpInspection.getRepair_file_id() != null){
            nxpInspectionFile = nxpInspectionFileService.selectNxpInspectionFileByIds(nxpInspection.getRepair_file_id().split(","));
        }
        mmap.put("nxpInspectionFile", nxpInspectionFile);

        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());

        NxpInspectionService nxpInspectionService = new NxpInspectionService();
        nxpInspectionService.setInspection_id(id);
        List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceService.selectNxpInspectionServiceList(nxpInspectionService);

        mmap.put("processes",nxpMachineProcesses);
        mmap.put("nxpInspection", nxpInspection);
        mmap.put("questionTypes", nxpInspectionQuestionTypes);
        mmap.put("nxpInspectionServices", nxpInspectionServices);
        return prefix + "/edit";
    }

    /**
     * 报修页面查看维修列表
     */
    @RequiresPermissions("system:inspection:serviceShow")
    @GetMapping("/serviceShow/{id}")
    public String serviceShow(@PathVariable("id") Long id, ModelMap mmap)
    {

        startPage();
        NxpInspectionService nxpInspectionService = new NxpInspectionService();
        nxpInspectionService.setInspection_id(id);
        List<NxpInspectionService> list = nxpInspectionServiceService.selectNxpInspectionServiceList(nxpInspectionService);
        for (NxpInspectionService inspectionService : list) {

            // 设备编号
            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(inspectionService.getFacility_id());
            inspectionService.setFacility_name(nxpFacility.getNumber());

            // 工序
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess()));
            if (nxpMachineProcess != null)
                inspectionService.setProcess_name(nxpMachineProcess.getName());
            else
                inspectionService.setProcess_name("");

            // 负责人
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpFacility.getHead_user());
            if (nxpUserInfo != null)
                inspectionService.setHead_user(nxpUserInfo.getChinese_name());
            else
                inspectionService.setHead_user("");

            //
            NxpInspectionService nxpInspectionService2 = nxpInspectionServiceService.selectNxpInspectionServiceById(inspectionService.getId());

            // 维修位置
            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(nxpInspectionService2.getPosition_id());
            if (nxpInspectionPosition != null)
                inspectionService.setPositionName(nxpInspectionPosition.getPosition());
            else
                inspectionService.setPositionName("");

            // 维修内容
            NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(nxpInspectionService2.getContent_id());
            if (nxpInspectionContent != null)
                inspectionService.setContentName(nxpInspectionContent.getContent());
            else
                inspectionService.setContentName("");

            // 维修人
            NxpUserInfo nxpUserInfo1 = nxpUserInfoService.selectNxpUserInfoById(inspectionService.getUser_info_id());
            if (nxpUserInfo1 != null)
                inspectionService.setUser_info_name(nxpUserInfo1.getChinese_name());
            else
                inspectionService.setUser_info_name("");

            // 开始时间
            if (inspectionService.getCreated_at() != null){
                Timestamp date = new Timestamp(inspectionService.getCreated_at()*1000);
                inspectionService.setCreated_time(date);

                // 维修时间
                // 创建SimpleDateFormat类型对象、 "yyyy-MM-dd HH:ss:mm.SSS"是正则式，分别表示年月日时分秒毫秒
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm.SSS");

                // 定义两个时间
                Date startTime = inspectionService.getService_time();
                Date endTime = inspectionService.getEnd_time();
                if (inspectionService.getEnd_time() == null)
                    inspectionService.setRepair_time("尚未结束");
                else
                    inspectionService.setRepair_time((((endTime.getTime() - startTime.getTime()) / 1000) / 60) + "分钟");

            }

            // 相关维修
            NxpInspection nxpInspection = nxpInspectionService1.selectNxpInspectionById(inspectionService.getInspection_id());
            inspectionService.setInspection_info(nxpInspection.getExplanation());

        }
        mmap.put("list",list);
        mmap.put("inspection_id",id);
        return "system/inspection/serviceShow";

    }

    /**
     * 报修页面查看维修列表
     */
    @RequiresPermissions("system:inspection:serviceShowInfo")
    @GetMapping("/serviceShowInfo/{id}")
    public String serviceShowInfo(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionService nxpInspectionService = nxpInspectionServiceService.selectNxpInspectionServiceById(id);
        if (nxpInspectionService.getPosition_id() != null){
            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(nxpInspectionService.getPosition_id());
            if (nxpInspectionPosition != null)
                nxpInspectionService.setPositionName(nxpInspectionPosition.getPosition());
        }
        if (nxpInspectionService.getContent_id() != null){
            NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(nxpInspectionService.getContent_id());
            if (nxpInspectionContent != null)
                nxpInspectionService.setContentName(nxpInspectionContent.getContent());
        }
        if (nxpInspectionService.getUser_info_id() != null){
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpInspectionService.getUser_info_id());
            if (nxpUserInfo != null)
                nxpInspectionService.setUser_info_name(nxpUserInfo.getChinese_name());
        }
        if (nxpInspectionService.getFacility_id() != null){
            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpInspectionService.getFacility_id());
            if (nxpFacility != null)
                nxpInspectionService.setFacility_name(nxpFacility.getNumber());
        }
        List<NxpInspectionFile> nxpInspectionFile = new ArrayList<>();
        if (nxpInspectionService.getFile_id() != null){
            nxpInspectionFile = nxpInspectionFileService.selectNxpInspectionFileByIds(nxpInspectionService.getFile_id().split(","));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm.SSS");
//        Date createdAt = df.format();
//        nxpInspectionService.setCreated_time(nxpInspectionService.getCreated_at());
        mmap.put("nxpInspectionService", nxpInspectionService);
        mmap.put("nxpInspectionFile", nxpInspectionFile);
        return "system/inspection/serviceShowInfo";

    }

    /**
     * 修改保存待处理设备
     */
    @RequiresPermissions("system:inspection:edit")
    @Log(title = "待处理设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspection nxpInspection)
    {
        return toAjax(nxpInspectionService.updateNxpInspection(nxpInspection));
    }

    @PostMapping("/getMathineByProcess")
    @ResponseBody
    public AjaxResult getMathineByProcess(NxpInspection nxpInspection){
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setProcess(nxpInspection.getProcess_id().toString());
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpFacilities);
        return json;
    }

    @PostMapping("/getTypeByProcess")
    @ResponseBody
    public AjaxResult getTypeByProcess(NxpInspection nxpInspection){
        NxpInspectionQuestionType nxpInspectionQuestionType = new NxpInspectionQuestionType();
        nxpInspectionQuestionType.setProcess_id(nxpInspection.getProcess_id().intValue());
        List<NxpInspectionQuestionType> nxpInspectionQuestionTypes = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpInspectionQuestionTypes);
        return json;
    }

    @PostMapping("/getProcessByArea")
    @ResponseBody
    public AjaxResult getProcessByArea(NxpInspection nxpInspection){
        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setArea(nxpInspection.getArea().toString());
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpMachineProcesses);
        return json;
    }

    /**
     * 删除待处理设备
     */
    @RequiresPermissions("system:inspection:remove")
    @Log(title = "待处理设备", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionService.deleteNxpInspectionByIds(ids));
    }
}
