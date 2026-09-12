package com.ruoyi.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.quartz.task.PhpMsgTask;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.NxpMaintainedCheckDTO;
import com.ruoyi.system.domain.VO.*;
import com.ruoyi.system.service.IApiMoldService;
import com.ruoyi.system.service.INxpUserLabelService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ITcpConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 模具控制器
 * @author snkj
 * @create 2023-06-26 13:38
 */
@RestController
@RequestMapping("/api/mold")
public class ApiMoldController {

    private static final Logger log = LoggerFactory.getLogger(ApiMoldController.class);

    @Resource
    private IApiMoldService apiMoldService;
    @Resource
    private INxpUserLabelService nxpUserLabelService;
    @Resource
    private ITcpConnectService tcpConnectService;
    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 单独更换模具申请
     * @param nxpFacility
     * @return
     */
    @PostMapping("/replaceSeparatelyMoldApply")
    public AjaxResult replaceSeparatelyMoldApply(@RequestBody NxpFacility nxpFacility){
        return apiMoldService.replaceSeparatelyMoldApply(nxpFacility);
    }

    /**
     * 单独更换模具申请提交
     * @param replacedList
     * @return
     */
    @PostMapping("/submit")
    public AjaxResult submit(@RequestBody ReplacedList replacedList){
        return apiMoldService.submit(replacedList);
    }

    /**
     * 领取空盒提交
     * @param replacedList
     * @return
     */
    @PostMapping("/claimEmptyBoxData")
    public AjaxResult claimEmptyBoxData(@RequestBody ReplacedList replacedList){
        return apiMoldService.claimEmptyBoxData(replacedList);
    }

    /**
     * 通过盒子编号查询位置以及密码等
     * @param nxpMold
     * @return
     */
    @PostMapping("/selectByBoxCode")
    public AjaxResult selectByBoxCode(@RequestBody NxpMold nxpMold){
        return apiMoldService.selectByBoxCode(nxpMold);
    }

    /**
     * 归还模具盒
     * @param replacedList
     * @return
     */
    @PostMapping("/returnClaimBox")
    public AjaxResult returnClaimBox(@RequestBody ReplacedList replacedList){
        return apiMoldService.returnClaimBox(replacedList);
    }

    /**
     * 部件标记异常
     * @param nxpMoldComponent
     * @return
     */
    @PostMapping("/markException")
    public AjaxResult markException(@RequestBody NxpMoldComponent nxpMoldComponent){
        return apiMoldService.markException(nxpMoldComponent);
    }

    /**
     * 领取模具
     * @param receiveComponentSubmitVO
     * @return
     */
    @PostMapping("/receiveMoldBox")
    public AjaxResult receiveMoldBox(@RequestBody ReceiveComponentSubmitVO receiveComponentSubmitVO){
        return apiMoldService.receiveMoldBox(receiveComponentSubmitVO);
    }

    /**
     * 待更换列表
     * @param replacedListVO
     * @return
     */
    @PostMapping("/replacedList")
    public AjaxResult replacedList(@RequestBody ReplacedListVO replacedListVO){
        if (replacedListVO.getUserId() == null){
            return AjaxResult.error("用户id不能为空");
        }
        NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelByUserInfoId(replacedListVO.getUserId().intValue());
        if (nxpUserLabel == null){
            // 如果不在权限标签表中的人，只看自己提交的
            replacedListVO.setUserInfoId(replacedListVO.getUserId().intValue());
        }else {
            // 在权限标签表中的人，根据模具类型，设备表中的是否是pm更换还是作业员和pm都可更换查询
            replacedListVO.setMoldType(nxpUserLabel.getMoldType());
            // ba模具灵活配置操作员和pm查看的机型，这里继续沿用process，不想改字段名了，实际是machineModel这个字段
            if (nxpUserLabel.getMoldType() == 2){
                List<String> processList = new ArrayList<>();
                if (nxpUserLabel.getPosition() == 1){
                    // 操作员
                    String baOperatorProcess = sysConfigService.selectConfigByKey("ba_operator_process");
                    if (baOperatorProcess.contains(",")){
                        String[] split = baOperatorProcess.split(",");
                        List<String> list = Arrays.asList(split);
                        processList.addAll(list);
                    }else {
                        processList.add(baOperatorProcess);
                    }
                }
                if (nxpUserLabel.getPosition() == 2){
                    // PM
                    String baPmProcess = sysConfigService.selectConfigByKey("ba_pm_process");
                    if (baPmProcess.contains(",")){
                        String[] split = baPmProcess.split(",");
                        List<String> list = Arrays.asList(split);
                        processList.addAll(list);
                    }else {
                        processList.add(baPmProcess);
                    }
                }
                replacedListVO.setProcessList(processList);
            }
            if (nxpUserLabel.getIsPm() == 2){
                // 操作员
                replacedListVO.setIsReplace(nxpUserLabel.getIsPm());
            }
        }
        PageHelper.startPage(replacedListVO.getPageNum(), replacedListVO.getPageSize());
        return AjaxResult.success(new PageInfo<ReplacedList>(apiMoldService.replacedList(replacedListVO,nxpUserLabel)));
    }

