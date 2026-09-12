package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.NxpFacilityUserMapper;
import com.ruoyi.system.service.INxpFacilityService;
import com.ruoyi.system.service.INxpMachineProcessService;
import com.ruoyi.system.service.INxpUserInfoService;
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
import com.ruoyi.system.service.INxpFacilityUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备相关人员Controller
 * 
 * @author YanCheng Liu
 * @date 2021-04-09
 */
@Controller
@RequestMapping("/system/facilityUser")
public class NxpFacilityUserController extends BaseController
{
    private String prefix = "system/facilityUser";

    @Autowired
    private INxpFacilityUserService nxpFacilityUserService;
    @Autowired
    private INxpFacilityService nxpFacilityService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;


    @RequiresPermissions("system:facilityUser:view")
    @GetMapping()
    public String facilityUser(ModelMap mmap)
    {
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("nxpFacilities",nxpFacilities);

        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);

        return prefix + "/facilityUser";
    }

    /**
     * 查询设备相关人员列表
     */
    @RequiresPermissions("system:facilityUser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpFacilityUser nxpFacilityUser)
    {
        // 1. 进入默认显示负责人为当前登录人的数据
        Long userId = ShiroUtils.getUserId();
        List<NxpFacilityUser> list = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();

        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        if (nxpFacilityUser.getProcessId() != null)
            nxpMachineProcess.setId(Long.valueOf(nxpFacilityUser.getProcessId()));

        if (nxpFacilityUser.getIsMe() == 1 && userId != 1)
            nxpMachineProcess.setOwner(userId);

        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        for (NxpMachineProcess machineProcess : nxpMachineProcesses) {
            NxpFacility nxpFacility = new NxpFacility();
            nxpFacility.setProcess(machineProcess.getId().toString());
            if (nxpFacilityUser.getFacilityId() != null)
                nxpFacility.setId(nxpFacilityUser.getFacilityId());
            nxpFacility.setStatus("1");
            List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
            for (NxpFacility facility : nxpFacilities) {
                ids.add(facility.getId().intValue());
            }
        }

        if (ids.size() == 0)
            return getDataTable(list);


        if (userId == 1){
            startPage();
            nxpFacilityUser.setIds(ids);
            list = nxpFacilityUserService.selectNxpFacilityUserList(nxpFacilityUser);
            for (NxpFacilityUser facilityUser : list) {
                facilityUser.setIsMenu(1);
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityUser.getFacilityId());
                if (nxpFacility != null){
                    NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess()));
                    if (nxpMachineProcess1 != null){
                        facilityUser.setProcessOwner(nxpMachineProcess1.getOwner());
                    }
                }
            }
        }else {
            if (nxpFacilityUser.getIsMe() == 1){
                startPage();
                nxpFacilityUser.setIds(ids);
                list = nxpFacilityUserService.selectNxpFacilityUserList(nxpFacilityUser);
                for (NxpFacilityUser facilityUser : list) {
                    facilityUser.setIsMenu(1);
                    NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityUser.getFacilityId());
                    if (nxpFacility != null){
                        NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess()));
                        if (nxpMachineProcess1 != null){
                            facilityUser.setProcessOwner(nxpMachineProcess1.getOwner());
                        }
                    }
                }
            }else {
                startPage();
                nxpFacilityUser.setIds(ids);
                list = nxpFacilityUserService.selectNxpFacilityUserList(nxpFacilityUser);

                for (NxpFacilityUser facilityUser : list) {
                    facilityUser.setIsMenu(0);
                    for (Integer id : ids) {
                        if (facilityUser.getFacilityId().intValue() == id){
                            facilityUser.setIsMenu(1);
                            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityUser.getFacilityId());
                            if (nxpFacility != null){
                                NxpMachineProcess nxpMachineProcess1 = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess()));
                                if (nxpMachineProcess1 != null){
                                    facilityUser.setProcessOwner(nxpMachineProcess1.getOwner());
                                }
                            }
                        }
                    }
                }
            }


        }

        return getDataTable(list);

