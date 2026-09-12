package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.service.INxpUserInfoService;
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
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.service.INxpMachineProcessService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import sun.font.DelegatingShape;

/**
 * 工序Controller
 * 
 * @author YanCheng Liu
 * @date 2021-02-08
 */
@Controller
@RequestMapping("/system/machineProcess")
public class NxpMachineProcessController extends BaseController
{
    private String prefix = "system/machineProcess";

    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:machineProcess:view")
    @GetMapping()
    public String machineProcess(ModelMap mmap)
    {
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        mmap.put("userList",nxpUserInfos);

        return prefix + "/machineProcess";
    }

    /**
     * 查询工序列表
     */
    @RequiresPermissions("system:machineProcess:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpMachineProcess nxpMachineProcess)
    {
        startPage();
        List<NxpMachineProcess> list = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        for (NxpMachineProcess machineProcess : list) {
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(machineProcess.getOwner());
            if (nxpUserInfo == null){
                machineProcess.setArea("");
                continue;
            }
            switch (nxpUserInfo.getArea()){
                case "1":
                    machineProcess.setArea("Non-lead");
                    break;
                case "2":
                    machineProcess.setArea("Leaded");
                    break;
                case "3":
                    machineProcess.setArea("Plating");
                    break;
                case "4":
                    machineProcess.setArea("PM");
                    break;
                default:
                    machineProcess.setArea("");
                    break;

            }
        }
        return getDataTable(list);
    }

    /**
     * 导出工序列表
     */
    @RequiresPermissions("system:machineProcess:export")
    @Log(title = "工序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpMachineProcess nxpMachineProcess)
    {
        List<NxpMachineProcess> list = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        ExcelUtil<NxpMachineProcess> util = new ExcelUtil<NxpMachineProcess>(NxpMachineProcess.class);
        return util.exportExcel(list, "machineProcess");
    }

    /**
     * 新增工序
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        mmap.put("userList",nxpUserInfos);
        return prefix + "/add";
    }

    /**
     * 新增保存工序
     */
    @RequiresPermissions("system:machineProcess:add")
    @Log(title = "工序", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpMachineProcess nxpMachineProcess)
    {
        return toAjax(nxpMachineProcessService.insertNxpMachineProcess(nxpMachineProcess));
    }

    /**
     * 修改工序
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(id);
        mmap.put("nxpMachineProcess", nxpMachineProcess);
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        mmap.put("userList",nxpUserInfos);
        if (nxpMachineProcess.getOwner() != null){
            NxpUserInfo nxpUserInfo1 = nxpUserInfoService.selectNxpUserInfoById(nxpMachineProcess.getOwner());
            mmap.put("userInfo",nxpUserInfo1);
        }else {
            NxpUserInfo nxpUserInfo1 = new NxpUserInfo();
            mmap.put("userInfo",nxpUserInfo1);

        }

        return prefix + "/edit";
    }

    /**
     * 修改保存工序
     */
    @RequiresPermissions("system:machineProcess:edit")
    @Log(title = "工序", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpMachineProcess nxpMachineProcess)
    {
        return toAjax(nxpMachineProcessService.updateNxpMachineProcess(nxpMachineProcess));
    }

    /**
     * 删除工序
     */
    @RequiresPermissions("system:machineProcess:remove")
    @Log(title = "工序", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpMachineProcessService.deleteNxpMachineProcessByIds(ids));
    }
}
