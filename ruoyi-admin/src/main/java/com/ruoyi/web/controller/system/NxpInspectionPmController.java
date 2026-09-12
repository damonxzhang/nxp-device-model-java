package com.ruoyi.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.VO.NxpFacilityVO;
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
 * 周PM检查Controller
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/InspectionPm")
public class NxpInspectionPmController extends BaseController
{
    private String prefix = "system/InspectionPm";

    @Autowired
    private INxpInspectionPmService nxpInspectionPmService;
    @Autowired
    private INxpInspectionPmFileService nxpInspectionPmFileService;
    @Autowired
    private INxpFacilityService nxpFacilityService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpInspectionPmCheckService nxpInspectionPmCheckService;
    @Autowired
    private INxpInspectionPmGroupItemService nxpInspectionPmGroupItemService;
    @Autowired
    private INxpInspectionPmGroupService nxpInspectionPmGroupService;
    @Autowired
    private INxpInspectionPmFacilityService nxpInspectionPmFacilityService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:InspectionPm:view")
    @GetMapping()
    public String InspectionPm(ModelMap mmap)
    {
        NxpFacilityVO nxpFacility = new NxpFacilityVO();
        nxpFacility.setStatus("1");
        List<NxpFacility> facilityList = nxpFacilityService.selectNxpFacilityByNotInIds(nxpFacility);
        mmap.put("facilityList",facilityList);

        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);

        NxpUserInfo userInfo = new NxpUserInfo();
        userInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(userInfo);
        mmap.put("nxpUserInfos",nxpUserInfos);

//        NxpInspectionPm nxpInspectionPm = new NxpInspectionPm();
//        List<NxpInspectionPm> nxpInspectionPms = nxpInspectionPmService.selectNxpInspectionPmList(nxpInspectionPm);
//        mmap.put("nxpInspectionPms",nxpInspectionPms);


