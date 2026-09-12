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
import com.ruoyi.system.domain.NxpAppMessage;
import com.ruoyi.system.service.INxpAppMessageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * app消息Controller
 * 
 * @author YanCheng Liu
 * @date 2021-02-07
 */
@Controller
@RequestMapping("/system/appMessage")
public class NxpAppMessageController extends BaseController
{
    private String prefix = "system/appMessage";

    @Autowired
    private INxpAppMessageService nxpAppMessageService;

    @RequiresPermissions("system:appMessage:view")
    @GetMapping()
    public String appMessage()
    {
        return prefix + "/appMessage";
    }

    /**
     * 查询app消息列表
     */
    @RequiresPermissions("system:appMessage:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpAppMessage nxpAppMessage)
    {
        startPage();
        List<NxpAppMessage> list = nxpAppMessageService.selectNxpAppMessageList(nxpAppMessage);
        return getDataTable(list);
    }

    /**
     * 导出app消息列表
     */
    @RequiresPermissions("system:appMessage:export")
    @Log(title = "app消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpAppMessage nxpAppMessage)
    {
        List<NxpAppMessage> list = nxpAppMessageService.selectNxpAppMessageList(nxpAppMessage);
        ExcelUtil<NxpAppMessage> util = new ExcelUtil<NxpAppMessage>(NxpAppMessage.class);
        return util.exportExcel(list, "appMessage");
    }

    /**
     * 新增app消息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存app消息
     */
    @RequiresPermissions("system:appMessage:add")
    @Log(title = "app消息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpAppMessage nxpAppMessage)
    {
        return toAjax(nxpAppMessageService.insertNxpAppMessage(nxpAppMessage));
    }

    /**
     * 修改app消息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpAppMessage nxpAppMessage = nxpAppMessageService.selectNxpAppMessageById(id);
        mmap.put("nxpAppMessage", nxpAppMessage);
        return prefix + "/edit";
    }

    /**
     * 修改保存app消息
     */
    @RequiresPermissions("system:appMessage:edit")
    @Log(title = "app消息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpAppMessage nxpAppMessage)
    {
        return toAjax(nxpAppMessageService.updateNxpAppMessage(nxpAppMessage));
    }

    /**
     * 删除app消息
     */
    @RequiresPermissions("system:appMessage:remove")
    @Log(title = "app消息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpAppMessageService.deleteNxpAppMessageByIds(ids));
    }
}
