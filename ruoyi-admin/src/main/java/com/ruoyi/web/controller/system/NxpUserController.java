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
import com.ruoyi.system.domain.NxpUser;
import com.ruoyi.system.service.INxpUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户登录Controller
 * 
 * @author YanCheng Liu
 * @date 2021-03-09
 */
@Controller
@RequestMapping("/system/nxpUser")
public class NxpUserController extends BaseController
{
    private String prefix = "system/nxpUser";

    @Autowired
    private INxpUserService nxpUserService;

    @RequiresPermissions("system:nxpUser:view")
    @GetMapping()
    public String nxpUser()
    {
        return prefix + "/nxpUser";
    }

    /**
     * 查询用户登录列表
     */
    @RequiresPermissions("system:nxpUser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpUser nxpUser)
    {
        startPage();
        List<NxpUser> list = nxpUserService.selectNxpUserList(nxpUser);
        return getDataTable(list);
    }

    /**
     * 导出用户登录列表
     */
    @RequiresPermissions("system:nxpUser:export")
    @Log(title = "用户登录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpUser nxpUser)
    {
        List<NxpUser> list = nxpUserService.selectNxpUserList(nxpUser);
        ExcelUtil<NxpUser> util = new ExcelUtil<NxpUser>(NxpUser.class);
        return util.exportExcel(list, "nxpUser");
    }

    /**
     * 新增用户登录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户登录
     */
    @RequiresPermissions("system:nxpUser:add")
    @Log(title = "用户登录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpUser nxpUser)
    {
        return toAjax(nxpUserService.insertNxpUser(nxpUser));
    }

    /**
     * 修改用户登录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpUser nxpUser = nxpUserService.selectNxpUserById(id);
        mmap.put("nxpUser", nxpUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户登录
     */
    @RequiresPermissions("system:nxpUser:edit")
    @Log(title = "用户登录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpUser nxpUser)
    {
        return toAjax(nxpUserService.updateNxpUser(nxpUser));
    }

    /**
     * 删除用户登录
     */
    @RequiresPermissions("system:nxpUser:remove")
    @Log(title = "用户登录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpUserService.deleteNxpUserByIds(ids));
    }
}
