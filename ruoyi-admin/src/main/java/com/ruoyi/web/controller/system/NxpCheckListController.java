package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.NxpCheckListFile;
import com.ruoyi.system.domain.NxpCheckListItem;
import com.ruoyi.system.domain.NxpInspectionFile;
import com.ruoyi.system.service.INxpCheckListFileService;
import com.ruoyi.system.service.INxpCheckListItemService;
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
import com.ruoyi.system.domain.NxpCheckList;
import com.ruoyi.system.service.INxpCheckListService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.annotation.Resource;

/**
 * 检查记录Controller
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
@Controller
@RequestMapping("/system/checkList")
public class NxpCheckListController extends BaseController
{
    private String prefix = "system/checkList";

    @Resource
    private INxpCheckListService nxpCheckListService;

    @Resource
    private INxpCheckListItemService nxpCheckListItemService;

    @Resource
    private INxpCheckListFileService nxpCheckListFileService;

    @RequiresPermissions("system:checkList:view")
    @GetMapping()
    public String checkList(ModelMap mmap)
    {
        // 工序
        /*NxpMachineProcess nxpMachineProcess = new NxpMachineProcess();
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessService.selectNxpMachineProcessList(nxpMachineProcess);
        mmap.put("processes",nxpMachineProcesses);*/
        return prefix + "/checkList";
    }

    /**
     * 查询检查记录列表
     */
    @RequiresPermissions("system:checkList:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpCheckList nxpCheckList)
    {
        startPage();
        List<NxpCheckList> list = nxpCheckListService.selectNxpCheckListList(nxpCheckList);
        return getDataTable(list);
    }

    /**
     * 导出检查记录列表
     */
    @RequiresPermissions("system:checkList:export")
    @Log(title = "检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpCheckList nxpCheckList)
    {
        List<NxpCheckList> list = nxpCheckListService.selectNxpCheckListList(nxpCheckList);
        ExcelUtil<NxpCheckList> util = new ExcelUtil<NxpCheckList>(NxpCheckList.class);
        return util.exportExcel(list, "checkList");
    }

    /**
     * 新增检查记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存检查记录
     */
    @RequiresPermissions("system:checkList:add")
    @Log(title = "检查记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpCheckList nxpCheckList)
    {
        return toAjax(nxpCheckListService.insertNxpCheckList(nxpCheckList));
    }

    /**
     * 修改检查记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpCheckList nxpCheckList = nxpCheckListService.selectNxpCheckListById(id);
        mmap.put("nxpCheckList", nxpCheckList);
        return prefix + "/edit";
    }

    /**
     * 修改保存检查记录
     */
    @RequiresPermissions("system:checkList:edit")
    @Log(title = "检查记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpCheckList nxpCheckList)
    {
        return toAjax(nxpCheckListService.updateNxpCheckList(nxpCheckList));
    }

    /**
     * 删除检查记录
     */
    @RequiresPermissions("system:checkList:remove")
    @Log(title = "检查记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpCheckListService.deleteNxpCheckListByIds(ids));
    }


    @GetMapping("/toDetails/{id}")
    public String toDetails(@PathVariable("id") Long id, ModelMap mmap){
        List<NxpCheckListItem> nxpCheckListItems = nxpCheckListItemService.selectByCheckListId(id);
        for (NxpCheckListItem nxpCheckListItem : nxpCheckListItems) {
            String files = nxpCheckListItem.getFiles();
            List<NxpCheckListFile> filePaths = new ArrayList<>();
            if (StringUtils.isNotEmpty(files)){
                if (files.contains(",")){
                    String[] file = files.split(",");
                    for (String fileId : file) {
                        NxpCheckListFile nxpCheckListFile = nxpCheckListFileService.selectNxpCheckListFileById(Long.valueOf(fileId));
                        if (nxpCheckListFile != null){
                            filePaths.add(nxpCheckListFile);
                        }
                    }
                    nxpCheckListItem.setNxpCheckListFiles(filePaths);
                }else {
                    NxpCheckListFile nxpCheckListFile = nxpCheckListFileService.selectNxpCheckListFileById(Long.valueOf(files));
                    if (nxpCheckListFile != null){
                        filePaths.add(nxpCheckListFile);
                    }
                    nxpCheckListItem.setNxpCheckListFiles(filePaths);
                }
            }
        }
        mmap.put("nxpCheckListItems",nxpCheckListItems);
        return prefix + "/checkItemDetails";
    }
}