        return prefix + "/InspectionPm";
    }

    /**
     * 查询周PM检查列表
     */
    @RequiresPermissions("system:InspectionPm:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPm nxpInspectionPm)
    {
        startPage();
        List<NxpInspectionPm> nxpInspectionPms = nxpInspectionPmService.selectNxpInspectionPmList(nxpInspectionPm);
        for (NxpInspectionPm inspectionPm : nxpInspectionPms) {
            if (inspectionPm.getFacility_id() != null){
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(inspectionPm.getFacility_id());
                if (nxpFacility != null)
                    inspectionPm.setFacility_name(nxpFacility.getNumber());
            }
            if (inspectionPm.getUserInfo_id() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(inspectionPm.getUserInfo_id());
                if (nxpUserInfo != null)
                    inspectionPm.setUserName(nxpUserInfo.getChinese_name());
            }
            if (inspectionPm.getReview_user_info_id() != null){
                NxpUserInfo nxpUserInfo1 = nxpUserInfoService.selectNxpUserInfoById(inspectionPm.getReview_user_info_id());
                if (nxpUserInfo1 != null)
                    inspectionPm.setReviewUserName(nxpUserInfo1.getChinese_name());
            }
            if (inspectionPm.getStatus() != null){
                switch (inspectionPm.getStatus()){
                    case 1:
                        inspectionPm.setStatus_name("待完成");
                        break;
                    case 2:
                        inspectionPm.setStatus_name("已完成");
                        break;
                    case 3:
                        inspectionPm.setStatus_name("逾期");
                        break;
                    default:
                        inspectionPm.setStatus_name("");
                        break;
                }
            }

            if (inspectionPm.getType() != null){
                switch (inspectionPm.getType().intValue()){
                    case 1:
                        inspectionPm.setType_name("周PM");
                        break;
                    case 2:
                        inspectionPm.setType_name("周PM复查");
                        break;
                    default:
                        inspectionPm.setType_name("");
                        break;
                }
            }

        }

        return getDataTable(nxpInspectionPms);
    }
//
//    /**
//     * 查询周PM检查列表
//     */
//    @RequiresPermissions("system:InspectionPm:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public TableDataInfo list(NxpInspectionPm nxpInspectionPm)
//    {
//        startPage();
//        NxpFacility nxpFacility = new NxpFacility();
//        if (nxpInspectionPm.getFacility_id() != null){
//            nxpFacility.setId(nxpInspectionPm.getFacility_id());
//        }
//        if (nxpInspectionPm.getStatus() != null){
//            nxpFacility.setPm_status(nxpInspectionPm.getStatus());
//        }
//        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityPmList(nxpFacility);
//        for (NxpFacility facility : nxpFacilities) {
//            // 查询设备和分组关联表
//            NxpInspectionPmFacility nxpInspectionPmFacility = new NxpInspectionPmFacility();
//            nxpInspectionPmFacility.setFacility_id(facility.getId());
//            List<NxpInspectionPmFacility> nxpInspectionPmFacilities = nxpInspectionPmFacilityService.selectNxpInspectionPmFacilityList(nxpInspectionPmFacility);
//            // 通过中间表查询group
//            for (NxpInspectionPmFacility inspectionPmFacility : nxpInspectionPmFacilities) {
//                Long group_id = inspectionPmFacility.getGroup_id();
//                NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupService.selectNxpInspectionPmGroupById(group_id);
//                if (nxpInspectionPmGroup != null){
//                    facility.setGroupName(nxpInspectionPmGroup.getName());
//                }
//            }
//            NxpInspectionPm inspectionPm = nxpInspectionPmService.selectNxpInspectionPmByfacilityId(facility.getId());
//            if (inspectionPm != null){
//                facility.setPm_status(inspectionPm.getStatus());
//                facility.setCheck_time(inspectionPm.getCheck_time());
//            }
//        }
//        return getDataTable(nxpFacilities);
//    }

    /**
     * 导出周PM检查列表
     */
    @RequiresPermissions("system:InspectionPm:export")
    @Log(title = "周PM检查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPm nxpInspectionPm)
    {
        List<NxpInspectionPm> list = nxpInspectionPmService.selectNxpInspectionPmList(nxpInspectionPm);
        ExcelUtil<NxpInspectionPm> util = new ExcelUtil<NxpInspectionPm>(NxpInspectionPm.class);
        return util.exportExcel(list, "InspectionPm");
    }

    /**
     * 新增周PM检查
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存周PM检查
     */
    @RequiresPermissions("system:InspectionPm:add")
    @Log(title = "周PM检查", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPm nxpInspectionPm)
    {
        return toAjax(nxpInspectionPmService.insertNxpInspectionPm(nxpInspectionPm));
    }

    /**
     * 修改周PM检查
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPm nxpInspectionPm = nxpInspectionPmService.selectNxpInspectionPmById(id);
        if (nxpInspectionPm.getFacility_id() != null){
            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpInspectionPm.getFacility_id());
            if (nxpFacility != null){
                nxpInspectionPm.setFacility_name(nxpFacility.getNumber());
            }
        }
        if (nxpInspectionPm.getUserInfo_id() != null){
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpInspectionPm.getUserInfo_id());
            if (nxpUserInfo != null){
                nxpInspectionPm.setUserName(nxpUserInfo.getChinese_name());
            }
        }
        if (nxpInspectionPm.getReview_user_info_id() != null){
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpInspectionPm.getReview_user_info_id());
            if (nxpUserInfo != null){
                nxpInspectionPm.setReviewUserName(nxpUserInfo.getChinese_name());
            }
        }
        if (nxpInspectionPm.getStatus() != null){
            switch (nxpInspectionPm.getStatus()){
                case 1:
                    nxpInspectionPm.setStatus_name("待完成");
                    break;
                case 2:
                    nxpInspectionPm.setStatus_name("已完成");
                    break;
                case 3:
                    nxpInspectionPm.setStatus_name("逾期");
                    break;
                default:
                    nxpInspectionPm.setStatus_name("");
                    break;
            }
        }

        if (nxpInspectionPm.getType() != null){
            switch (nxpInspectionPm.getType().intValue()){
                case 1:
                    nxpInspectionPm.setType_name("周PM");
                    break;
                case 2:
                    nxpInspectionPm.setType_name("周PM复查");
                    break;
                default:
                    nxpInspectionPm.setType_name("");
                    break;
            }
        }

        if (nxpInspectionPm.getCheck_time() != null){
            // 维修时间
            // 创建SimpleDateFormat类型对象、 "yyyy-MM-dd HH:ss:mm.SSS"是正则式，分别表示年月日时分秒毫秒
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            // 定义两个时间
            Date time = nxpInspectionPm.getCheck_time();
            nxpInspectionPm.setCheckTime(df.format(time));
        }

        if (nxpInspectionPm.getReal_check_time() != null){
            // 维修时间
            // 创建SimpleDateFormat类型对象、 "yyyy-MM-dd HH:ss:mm.SSS"是正则式，分别表示年月日时分秒毫秒
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 定义两个时间
            Date time = nxpInspectionPm.getReal_check_time();
            nxpInspectionPm.setRealCheckTime(df.format(time));
        }

//        获取图片

        List<NxpInspectionFile> nxpInspectionFile = new ArrayList<>();
        NxpInspectionPmFile nxpInspectionPmFile = new NxpInspectionPmFile();
        nxpInspectionPmFile.setPm_id(id);
        List<NxpInspectionPmFile> nxpInspectionPmFiles = nxpInspectionPmFileService.selectNxpInspectionPmFileList(nxpInspectionPmFile);

        // 查询pmcheck
        NxpInspectionPmCheck nxpInspectionPmCheckParam = new NxpInspectionPmCheck();
        nxpInspectionPmCheckParam.setPm_id(id);
        List<NxpInspectionPmCheck> nxpInspectionPmChecks = nxpInspectionPmCheckService.selectNxpInspectionPmCheckList(nxpInspectionPmCheckParam);
        // groupItem结果集
        List<NxpInspectionPmGroupItem> items = new ArrayList<>();
        // 通过pmcheck中的groupItemId查询问题
        for (NxpInspectionPmCheck nxpInspectionPmCheck : nxpInspectionPmChecks) {
            NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemById(nxpInspectionPmCheck.getGroup_item_id());
            Long groupItemResult = nxpInspectionPmCheck.getGroup_item_result();
            if (groupItemResult != null){
                //检查项结果：1：正常；2：异常
                switch (groupItemResult.intValue()){
                    case 1:
                        nxpInspectionPmGroupItem.setGroup_item_result("正常");
                        break;
                    case 2:
                        nxpInspectionPmGroupItem.setGroup_item_result("异常");
                        break;
                    default:
                        nxpInspectionPmGroupItem.setGroup_item_result("其他");
                }
            }
            for (NxpInspectionPmFile inspectionPmFile : nxpInspectionPmFiles) {
                if (nxpInspectionPmCheck.getId().equals(inspectionPmFile.getPm_check_id())){
                    nxpInspectionPmGroupItem.setFilePath(inspectionPmFile.getFile_path());
                }
            }
            items.add(nxpInspectionPmGroupItem);
        }
        nxpInspectionPm.setNxpInspectionPmGroupItemList(items);
//        mmap.put("nxpInspectionPmGroupItemList", items);
        mmap.put("nxpInspectionPmFiles", nxpInspectionPmFiles);
        mmap.put("nxpInspectionPm", nxpInspectionPm);
        return prefix + "/edit";
    }
//
//    /**
//     * 修改周PM检查
//     */
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable("id") Long id, ModelMap mmap)
//    {
//        NxpFacility facility = nxpFacilityService.selectNxpFacilityById(id);
//        // 处理groupId
//        NxpInspectionPmFacility nxpInspectionPmFacility = new NxpInspectionPmFacility();
//        nxpInspectionPmFacility.setFacility_id(facility.getId());
//        List<NxpInspectionPmFacility> nxpInspectionPmFacilities = nxpInspectionPmFacilityService.selectNxpInspectionPmFacilityList(nxpInspectionPmFacility);
//        // 通过中间表查询group
//        for (NxpInspectionPmFacility inspectionPmFacility : nxpInspectionPmFacilities) {
//            Long group_id = inspectionPmFacility.getGroup_id();
//            NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupService.selectNxpInspectionPmGroupById(group_id);
//            if (nxpInspectionPmGroup != null){
//                facility.setGroupName(nxpInspectionPmGroup.getName());
//            }
//            facility.setGroupId(group_id.toString());
//        }
//        NxpInspectionPmGroup nxpInspectionPmGroup = new NxpInspectionPmGroup();
//        nxpInspectionPmGroup.setStatus(1);
//        List<NxpInspectionPmGroup> nxpInspectionPmGroups = nxpInspectionPmGroupService.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);
//        mmap.put("nxpInspectionPm", facility);
//        mmap.put("groups", nxpInspectionPmGroups);
//        return prefix + "/edit";
//    }

    /**
     * 修改保存周PM检查
     */
    @RequiresPermissions("system:InspectionPm:edit")
    @Log(title = "周PM检查", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpFacility nxpFacility)
    {
        return toAjax(nxpInspectionPmService.updateNxpInspectionPm(nxpFacility));
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

    /**
     * 删除周PM检查
     */
    @RequiresPermissions("system:InspectionPm:remove")
    @Log(title = "周PM检查", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPmService.deleteNxpInspectionPmByIds(ids));
    }
}
