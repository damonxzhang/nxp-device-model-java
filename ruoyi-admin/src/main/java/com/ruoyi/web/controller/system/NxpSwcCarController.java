package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.domain.NxpTeam;
import com.ruoyi.system.service.INxpTeamService;
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
import com.ruoyi.system.domain.NxpSwcCar;
import com.ruoyi.system.service.INxpSwcCarService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * CAR扣分Controller
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/system/car")
public class NxpSwcCarController extends BaseController
{
    private String prefix = "system/car";

    @Autowired
    private INxpSwcCarService nxpSwcCarService;
    @Autowired
    private INxpTeamService nxpTeamService;

    @RequiresPermissions("system:car:view")
    @GetMapping()
    public String car(ModelMap mmap)
    {
        List<NxpTeam> nxpTeams = nxpTeamService.selectNxpTeamList(new NxpTeam());
        mmap.put("teams",nxpTeams);
        return prefix + "/car";
    }

    /**
     * 查询CAR扣分列表
     */
    @RequiresPermissions("system:car:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpSwcCar nxpSwcCar)
    {
        startPage();
        List<NxpSwcCar> list = nxpSwcCarService.selectNxpSwcCarList(nxpSwcCar);
        return getDataTable(list);
    }

    /**
     * 导出CAR扣分列表
     */
    @RequiresPermissions("system:car:export")
    @Log(title = "CAR扣分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpSwcCar nxpSwcCar)
    {
        List<NxpSwcCar> list = nxpSwcCarService.selectNxpSwcCarList(nxpSwcCar);
        ExcelUtil<NxpSwcCar> util = new ExcelUtil<NxpSwcCar>(NxpSwcCar.class);
        return util.exportExcel(list, "car");
    }

    /**
     * 新增CAR扣分
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<NxpTeam> nxpTeams = nxpTeamService.selectNxpTeamList(new NxpTeam());
        mmap.put("teams",nxpTeams);
        return prefix + "/add";
    }

    /**
     * 新增保存CAR扣分
     */
    @RequiresPermissions("system:car:add")
    @Log(title = "CAR扣分", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpSwcCar nxpSwcCar)
    {
        return toAjax(nxpSwcCarService.insertNxpSwcCar(nxpSwcCar));
    }

    /**
     * 修改CAR扣分
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpSwcCar nxpSwcCar = nxpSwcCarService.selectNxpSwcCarById(id);
        mmap.put("nxpSwcCar", nxpSwcCar);
        return prefix + "/edit";
    }

    /**
     * 修改保存CAR扣分
     */
    @RequiresPermissions("system:car:edit")
    @Log(title = "CAR扣分", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpSwcCar nxpSwcCar)
    {
        return toAjax(nxpSwcCarService.updateNxpSwcCar(nxpSwcCar));
    }

    /**
     * 删除CAR扣分
     */
    @RequiresPermissions("system:car:remove")
    @Log(title = "CAR扣分", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpSwcCarService.deleteNxpSwcCarByIds(ids));
    }
}
