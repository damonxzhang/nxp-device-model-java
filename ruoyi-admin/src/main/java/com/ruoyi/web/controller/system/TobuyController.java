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
import com.ruoyi.system.domain.Tobuy;
import com.ruoyi.system.service.ITobuyService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 史工输出表Controller
 * 
 * @author d
 * @date 2021-05-21
 */
@Controller
@RequestMapping("/system/sparepartToBuy")
public class TobuyController extends BaseController
{
    private String prefix = "system/sparepartToBuy";

    @Autowired
    private ITobuyService tobuyService;

    @RequiresPermissions("system:sparepartToBuy:view")
    @GetMapping()
    public String sparepartToBuy()
    {
        return prefix + "/sparepartToBuy";
    }

    /**
     * 查询史工输出表列表
     */
    @RequiresPermissions("system:sparepartToBuy:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Tobuy tobuy)
    {
        startPage();
        List<Tobuy> list = tobuyService.selectTobuyList(tobuy);
        return getDataTable(list);
    }

    /**
     * 导出史工输出表列表
     */
    @RequiresPermissions("system:sparepartToBuy:export")
    @Log(title = "史工输出表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Tobuy tobuy)
    {
        List<Tobuy> list = tobuyService.selectTobuyList(tobuy);
        ExcelUtil<Tobuy> util = new ExcelUtil<Tobuy>(Tobuy.class);
        return util.exportExcel(list, "sparepartToBuy");
    }

    /**
     * 新增史工输出表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存史工输出表
     */
    @RequiresPermissions("system:sparepartToBuy:add")
    @Log(title = "史工输出表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Tobuy tobuy)
    {
        return toAjax(tobuyService.insertTobuy(tobuy));
    }

    /**
     * 修改史工输出表
     */
    @GetMapping("/edit/{partID}")
    public String edit(@PathVariable("partID") String partID, ModelMap mmap)
    {
        Tobuy tobuy = tobuyService.selectTobuyById(partID);
        mmap.put("tobuy", tobuy);
        return prefix + "/edit";
    }

    /**
     * 修改保存史工输出表
     */
    @RequiresPermissions("system:sparepartToBuy:edit")
    @Log(title = "史工输出表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Tobuy tobuy)
    {
        return toAjax(tobuyService.updateTobuy(tobuy));
    }

    /**
     * 删除史工输出表
     */
    @RequiresPermissions("system:sparepartToBuy:remove")
    @Log(title = "史工输出表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tobuyService.deleteTobuyByIds(ids));
    }
}