    /**
     * 维修更换模具按钮触发接口
     * @param inspectionReplaceMoldVO
     * @return
     */
    @PostMapping("/inspectionReplaceMoldButton")
    public AjaxResult inspectionReplaceMoldButton(@RequestBody InspectionReplaceMoldVO inspectionReplaceMoldVO){
        return apiMoldService.inspectionReplaceMoldButton(inspectionReplaceMoldVO);
    }

    /**
     * 根据模具编号查询模具部件
     * @param nxpMoldComponent
     * @return
     */
    @PostMapping("/selectComponentByMoldCode")
    public AjaxResult selectComponentByMoldCode(@RequestBody NxpMoldComponent nxpMoldComponent){
        return apiMoldService.selectComponentByMoldCode(nxpMoldComponent);
    }

    /**
     * 维修更换模具确认类型下一步接口
     * @param confirmTypeNextStepVO
     * @return
     */
    @PostMapping("/confirmTypeNextStep")
    public AjaxResult confirmTypeNextStep(@RequestBody ConfirmTypeNextStepVO confirmTypeNextStepVO){
        return apiMoldService.confirmTypeNextStep(confirmTypeNextStepVO);
    }

    /**
     * 维修更换模具领取部件提交接口
     * @param receiveComponentSubmitVO
     * @return
     */
    @PostMapping("/receiveComponentSubmit")
    public AjaxResult receiveComponentSubmit(@RequestBody ReceiveComponentSubmitVO receiveComponentSubmitVO){
        return apiMoldService.receiveComponentSubmit(receiveComponentSubmitVO);
    }

    /**
     * 待归还列表
     * @param nxpMoldReturn
     * @return
     */
    @PostMapping("/returnList")
    public AjaxResult returnList(@RequestBody NxpMoldReturn nxpMoldReturn){
        if (nxpMoldReturn.getUserInfoId() == null){
            return AjaxResult.error("用户id不能为空");
        }
        NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelByUserInfoId(nxpMoldReturn.getUserInfoId().intValue());
        if (nxpUserLabel != null){
            nxpMoldReturn.setMoldType(nxpUserLabel.getMoldType());
            PageHelper.startPage(nxpMoldReturn.getPageNum(), nxpMoldReturn.getPageSize());
            return AjaxResult.success(new PageInfo<NxpMoldReturn>(apiMoldService.returnList(nxpMoldReturn)));
        }else {
            return AjaxResult.success(new PageInfo<NxpMoldReturn>(new ArrayList<>()));
        }
    }

    /**
     * 模具归还提交按钮
     * @param nxpMoldReturn
     * @return
     */
    @PostMapping("/moldReturnSubmit")
    public AjaxResult moldReturnSubmit(@RequestBody NxpMoldReturn nxpMoldReturn){
        return apiMoldService.moldReturnSubmit(nxpMoldReturn);
    }

    /**
     * 模具维护-根据模具和模具盒编号查询模具
     * @param nxpMoldMaintenance
     * @return
     */
    @PostMapping("/moldMaintenanceSearch")
    public AjaxResult moldMaintenanceSearch(@RequestBody NxpMoldMaintenance nxpMoldMaintenance){
        return apiMoldService.moldMaintenanceSearch(nxpMoldMaintenance);
    }

    /**
     * 模具维护-开启维护检查
     * @param nxpMoldMaintenance
     * @return
     */
    @PostMapping("/beginMoldMaintenanceCheck")
    public AjaxResult beginMoldMaintenanceCheck(@RequestBody NxpMoldMaintenance nxpMoldMaintenance){
        return apiMoldService.beginMoldMaintenanceCheck(nxpMoldMaintenance);
    }

