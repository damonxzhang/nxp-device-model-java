package com.ruoyi.web.controller.system;

import java.util.List;
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
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.service.INxpUserInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 * 
 * @author YanCheng Liu
 * @date 2021-02-05
 */
@Controller
@RequestMapping("/system/nxpUserInfo")
public class NxpUserInfoController extends BaseController
{
    private String prefix = "system/nxpUserInfo";

    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:nxpUserInfo:view")
    @GetMapping()
    public String nxpUserInfo()
    {
        return prefix + "/nxpUserInfo";
    }

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("system:nxpUserInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpUserInfo nxpUserInfo)
    {
        startPage();
        List<NxpUserInfo> list = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @RequiresPermissions("system:nxpUserInfo:export")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpUserInfo nxpUserInfo)
    {
        List<NxpUserInfo> list = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        ExcelUtil<NxpUserInfo> util = new ExcelUtil<NxpUserInfo>(NxpUserInfo.class);
        return util.exportExcel(list, "nxpUserInfo");
    }

    /**
     * 新增用户信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户信息
     */
    @RequiresPermissions("system:nxpUserInfo:add")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpUserInfo nxpUserInfo)
    {
        return toAjax(nxpUserInfoService.insertNxpUserInfo(nxpUserInfo));
    }

    /**
     * 修改用户信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(id);
        mmap.put("nxpUserInfo", nxpUserInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户信息
     */
    @RequiresPermissions("system:nxpUserInfo:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpUserInfo nxpUserInfo)
    {
        return toAjax(nxpUserInfoService.updateNxpUserInfo(nxpUserInfo));
    }

    /**
     * 删除用户信息
     */
    @RequiresPermissions("system:nxpUserInfo:remove")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpUserInfoService.deleteNxpUserInfoByIds(ids));
    }
}
