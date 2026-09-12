package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.service.INxpUserInfoService;
import org.apache.commons.collections.CollectionUtils;
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
import com.ruoyi.system.domain.NxpUserLabel;
import com.ruoyi.system.service.INxpUserLabelService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户权限标签Controller
 * 
 * @author YanCheng Liu
 * @date 2023-08-13
 */
@Controller
@RequestMapping("/system/nxpUserLabel")
public class NxpUserLabelController extends BaseController
{
    private String prefix = "system/nxpUserLabel";

    @Autowired
    private INxpUserLabelService nxpUserLabelService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:nxpUserLabel:view")
    @GetMapping()
    public String nxpUserLabel()
    {
        return prefix + "/nxpUserLabel";
    }

    /**
     * 查询用户权限标签列表
     */
    @RequiresPermissions("system:nxpUserLabel:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpUserLabel nxpUserLabel)
    {
        startPage();
        List<NxpUserLabel> list = nxpUserLabelService.selectNxpUserLabelList(nxpUserLabel);
        return getDataTable(list);
    }

    /**
     * 导出用户权限标签列表
     */
    @RequiresPermissions("system:nxpUserLabel:export")
    @Log(title = "用户权限标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpUserLabel nxpUserLabel)
    {
        List<NxpUserLabel> list = nxpUserLabelService.selectNxpUserLabelList(nxpUserLabel);
        ExcelUtil<NxpUserLabel> util = new ExcelUtil<NxpUserLabel>(NxpUserLabel.class);
        return util.exportExcel(list, "nxpUserLabel");
    }

    /**
     * 新增用户权限标签
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        // 查询所有已启用的人员
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        // 查询当前已配置的人
        List<NxpUserInfo> userInfos = new ArrayList<>();
        List<NxpUserLabel> nxpUserLabels = nxpUserLabelService.selectNxpUserLabelList(new NxpUserLabel());
        for (NxpUserLabel nxpUserLabel : nxpUserLabels) {
            NxpUserInfo nxpUserInfo1 = nxpUserInfoService.selectNxpUserInfoById(nxpUserLabel.getUserInfoId());
            userInfos.add(nxpUserInfo1);
        }
        if (userInfos.size() > 0){
            Collection subtract = CollectionUtils.subtract(nxpUserInfos, userInfos);
            List<NxpUserInfo> list = Lists.newArrayList(subtract);
            mmap.put("userInfos",list);
        }else {
            mmap.put("userInfos",nxpUserInfos);
        }
        return prefix + "/add";
    }

    /**
     * 新增保存用户权限标签
     */
    @RequiresPermissions("system:nxpUserLabel:add")
    @Log(title = "用户权限标签", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpUserLabel nxpUserLabel)
    {
        if (nxpUserLabel.getUserInfoId() != null){
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpUserLabel.getUserInfoId());
            if (nxpUserInfo != null){
                nxpUserLabel.setChineseName(nxpUserInfo.getChinese_name());
            }
        }
        return toAjax(nxpUserLabelService.insertNxpUserLabel(nxpUserLabel));
    }

    /**
     * 修改用户权限标签
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelById(id);
        mmap.put("nxpUserLabel", nxpUserLabel);
        // 查询所有已启用的人员
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setStatus(1);
        List<NxpUserInfo> nxpUserInfos = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        mmap.put("userInfos",nxpUserInfos);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户权限标签
     */
    @RequiresPermissions("system:nxpUserLabel:edit")
    @Log(title = "用户权限标签", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpUserLabel nxpUserLabel)
    {
        if (nxpUserLabel.getUserInfoId() != null){
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(nxpUserLabel.getUserInfoId());
            if (nxpUserInfo != null){
                nxpUserLabel.setChineseName(nxpUserInfo.getChinese_name());
            }
        }
        return toAjax(nxpUserLabelService.updateNxpUserLabel(nxpUserLabel));
    }

    /**
     * 删除用户权限标签
     */
    @RequiresPermissions("system:nxpUserLabel:remove")
    @Log(title = "用户权限标签", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpUserLabelService.deleteNxpUserLabelByIds(ids));
    }
}
