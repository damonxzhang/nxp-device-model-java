package com.ruoyi.web.controller.system;

import java.util.List;

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
 * 绑定关系管理Controller
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
@Controller
@RequestMapping("/system/bindRelationship")
public class NxpBindRelationshipController extends BaseController
{
    private String prefix = "system/bindRelationship";

    @Autowired
    private INxpBindRelationshipService nxpBindRelationshipService;
    @Autowired
    private INxpCheckContentService nxpCheckContentService;
    @Autowired
    private INxpInspectionPositionService nxpInspectionPositionService;
    @Autowired
    private INxpInspectionContentService nxpInspectionContentService;
    @Autowired
    private INxpSwcJobContentService nxpSwcJobContentService;
    @Autowired
    private INxpInspectionPmGroupService nxpInspectionPmGroupService;
    @Autowired
    private INxpInspectionPmGroupItemService nxpInspectionPmGroupItemService;
    @Autowired
    private INxpCheckGroupService nxpCheckGroupService;

    @RequiresPermissions("system:bindRelationship:view")
    @GetMapping()
    public String bindRelationship(ModelMap mmap)
    {
//        List<NxpCheckContent> nxpCheckContents = nxpCheckContentService.selectNxpCheckContentList(new NxpCheckContent());
//        mmap.put("nxpCheckContents",nxpCheckContents);
        List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(new NxpCheckGroup());
        mmap.put("nxpCheckGroups",nxpCheckGroups);
        return prefix + "/bindRelationship";
    }