    /**
     * 待维护列表
     * @param nxpMoldMaintenanceVO
     * @return
     */
    @PostMapping("/maintainedList")
    public AjaxResult maintainedList(@RequestBody NxpMoldMaintenanceVO nxpMoldMaintenanceVO){
        if (nxpMoldMaintenanceVO.getUserInfoId() == null){
            return AjaxResult.error("用户id不能为空");
        }
        NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelByUserInfoId(nxpMoldMaintenanceVO.getUserInfoId());
        if (nxpUserLabel != null){
            if (nxpUserLabel.getPosition() != 3){
                nxpMoldMaintenanceVO.setMoldType(nxpUserLabel.getMoldType());
            }
            PageHelper.startPage(nxpMoldMaintenanceVO.getPageNum(), nxpMoldMaintenanceVO.getPageSize());
            return AjaxResult.success(new PageInfo<NxpMoldMaintenance>(apiMoldService.maintainedList(nxpMoldMaintenanceVO)));
        }else {
            return AjaxResult.success(new PageInfo<NxpMoldMaintenance>(new ArrayList<>()));
        }
    }

    /**
     * 维护上传图片
     * @param picture
     * @return
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestBody Picture picture){
        log.info("触发维护上传图片接口");
        return apiMoldService.uploadFile(picture);
    }

    /**
     * 维护提交按钮接口
     * @param nxpMaintainedCheckDTO
     * @return
     */
    @PostMapping("/maintainedCheck")
    public AjaxResult maintainedCheck(@RequestBody NxpMaintainedCheckDTO nxpMaintainedCheckDTO){
        log.info("维护提交参数 --> {}", JSON.toJSONString(nxpMaintainedCheckDTO));
        return apiMoldService.maintainedCheck(nxpMaintainedCheckDTO);
    }

    /**
     * 模具列表
     * @param nxpMold
     * @return
     */
    @PostMapping("/moldList")
    public AjaxResult moldList(@RequestBody NxpMold nxpMold){
        if (nxpMold.getUserInfoId() == null){
            return AjaxResult.error("用户id不能为空");
        }
        NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelByUserInfoId(nxpMold.getUserInfoId());
        if (nxpUserLabel == null){
            return AjaxResult.success(new PageInfo<NxpMold>(new ArrayList<>()));
        }else {
            nxpMold.setMoldType(nxpUserLabel.getMoldType());
            PageHelper.startPage(nxpMold.getPageNum(), nxpMold.getPageSize());
            return AjaxResult.success(new PageInfo<NxpMold>(apiMoldService.moldList(nxpMold)));
        }
    }

    /**
     * 模具详情
     * @param nxpMold
     * @return
     */
    @PostMapping("/moldDetails")
    public AjaxResult moldDetails(@RequestBody NxpMold nxpMold){
        return apiMoldService.moldDetails(nxpMold);
    }

    /**
     * 模具详情提交
     * @param receiveComponentSubmitVO
     * @return
     */
    @PostMapping("/moldDetailsSubmit")
    public AjaxResult moldDetailsSubmit(@RequestBody ReceiveComponentSubmitVO receiveComponentSubmitVO){
        return apiMoldService.moldDetailsSubmit(receiveComponentSubmitVO);
    }

    /**
     * 模具列表筛选项下拉数据
     * @return
     */
    @PostMapping("/searchData")
    public AjaxResult searchData(){
        return apiMoldService.searchData();
    }

    /**
     * 开锁
     * @param nxpMold
     * @return
     */
    @PostMapping("/openLockOrPwd")
    public AjaxResult openLockOrPwd(@RequestBody NxpMold nxpMold){
        return apiMoldService.openLockOrPwd(nxpMold);
    }

    /**
     * 开锁测试接口-正式上线时删除
     * @param openLockVO
     * @return
     */
    @PostMapping("/openLockTest")
    public AjaxResult openLockTest(@RequestBody OpenLockVO openLockVO){
        try {
            Map<String, String> result = tcpConnectService.sendOpenInstruct(openLockVO.getIpAddress(), openLockVO.getPort(), openLockVO.getBoxNum());
            return AjaxResult.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("开锁报错 --> {}",e.getMessage());
            return AjaxResult.error();
        }
    }

    /**
     * 待更换列表删除
     * @param replacedList
     * @return
     */
    @PostMapping("/replacedDel")
    public AjaxResult replacedDel(@RequestBody ReplacedList replacedList){
        return apiMoldService.replacedDel(replacedList);
    }

    /**
     * 模具按钮右上角数字
     * @param moldNumbersVO
     * @return
     */
    @PostMapping("/moldNumbers")
    public AjaxResult moldNumbers(@RequestBody MoldNumbersVO moldNumbersVO){
        return apiMoldService.moldNumbers(moldNumbersVO);
    }

    @PostMapping("/updateNxpComponent")
    public AjaxResult updateNxpComponent(){
        return apiMoldService.uploadNxpComponent();
    }
}
