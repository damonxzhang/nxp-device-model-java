package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.service.INxpFacilityService;
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
import com.ruoyi.system.domain.NxpSwcCheckSelf;
import com.ruoyi.system.service.INxpSwcCheckSelfService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * SWC自查问题Controller
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/system/swcCheckSelf")
public class NxpSwcCheckSelfController extends BaseController
{
    private String prefix = "system/swcCheckSelf";

    @Autowired
    private INxpSwcCheckSelfService nxpSwcCheckSelfService;
    @Autowired
    private INxpFacilityService nxpFacilityService;

    @RequiresPermissions("system:swcCheckSelf:view")
    @GetMapping()
    public String swcCheckSelf()
    {

        return prefix + "/swcCheckSelf";
    }

    /**
     * 查询SWC自查问题列表
     */
    @RequiresPermissions("system:swcCheckSelf:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpSwcCheckSelf nxpSwcCheckSelf)
    {
        startPage();
        List<NxpSwcCheckSelf> list = nxpSwcCheckSelfService.selectNxpSwcCheckSelfList(nxpSwcCheckSelf);
        return getDataTable(list);
    }

    /**
     * 导出SWC自查问题列表
     */
    @RequiresPermissions("system:swcCheckSelf:export")
    @Log(title = "SWC自查问题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpSwcCheckSelf nxpSwcCheckSelf)
    {
        List<NxpSwcCheckSelf> list = nxpSwcCheckSelfService.selectNxpSwcCheckSelfList(nxpSwcCheckSelf);
        ExcelUtil<NxpSwcCheckSelf> util = new ExcelUtil<NxpSwcCheckSelf>(NxpSwcCheckSelf.class);
        return util.exportExcel(list, "swcCheckSelf");
    }

    /**
     * 新增SWC自查问题
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        mmap.put("facilitiesList", nxpFacilities);
        return prefix + "/add";
    }

    /**
     * 新增保存SWC自查问题
     */
    @RequiresPermissions("system:swcCheckSelf:add")
    @Log(title = "SWC自查问题", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpSwcCheckSelf nxpSwcCheckSelf)
    {
        return toAjax(nxpSwcCheckSelfService.insertNxpSwcCheckSelf(nxpSwcCheckSelf));
    }

    /**
     * 修改SWC自查问题
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpSwcCheckSelf nxpSwcCheckSelf = nxpSwcCheckSelfService.selectNxpSwcCheckSelfById(id);
        mmap.put("nxpSwcCheckSelf", nxpSwcCheckSelf);
        NxpFacility nxpFacility = new NxpFacility();
        nxpFacility.setStatus("1");
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        String facilityId = nxpSwcCheckSelf.getFacilityId();
        List<String> list = new ArrayList<>();
        if (facilityId.indexOf(",") != -1){
            // 多个
            String[] split = facilityId.split(",");
            list = Arrays.asList(split);
        }else {
            list.add(facilityId);
        }
        // 对比
        for (NxpFacility facility : nxpFacilities) {
            for (String s : list) {
                if (facility.getId().toString().equals(s)){
                    facility.setFlag(true);
                    break;
                }
            }
        }
        mmap.put("facilitiesList", nxpFacilities);
        return prefix + "/edit";
    }

    /**
     * 修改保存SWC自查问题
     */
    @RequiresPermissions("system:swcCheckSelf:edit")
    @Log(title = "SWC自查问题", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpSwcCheckSelf nxpSwcCheckSelf)
    {
        return toAjax(nxpSwcCheckSelfService.updateNxpSwcCheckSelf(nxpSwcCheckSelf));
    }

    /**
     * 删除SWC自查问题
     */
    @RequiresPermissions("system:swcCheckSelf:remove")
    @Log(title = "SWC自查问题", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpSwcCheckSelfService.deleteNxpSwcCheckSelfByIds(ids));
    }
}