    /**
     * 查询绑定关系管理列表
     */
    @RequiresPermissions("system:bindRelationship:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpBindRelationship nxpBindRelationship)
    {
        startPage();
        List<NxpBindRelationship> list = nxpBindRelationshipService.selectNxpBindRelationshipList(nxpBindRelationship);
        for (NxpBindRelationship bindRelationship : list) {
            /*NxpCheckContent nxpCheckContent = nxpCheckContentService.selectNxpCheckContentById(bindRelationship.getCheckContentId());
            bindRelationship.setCheckContentName(nxpCheckContent.getCheckContent());*/
            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(bindRelationship.getPositionId());
            if(nxpInspectionPosition != null){
                bindRelationship.setPositionName(nxpInspectionPosition.getPosition());
            }
            NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(bindRelationship.getInspectionContentId());
            if (nxpInspectionContent != null){
                bindRelationship.setInspectionContentName(nxpInspectionContent.getContent());
            }
            NxpSwcJobContent nxpSwcJobContent = nxpSwcJobContentService.selectNxpSwcJobContentById(bindRelationship.getSwcJobContentId());
            if (nxpSwcJobContent != null){
                bindRelationship.setSwcJobContentName(nxpSwcJobContent.getJobContent());
            }
            NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupService.selectNxpInspectionPmGroupById(bindRelationship.getPmGroupId());
            if (nxpInspectionPmGroup != null){
                bindRelationship.setPmGroupName(nxpInspectionPmGroup.getName());
            }
            NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemById(bindRelationship.getPmGroupItemId());
            if (nxpInspectionPmGroupItem != null){
                bindRelationship.setPmGroupItemName(nxpInspectionPmGroupItem.getItem_name());
            }
            NxpCheckGroup nxpCheckGroup = nxpCheckGroupService.selectNxpCheckGroupById(bindRelationship.getCheckGroupId());
            if (nxpCheckGroup != null){
                bindRelationship.setCheckGroupName(nxpCheckGroup.getGroupName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出绑定关系管理列表
     */
    @RequiresPermissions("system:bindRelationship:export")
    @Log(title = "绑定关系管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpBindRelationship nxpBindRelationship)
    {
        List<NxpBindRelationship> list = nxpBindRelationshipService.selectNxpBindRelationshipList(nxpBindRelationship);
        ExcelUtil<NxpBindRelationship> util = new ExcelUtil<NxpBindRelationship>(NxpBindRelationship.class);
        return util.exportExcel(list, "bindRelationship");
    }

    /**
     * 新增绑定关系管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        // 检查组
//        List<NxpCheckContent> nxpCheckContents = nxpCheckContentService.selectNxpCheckContentList(new NxpCheckContent());
//        mmap.put("nxpCheckContents",nxpCheckContents);
        List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(new NxpCheckGroup());
        mmap.put("nxpCheckGroups",nxpCheckGroups);
        // 维修位置
        NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
        nxpInspectionPosition.setStatus(1);
        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionListNew(nxpInspectionPosition);
        mmap.put("nxpInspectionPositions",nxpInspectionPositions);
        // swc 作业内容
        NxpSwcJobContent nxpSwcJobContent = new NxpSwcJobContent();
        nxpSwcJobContent.setState(1);
        List<NxpSwcJobContent> nxpSwcJobContents = nxpSwcJobContentService.selectNxpSwcJobContentList(nxpSwcJobContent);
        mmap.put("nxpSwcJobContents",nxpSwcJobContents);
        // pm分组
        NxpInspectionPmGroup nxpInspectionPmGroup = new NxpInspectionPmGroup();
        nxpInspectionPmGroup.setStatus(1);
        List<NxpInspectionPmGroup> nxpInspectionPmGroups = nxpInspectionPmGroupService.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);
        mmap.put("nxpInspectionPmGroups",nxpInspectionPmGroups);
        return prefix + "/add";
    }

    /**
     * 新增保存绑定关系管理
     */
    @RequiresPermissions("system:bindRelationship:add")
    @Log(title = "绑定关系管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpBindRelationship nxpBindRelationship)
    {
        return toAjax(nxpBindRelationshipService.insertNxpBindRelationship(nxpBindRelationship));
    }

    /**
     * 修改绑定关系管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpBindRelationship nxpBindRelationship = nxpBindRelationshipService.selectNxpBindRelationshipById(id);
        mmap.put("nxpBindRelationship", nxpBindRelationship);
        // 检查组
//        List<NxpCheckContent> nxpCheckContents = nxpCheckContentService.selectNxpCheckContentList(new NxpCheckContent());
//        mmap.put("nxpCheckContents",nxpCheckContents);
        List<NxpCheckGroup> nxpCheckGroups = nxpCheckGroupService.selectNxpCheckGroupList(new NxpCheckGroup());
        mmap.put("nxpCheckGroups",nxpCheckGroups);
        // 维修位置
        NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
        nxpInspectionPosition.setStatus(1);
        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionService.selectNxpInspectionPositionListNew(nxpInspectionPosition);
        mmap.put("nxpInspectionPositions",nxpInspectionPositions);
        // 维修内容
        NxpInspectionContent nxpInspectionContent = new NxpInspectionContent();
        nxpInspectionContent.setPosition_id(nxpBindRelationship.getPositionId());
        nxpInspectionContent.setStatus(1);
        List<NxpInspectionContent> nxpInspectionContents = nxpInspectionContentService.selectNxpInspectionContentListNew(nxpInspectionContent);
        mmap.put("nxpInspectionContents",nxpInspectionContents);
        // swc 作业内容
        NxpSwcJobContent nxpSwcJobContent = new NxpSwcJobContent();
        nxpSwcJobContent.setState(1);
        List<NxpSwcJobContent> nxpSwcJobContents = nxpSwcJobContentService.selectNxpSwcJobContentList(nxpSwcJobContent);
        mmap.put("nxpSwcJobContents",nxpSwcJobContents);
        // pm分组
        NxpInspectionPmGroup nxpInspectionPmGroup = new NxpInspectionPmGroup();
        nxpInspectionPmGroup.setStatus(1);
        List<NxpInspectionPmGroup> nxpInspectionPmGroups = nxpInspectionPmGroupService.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);
        mmap.put("nxpInspectionPmGroups",nxpInspectionPmGroups);
        // pm检查项
        NxpInspectionPmGroupItem nxpInspectionPmGroupItem = new NxpInspectionPmGroupItem();
        nxpInspectionPmGroupItem.setGroup_id(nxpBindRelationship.getPmGroupId());
        nxpInspectionPmGroupItem.setItem_status(1);
        List<NxpInspectionPmGroupItem> groupItems = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemList(nxpInspectionPmGroupItem);
        mmap.put("groupItems",groupItems);
        return prefix + "/edit";
    }

    /**
     * 修改保存绑定关系管理
     */
    @RequiresPermissions("system:bindRelationship:edit")
    @Log(title = "绑定关系管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpBindRelationship nxpBindRelationship)
    {
        return toAjax(nxpBindRelationshipService.updateNxpBindRelationship(nxpBindRelationship));
    }

    /**
     * 删除绑定关系管理
     */
    @RequiresPermissions("system:bindRelationship:remove")
    @Log(title = "绑定关系管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpBindRelationshipService.deleteNxpBindRelationshipByIds(ids));
    }

    /**
     * 获取维修内容
     * @param nxpInspectionContent
     * @return
     */
    @PostMapping("/getContent")
    @ResponseBody
    public AjaxResult getContent(NxpInspectionContent nxpInspectionContent){
        // 只查询已启用的
        nxpInspectionContent.setStatus(1);
        List<NxpInspectionContent> nxpInspectionContents = nxpInspectionContentService.selectNxpInspectionContentListNew(nxpInspectionContent);
        return AjaxResult.success(nxpInspectionContents);
    }

    /**
     * 获取pm检查项
     * @param nxpInspectionPmGroupItem
     * @return
     */
    @PostMapping("/getPmGroupItems")
    @ResponseBody
    public AjaxResult getPmGroupItems(NxpInspectionPmGroupItem nxpInspectionPmGroupItem){
        // 只查询已启用的
        nxpInspectionPmGroupItem.setItem_status(1);
        List<NxpInspectionPmGroupItem> groupItems = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemList(nxpInspectionPmGroupItem);
        return AjaxResult.success(groupItems);
    }
}