//        startPage();
//        List<NxpFacilityUser> list = nxpFacilityUserService.selectNxpFacilityUserList(nxpFacilityUser);
//        return getDataTable(list);
    }

    /**
     * 导出设备相关人员列表
     */
    @RequiresPermissions("system:facilityUser:export")
    @Log(title = "设备相关人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpFacilityUser nxpFacilityUser)
    {
        List<NxpFacilityUser> list = nxpFacilityUserService.selectNxpFacilityUserList(nxpFacilityUser);
        ExcelUtil<NxpFacilityUser> util = new ExcelUtil<NxpFacilityUser>(NxpFacilityUser.class);
        return util.exportExcel(list, "facilityUser");
    }

    /**
     * 新增设备相关人员
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("nxpFacilities",nxpFacilities);

        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("nxpMachineProcesses",nxpMachineProcesses);

        return prefix + "/add";
    }

    /**
     * 新增保存设备相关人员
     */
    @RequiresPermissions("system:facilityUser:add")
    @Log(title = "设备相关人员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpFacilityUser nxpFacilityUser)
    {
        return toAjax(nxpFacilityUserService.insertNxpFacilityUser(nxpFacilityUser));
    }

    /**
     * 修改设备相关人员
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {

        List<NxpFacilityUser> nxpFacilityUsers = nxpFacilityUserService.selectNxpFacilityUserByFacilityId(id);
//        NxpFacility nxpFacility = new NxpFacility();
//        nxpFacility.setStatus("1");
//        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        NxpFacility nxpFacility1 = nxpFacilityService.selectNxpFacilityById(id);
        // 人员
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        for (NxpFacilityUser nxpFacilityUser : nxpFacilityUsers) {
            if (nxpFacilityUser.getUserId() != null){
                nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpFacilityUser.getUserId());
            }
        }
        NxpUserInfo param = new NxpUserInfo();
        param.setStatus(1);
        if (StringUtils.isNoneBlank(nxpUserInfo.getArea()) && nxpUserInfo.getShift() != null){
            param.setArea(nxpUserInfo.getArea());
            param.setShift(nxpUserInfo.getShift());
        }


        NxpUserInfo nxpUserInfo1 = new NxpUserInfo();
        nxpUserInfo1.setStatus(1);
        nxpUserInfo1.setArea(nxpUserInfo.getArea());
        nxpUserInfo1.setShift(nxpUserInfo.getShift());
        List<NxpUserInfo> nxpUserInfos1 = nxpUserInfoService.selectNxpUserInfoListByFacility(nxpUserInfo1, id);


//        NxpFacilityUser nxpFacilityUser = nxpFacilityUserService.selectNxpFacilityUserById(id);
//        NxpFacility nxpFacility = new NxpFacility();
//        nxpFacility.setStatus("1");
//        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
//        // 人员
//        NxpUserInfo nxpUserInfo = new NxpUserInfo();
//        if (nxpFacilityUser.getUserId() != null){
//            nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpFacilityUser.getUserId().longValue());
//        }
//        NxpUserInfo param = new NxpUserInfo();
//        param.setStatus(1);
//        if (StringUtils.isNoneBlank(nxpUserInfo.getArea()) && nxpUserInfo.getShift() != null){
//            param.setArea(nxpUserInfo.getArea());
//            param.setShift(nxpUserInfo.getShift());
//        }
//        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(param);


        NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("nxpMachineProcesses",nxpMachineProcesses);


        mmap.put("userInfo",nxpUserInfo);
        mmap.put("userInfos",nxpUserInfos1);
        mmap.put("nxpFacilities",nxpFacility1);
        mmap.put("facilityId", id);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备相关人员
     */
    @RequiresPermissions("system:facilityUser:edit")
    @Log(title = "设备相关人员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpFacilityUser nxpFacilityUser)
    {
        return toAjax(nxpFacilityUserService.updateNxpFacilityUser(nxpFacilityUser));
    }

    /**
     * 删除设备相关人员
     */
    @RequiresPermissions("system:facilityUser:remove")
    @Log(title = "设备相关人员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpFacilityUserService.deleteNxpFacilityUserByIds(ids));
    }

    @PostMapping("/getUserByAreaAndShift")
    @ResponseBody
    public AjaxResult getUserByAreaAndShift(NxpFacilityUser nxpFacilityUser){
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setArea(nxpFacilityUser.getArea().toString());
        nxpUserInfo.setShift(nxpFacilityUser.getShift());
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        AjaxResult json = new AjaxResult();
        json.put("msg", nxpUserInfos);
        return json;
    }

    @PostMapping("/getMathineByProcess")
    @ResponseBody
    public AjaxResult getMathineByProcess(NxpFacilityUser nxpFacilityUser){
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setProcess(nxpFacilityUser.getProcessId().toString());
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);

        AjaxResult json = new AjaxResult();
        json.put("msg", nxpFacilities);
        return json;
    }
}
