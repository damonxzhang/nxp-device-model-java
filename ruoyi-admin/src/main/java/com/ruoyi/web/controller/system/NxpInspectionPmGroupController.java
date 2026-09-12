package com.ruoyi.web.controller.system;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.DTO.NxpCheckGroupDTO;
import com.ruoyi.system.domain.NxpCheckGroup;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.service.INxpCheckGroupService;
import com.ruoyi.system.service.INxpMachineProcessService;
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
import com.ruoyi.system.domain.NxpInspectionPmGroup;
import com.ruoyi.system.service.INxpInspectionPmGroupService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 周PM分组Controller
 * 
 * @author ruoyi
 * @date 2021-02-21
 */
@Controller
@RequestMapping("/system/inspectionPmGroup")
public class NxpInspectionPmGroupController extends BaseController
{
    private String prefix = "system/inspectionPmGroup";

    @Autowired
    private INxpInspectionPmGroupService nxpInspectionPmGroupService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpCheckGroupService nxpCheckGroupService;

    @RequiresPermissions("system:inspectionPmGroup:view")
    @GetMapping()
    public String inspectionPmGroup()
    {
        return prefix + "/inspectionPmGroup";
    }

    /**
     * 查询周PM分组列表
     */
    @RequiresPermissions("system:inspectionPmGroup:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
        startPage();
        List<NxpInspectionPmGroup> list = nxpInspectionPmGroupService.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);
        for (NxpInspectionPmGroup inspectionPmGroup : list) {
            if (inspectionPmGroup.getCreate_user_id() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(inspectionPmGroup.getCreate_user_id());
                if (nxpUserInfo != null){
                    inspectionPmGroup.setCreateUserName(nxpUserInfo.getChinese_name());
                }
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出周PM分组列表
     */
    @RequiresPermissions("system:inspectionPmGroup:export")
    @Log(title = "周PM分组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
        List<NxpInspectionPmGroup> list = nxpInspectionPmGroupService.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);
        ExcelUtil<NxpInspectionPmGroup> util = new ExcelUtil<NxpInspectionPmGroup>(NxpInspectionPmGroup.class);
        return util.exportExcel(list, "inspectionPmGroup");
    }

    /**
     * 新增周PM分组
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
//        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        List<NxpCheckGroupDTO> list = nxpCheckGroupService.selectListAndProcess();
        mmap.put("checkGroups", JSON.toJSONString(list));
        return prefix + "/add";
    }

    /**
     * 新增保存周PM分组
     */
    @RequiresPermissions("system:inspectionPmGroup:add")
    @Log(title = "周PM分组", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
        Long userId = ShiroUtils.getUserId();
        nxpInspectionPmGroup.setCreate_user_id(userId);
        return toAjax(nxpInspectionPmGroupService.insertNxpInspectionPmGroup(nxpInspectionPmGroup));
    }

    /**
     * 修改周PM分组
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupService.selectNxpInspectionPmGroupById(id);
        List<NxpCheckGroupDTO> list = nxpCheckGroupService.selectListAndProcess();
        mmap.put("checkGroups", JSON.toJSONString(list));
        mmap.put("nxpInspectionPmGroup", nxpInspectionPmGroup);
        return prefix + "/edit";
    }

    /**
     * 修改保存周PM分组
     */
    @RequiresPermissions("system:inspectionPmGroup:edit")
    @Log(title = "周PM分组", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
        return toAjax(nxpInspectionPmGroupService.updateNxpInspectionPmGroup(nxpInspectionPmGroup));
    }

    /**
     * 删除周PM分组
     */
    @RequiresPermissions("system:inspectionPmGroup:remove")
    @Log(title = "周PM分组", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionPmGroupService.deleteNxpInspectionPmGroupByIds(ids));
    }
}
