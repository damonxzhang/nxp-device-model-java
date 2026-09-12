package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.VO.NxpSwcProgramGroupVO;
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
 * SWC互查问题Controller
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/system/swcCheck")
public class NxpSwcCheckController extends BaseController
{
    private String prefix = "system/swcCheck";

    @Autowired
    private INxpSwcCheckService nxpSwcCheckService;
    @Autowired
    private INxpSwcProgramGroupService nxpSwcProgramGroupService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpSwcProgramTypeService nxpSwcProgramTypeService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:swcCheck:view")
    @GetMapping()
    public String swcCheck(ModelMap mmap)
    {
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        mmap.put("processes",nxpMachineProcesses);
        List<NxpSwcProgramGroup> nxpSwcProgramGroup = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(new NxpSwcProgramGroup());
        mmap.put("groups",nxpSwcProgramGroup);
        return prefix + "/swcCheck";
    }

    /**
     * 查询SWC互查问题列表
     */
    @RequiresPermissions("system:swcCheck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpSwcCheck nxpSwcCheck)
    {
        startPage();
        List<NxpSwcCheck> list = nxpSwcCheckService.selectNxpSwcCheckList(nxpSwcCheck);
        return getDataTable(list);
    }

    /**
     * 导出SWC互查问题列表
     */
    @RequiresPermissions("system:swcCheck:export")
    @Log(title = "SWC互查问题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpSwcCheck nxpSwcCheck)
    {
        List<NxpSwcCheck> list = nxpSwcCheckService.selectNxpSwcCheckList(nxpSwcCheck);
        ExcelUtil<NxpSwcCheck> util = new ExcelUtil<NxpSwcCheck>(NxpSwcCheck.class);
        return util.exportExcel(list, "swcCheck");
    }

    /**
     * 新增SWC互查问题
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setStatus(1);
        List<NxpUserInfo> userInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(new NxpMachineProcess());
        List<NxpSwcProgramGroup> nxpSwcProgramGroup = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(new NxpSwcProgramGroup());
        mmap.put("processes",nxpMachineProcesses);
        mmap.put("groups",nxpSwcProgramGroup);
        mmap.put("userInfos",userInfos);
        return prefix + "/add";
    }

    /**
     * 新增保存SWC互查问题
     */
    @RequiresPermissions("system:swcCheck:add")
    @Log(title = "SWC互查问题", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpSwcCheck nxpSwcCheck)
    {
        return toAjax(nxpSwcCheckService.insertNxpSwcCheck(nxpSwcCheck));
    }

    /**
     * 修改SWC互查问题
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        List<NxpSwcProgramGroup> nxpSwcProgramGroup = nxpSwcProgramGroupService.selectNxpSwcProgramGroupList(new NxpSwcProgramGroup());
        mmap.put("groups",nxpSwcProgramGroup);
        NxpSwcCheck nxpSwcCheck = nxpSwcCheckService.selectNxpSwcCheckById(id);
        mmap.put("nxpSwcCheck", nxpSwcCheck);
        return prefix + "/edit";
    }

    /**
     * 修改保存SWC互查问题
     */
    @RequiresPermissions("system:swcCheck:edit")
    @Log(title = "SWC互查问题", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpSwcCheck nxpSwcCheck)
    {
        return toAjax(nxpSwcCheckService.updateNxpSwcCheck(nxpSwcCheck));
    }

    /**
     * 删除SWC互查问题
     */
    @RequiresPermissions("system:swcCheck:remove")
    @Log(title = "SWC互查问题", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpSwcCheckService.deleteNxpSwcCheckByIds(ids));
    }

    /**
     * 根据分组id查询
     * @param nxpSwcProgramGroup
     * @return
     */
    @PostMapping("/getProgramTypes")
    @ResponseBody
    public TableDataInfo getProgramTypes(NxpSwcProgramGroup nxpSwcProgramGroup){
        // 根据groupId查询分类
        /*NxpSwcProgramType nxpSwcProgramType = new NxpSwcProgramType();
        nxpSwcProgramType.setGroupId(nxpSwcProgramGroup.getId());
        List<NxpSwcProgramType> nxpSwcProgramTypes = nxpSwcProgramTypeService.selectNxpSwcProgramTypeList(nxpSwcProgramType);
        List<NxpSwcProgramType> resultList = new ArrayList<>();
        for (NxpSwcProgramType swcProgramType : nxpSwcProgramTypes) {
            resultList.addAll(nxpSwcProgramTypeService.selectNxpSwcProgramTypeByParam(swcProgramType));
        }*/
        List<NxpSwcProgramGroupVO> nxpSwcProgramGroupVOS = nxpSwcProgramGroupService.selectThreeMenu(nxpSwcProgramGroup);
        return getDataTable(nxpSwcProgramGroupVOS);
    }
}
