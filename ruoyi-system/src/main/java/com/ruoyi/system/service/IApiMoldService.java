package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.NxpMaintainedCheckDTO;
import com.ruoyi.system.domain.VO.*;

import java.util.List;


/**
 * @author snkj
 * @create 2023-06-26 13:39
 */
public interface IApiMoldService {
    /**
     * 单独更换模具申请
     * @param nxpFacility
     * @return
     */
    public AjaxResult replaceSeparatelyMoldApply(NxpFacility nxpFacility);

    /**
     * 单独更换模具申请提交
     * @param replacedList
     * @return
     */
    AjaxResult submit(ReplacedList replacedList);

    /**
     * 通过盒子编号查询位置以及密码等
     * @param nxpMold
     * @return
     */
    AjaxResult selectByBoxCode(NxpMold nxpMold);

    /**
     * 领取空盒提交
     * @param replacedList
     * @return
     */
    AjaxResult claimEmptyBoxData(ReplacedList replacedList);

    /**
     * 归还模具盒
     * @param replacedList
     * @return
     */
    AjaxResult returnClaimBox(ReplacedList replacedList);

    /**
     * 部件标记异常
     * @param nxpMoldComponent
     * @return
     */
    AjaxResult markException(NxpMoldComponent nxpMoldComponent);

    /**
     * 待更换列表
     * @param replacedListVO
     * @return
     */
    List<ReplacedList> replacedList(ReplacedListVO replacedListVO,NxpUserLabel nxpUserLabel);

    /**
     * 维修更换模具按钮触发接口
     * @param inspectionReplaceMoldVO
     * @return
     */
    AjaxResult inspectionReplaceMoldButton(InspectionReplaceMoldVO inspectionReplaceMoldVO);

    /**
     * 根据模具编号查询模具部件
     * @param nxpMoldComponent
     * @return
     */
    AjaxResult selectComponentByMoldCode(NxpMoldComponent nxpMoldComponent);

    /**
     * 维修更换模具确认类型下一步接口
     * @param confirmTypeNextStepVO
     * @return
     */
    AjaxResult confirmTypeNextStep(ConfirmTypeNextStepVO confirmTypeNextStepVO);

    /**
     * 维修更换模具领取部件提交接口
     * @param receiveComponentSubmitVO
     * @return
     */
    AjaxResult receiveComponentSubmit(ReceiveComponentSubmitVO receiveComponentSubmitVO);

    /**
     * 领取模具
     * @param receiveComponentSubmitVO
     * @return
     */
    AjaxResult receiveMoldBox(ReceiveComponentSubmitVO receiveComponentSubmitVO);

    /**
     * 待归还列表
     * @param nxpMoldReturn
     * @return
     */
    List<NxpMoldReturn> returnList(NxpMoldReturn nxpMoldReturn);

    /**
     * 模具归还提交按钮
     * @param nxpMoldReturn
     * @return
     */
    AjaxResult moldReturnSubmit(NxpMoldReturn nxpMoldReturn);

    /**
     * 根据模具和模具盒编号查询模具
     * @param nxpMoldMaintenance
     * @return
     */
    AjaxResult moldMaintenanceSearch(NxpMoldMaintenance nxpMoldMaintenance);

    /**
     * 模具维护-开启维护检查
     * @param nxpMoldMaintenance
     * @return
     */
    AjaxResult beginMoldMaintenanceCheck(NxpMoldMaintenance nxpMoldMaintenance);

    /**
     * 待维护列表
     * @param nxpMoldMaintenanceVO
     * @return
     */
    List<NxpMoldMaintenance> maintainedList(NxpMoldMaintenanceVO nxpMoldMaintenanceVO);

    /**
     * 维护上传图片
     * @param picture
     * @return
     */
    AjaxResult uploadFile(Picture picture);

    /**
     * 维护提交按钮接口
     * @param nxpMaintainedCheckDTO
     * @return
     */
    AjaxResult maintainedCheck(NxpMaintainedCheckDTO nxpMaintainedCheckDTO);

    /**
     * 模具列表
     * @param nxpMold
     * @return
     */
    List<NxpMold> moldList(NxpMold nxpMold);

    /**
     * 模具详情
     * @param nxpMold
     * @return
     */
    AjaxResult moldDetails(NxpMold nxpMold);

    /**
     * 模具详情提交
     * @param receiveComponentSubmitVO
     * @return
     */
    AjaxResult moldDetailsSubmit(ReceiveComponentSubmitVO receiveComponentSubmitVO);

    /**
     * 模具列表筛选项下拉数据
     * @return
     */
    AjaxResult searchData();

    /**
     * 开锁
     * @param nxpMold
     * @return
     */
    AjaxResult openLockOrPwd(NxpMold nxpMold);

    /**
     * 待更换列表删除
     * @param replacedList
     * @return
     */
    AjaxResult replacedDel(ReplacedList replacedList);

    /**
     * 模具按钮右上角数字
     * @param moldNumbersVO
     * @return
     */
    AjaxResult moldNumbers(MoldNumbersVO moldNumbersVO);

    AjaxResult uploadNxpComponent();
}
