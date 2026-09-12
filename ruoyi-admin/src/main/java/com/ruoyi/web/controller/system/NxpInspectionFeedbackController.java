package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.NxpUserInfo;
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
import com.ruoyi.system.domain.NxpInspectionFeedback;
import com.ruoyi.system.service.INxpInspectionFeedbackService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * APP反馈Controller
 * 
 * @author DamonZhang
 * @date 2021-04-28
 */
@Controller
@RequestMapping("/system/feedback")
public class NxpInspectionFeedbackController extends BaseController
{
    private String prefix = "system/feedback";

    @Autowired
    private INxpInspectionFeedbackService nxpInspectionFeedbackService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    @RequiresPermissions("system:feedback:view")
    @GetMapping()
    public String feedback()
    {

        return prefix + "/feedback";
    }

    /**
     * 查询APP反馈列表
     */
    @RequiresPermissions("system:feedback:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NxpInspectionFeedback nxpInspectionFeedback)
    {
        startPage();
//        List<NxpInspectionFeedback> list = nxpInspectionFeedbackService.selectNxpInspectionFeedbackList(nxpInspectionFeedback);
//        NxpInspectionFeedback nxpInspectionFeedback = new NxpInspectionFeedback();
        List<NxpInspectionFeedback> nxpInspectionFeedbacks = nxpInspectionFeedbackService.selectNxpInspectionFeedbackList(nxpInspectionFeedback);

        for (NxpInspectionFeedback inspectionFeedback : nxpInspectionFeedbacks) {
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(inspectionFeedback.getUserId());
            inspectionFeedback.setUserName(nxpUserInfo.getChinese_name());
            inspectionFeedback.setUserPhone(nxpUserInfo.getTel());
            switch (nxpUserInfo.getShift()){
                case 1:
                    inspectionFeedback.setUserShift("A班");
                    break;
                case 2:
                    inspectionFeedback.setUserShift("B班");
                    break;
                case 3:
                    inspectionFeedback.setUserShift("C班");
                    break;
                case 4:
                    inspectionFeedback.setUserShift("D班");
                    break;
                case 5:
                    inspectionFeedback.setUserShift("R班");
                    break;
                case 6:
                    inspectionFeedback.setUserShift("AB班");
                    break;
                case 7:
                    inspectionFeedback.setUserShift("CD班");
                    break;
                default:
                    inspectionFeedback.setUserShift("other");
                    break;
            }
        }

        return getDataTable(nxpInspectionFeedbacks);
    }

    /**
     * 导出APP反馈列表
     */
    @RequiresPermissions("system:feedback:export")
    @Log(title = "APP反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NxpInspectionFeedback nxpInspectionFeedback)
    {
        List<NxpInspectionFeedback> list = nxpInspectionFeedbackService.selectNxpInspectionFeedbackList(nxpInspectionFeedback);
        ExcelUtil<NxpInspectionFeedback> util = new ExcelUtil<NxpInspectionFeedback>(NxpInspectionFeedback.class);
        return util.exportExcel(list, "feedback");
    }

    /**
     * 新增APP反馈
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存APP反馈
     */
    @RequiresPermissions("system:feedback:add")
    @Log(title = "APP反馈", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NxpInspectionFeedback nxpInspectionFeedback)
    {
        return toAjax(nxpInspectionFeedbackService.insertNxpInspectionFeedback(nxpInspectionFeedback));
    }

    /**
     * 修改APP反馈
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NxpInspectionFeedback nxpInspectionFeedback = nxpInspectionFeedbackService.selectNxpInspectionFeedbackById(id);
        mmap.put("nxpInspectionFeedback", nxpInspectionFeedback);
        return prefix + "/edit";
    }

    /**
     * 修改保存APP反馈
     */
    @RequiresPermissions("system:feedback:edit")
    @Log(title = "APP反馈", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NxpInspectionFeedback nxpInspectionFeedback)
    {
        return toAjax(nxpInspectionFeedbackService.updateNxpInspectionFeedback(nxpInspectionFeedback));
    }

    /**
     * 删除APP反馈
     */
    @RequiresPermissions("system:feedback:remove")
    @Log(title = "APP反馈", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(nxpInspectionFeedbackService.deleteNxpInspectionFeedbackByIds(ids));
    }
}
