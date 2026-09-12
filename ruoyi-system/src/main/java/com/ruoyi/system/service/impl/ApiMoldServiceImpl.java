package com.ruoyi.system.service.impl;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MultipartFileUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.MoldStateDTO;
import com.ruoyi.system.domain.DTO.NxpMaintainedCheckDTO;
import com.ruoyi.system.domain.VO.*;
import com.ruoyi.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author snkj
 * @create 2023-06-26 13:39
 */
@Service
@Slf4j
public class ApiMoldServiceImpl implements IApiMoldService {

    @Resource
    private INxpFacilityService facilityService;
    @Resource
    private IReplacedListService replacedListService;
    @Resource
    private INxpFacilityProductTypeService nxpFacilityProductTypeService;
    @Resource
    private INxpMoldService nxpMoldService;
    @Resource
    private INxpMoldComponentService nxpMoldComponentService;
    @Resource
    private INxpMachineProcessService nxpMachineProcessService;
    @Resource
    private INxpUserInfoService nxpUserInfoService;
    @Resource
    private INxpProductTypeService nxpProductTypeService;
    @Resource
    private INxpFacilityService nxpFacilityService;
    @Resource
    private INxpMoldReturnService nxpMoldReturnService;
    @Resource
    private INxpMoldComponentAbnormalLogsService nxpMoldComponentAbnormalLogsService;
    @Resource
    private INxpMoldMaintenanceService nxpMoldMaintenanceService;
    @Resource
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;
    @Resource
    private INxpMoldMaintenanceGroupBindService nxpMoldMaintenanceGroupBindService;
    @Resource
    private INxpMoldMaintenanceCheckService nxpMoldMaintenanceCheckService;
    @Resource
    private INxpMoldMaintenanceFileService nxpMoldMaintenanceFileService;
    @Resource
    private ServerConfig serverConfig;
    @Resource
    private INxpSmartCabinetService nxpSmartCabinetService;
    @Resource
    private ITcpConnectService tcpConnectService;
    @Resource
    private INxpMoldMaintenanceGroupItemService nxpMoldMaintenanceGroupItemService;
    @Resource
    private INxpMoldTypeService nxpMoldTypeService;
    @Resource
    private INxpUserLabelService nxpUserLabelService;
    @Resource
    private INxpMachineModelService nxpMachineModelService;
    @Resource
    private INxpFacilityUserService nxpFacilityUserService;
    @Resource
    private INxpAppMessageService nxpAppMessageService;

    /**
     * 单独更换模具申请
     *
     * @param nxpFacility
     * @return
     */
    @Override
    public AjaxResult replaceSeparatelyMoldApply(NxpFacility nxpFacility) {
        // 1. 校验参数
        if (StringUtils.isEmpty(nxpFacility.getNumber())) {
            return AjaxResult.error("设备编号不能为空");
        }
        NxpFacility facility = facilityService.selectByNumber(nxpFacility.getNumber());
        if (facility == null) {
            return AjaxResult.error("设备不存在");
        }

        // 构建返回map
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("facilityNumber", facility.getNumber());
        resultMap.put("processName", facility.getProcessName());

        // 2. 查询当前使用的模具编号
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByFacility(facility.getId());
        if (nxpMold == null) {
            resultMap.put("currentMoldNumber", "");
        } else {
            resultMap.put("currentMoldNumber", nxpMold);
        }

        // 查询通过设备编号查询产品类型，再通过产品类型查询模具
        List<NxpProductType> productTypes = new ArrayList<>();
        NxpFacilityProductType nxpFacilityProductType = nxpFacilityProductTypeService.selectNxpFacilityProductTypeById(facility.getId());
        if (nxpFacilityProductType != null) {
            String productTypeId = nxpFacilityProductType.getProductTypeId();
            if (productTypeId.contains(",")) {
                String[] split = productTypeId.split(",");
                for (String productTypeIdStr : split) {
                    NxpProductType nxpProductType = nxpProductTypeService.selectNxpProductTypeById(Long.valueOf(productTypeIdStr));
                    productTypes.add(nxpProductType);
                }
            } else {
                NxpProductType nxpProductType = nxpProductTypeService.selectNxpProductTypeById(Long.valueOf(productTypeId));
                productTypes.add(nxpProductType);
            }
        }
        resultMap.put("productTypes", productTypes);
        return AjaxResult.success(resultMap);
    }

    /**
     * 单独更换模具申请提交
     *
     * @param replacedList
     * @return
     */
    @Override
    public AjaxResult submit(ReplacedList replacedList) {
        if (StringUtils.isEmpty(replacedList.getFacilityNumber())) {
            return AjaxResult.error("设备编号不能为空");
        }
        if (replacedList.getUserInfoId() == null) {
            return AjaxResult.error("申请人id不能为空");
        }
        if (replacedList.getProductTypeId() == null) {
            return AjaxResult.error("产品类型id不能为空");
        }
        // 查询产品类型名称
        NxpProductType nxpProductType = nxpProductTypeService.selectNxpProductTypeById(replacedList.getProductTypeId());
        if (nxpProductType != null) {
            replacedList.setProductTypeName(nxpProductType.getProductTypeName());
            replacedList.setMoldType(nxpProductType.getMoldType());
        }
        // 查询设备
        NxpFacility nxpFacility = facilityService.selectByNumber(replacedList.getFacilityNumber());
        if (nxpFacility == null) {
            return AjaxResult.error("设备不存在");
        }

        // 一台设备没完成更换，不能申请第二次
        if (StringUtils.isNotEmpty(replacedList.getMoldRmsCode())) {
            NxpMold nxpMold = nxpMoldService.selectNxpMoldByRMSCode(replacedList.getMoldRmsCode());
            ReplacedList params = new ReplacedList();
//            params.setMoldCode(nxpMold.getMoldCode());
//            params.setType(1);
            params.setFacilityNumber(replacedList.getFacilityNumber());
            params.setPageSize(null);
            params.setPageNum(null);
            ReplacedList moldReplacedList = replacedListService.selectByStatus(params);
            if (moldReplacedList != null){
                // 表示该设备有待更换的模具未完成
                return AjaxResult.error("该设备有未完成的模具更换操作");
            }
            /*if (moldReplacedList != null && moldReplacedList.getStatus() != 4) {
                // 表示该设备有待更换的模具未完成
                return AjaxResult.error("该设备有未完成的模具更换操作");
            }*/

            replacedList.setProcessId(nxpFacility.getProcess());
            replacedList.setFacilityId(nxpFacility.getId());
            replacedList.setFacilityNumber(nxpFacility.getNumber());
            replacedList.setMoldId(nxpMold.getId());
            replacedList.setMoldName(nxpMold.getMoldName());
            replacedList.setMoldCode(nxpMold.getMoldCode());
            replacedList.setFacilityId(nxpFacility.getId());
            replacedList.setStatus(1);
            replacedList.setType(1);
            replacedList.setProcessId(nxpFacility.getProcess());
            replacedList.setIsReplace(nxpFacility.getIs_replace());
//            replacedList.setMoldType(nxpMold.getMoldType());
            replacedListService.insertReplacedList(replacedList);
        } else {
            replacedList.setProcessId(nxpFacility.getProcess());
            replacedList.setFacilityId(nxpFacility.getId());
            replacedList.setFacilityNumber(nxpFacility.getNumber());
            replacedList.setFacilityId(nxpFacility.getId());
            replacedList.setStatus(2);
            replacedList.setType(1);
            replacedList.setProcessId(nxpFacility.getProcess());
            replacedList.setIsReplace(nxpFacility.getIs_replace());
            /*Long userInfoId = replacedList.getUserInfoId();
            NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelByUserInfoId(userInfoId.intValue());
            if (nxpUserLabel != null){
                replacedList.setMoldType(nxpUserLabel.getMoldType());
            }*/
            replacedListService.insertReplacedList(replacedList);
        }

        NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(replacedList.getUserInfoId());
        // 查询设备相关人推送消息
        List<NxpFacilityUser> nxpFacilityUsers = nxpFacilityUserService.selectNxpFacilityUserByFacilityId(nxpFacility.getId());
        for (NxpFacilityUser nxpFacilityUser : nxpFacilityUsers) {
            NxpAppMessage nxpAppMessage = new NxpAppMessage();
            nxpAppMessage.setStatus(1);
            nxpAppMessage.setType(4);
            nxpAppMessage.setFacility_id(nxpFacility.getId());
            nxpAppMessage.setComment(nxpUserInfo.getChinese_name() + " 提交了模具更换信息，请及时确认");
            nxpAppMessage.setDel("NO");
            nxpAppMessage.setIs_receive(1);
            nxpAppMessage.setTypeName("模具更换信息");
            nxpAppMessage.setCreate_user_id(replacedList.getUserInfoId());
            nxpAppMessage.setCheck_id(replacedList.getReplaceId());
            nxpAppMessage.setUser_id(nxpFacilityUser.getUserId());
            nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
        }

        return AjaxResult.success();
    }

    /**
     * 通过盒子编号查询位置以及密码等
     *
     * @param nxpMold
     * @return
     */
    @Override
    public AjaxResult selectByBoxCode(NxpMold nxpMold) {
        if (StringUtils.isEmpty(nxpMold.getMoldRmsCode())) {
            return AjaxResult.error("模具盒RMS编号不能为空");
        }
        NxpMold moldBox = nxpMoldService.selectNxpMoldByRMSCode(nxpMold.getMoldRmsCode());
        if (moldBox != null) {
            Long moldAddressId = moldBox.getMoldAddressId();
            NxpSmartCabinet nxpSmartCabinet = nxpSmartCabinetService.selectNxpSmartCabinetById(moldAddressId);
            if (nxpSmartCabinet != null) {
                moldBox.setMoldPwd(nxpSmartCabinet.getPwd());
            } else {
                moldBox.setMoldPwd("");
            }
        }
        return AjaxResult.success(moldBox);
    }

    /**
     * 领取空盒提交
     *
     * @param replacedList
     * @return
     */
    @Override
    public AjaxResult claimEmptyBoxData(ReplacedList replacedList) {
        if (StringUtils.isEmpty(replacedList.getFacilityNumber())) {
            return AjaxResult.error("设备编号不能为空");
        }
        if (StringUtils.isEmpty(replacedList.getMoldRmsCode())) {
            return AjaxResult.error("模具RMS不能为空");
        }
        if (replacedList.getUserInfoId() == null) {
            return AjaxResult.error("申请人id不能为空");
        }
        if (replacedList.getReplaceId() == null) {
            return AjaxResult.error("待更换列表id不能为空");
        }
        NxpFacility nxpFacility = facilityService.selectByNumber(replacedList.getFacilityNumber());
        if (nxpFacility == null) {
            return AjaxResult.error("设备不存在");
        }
//      判断领取的模具盒是否正确
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByRMSCode(replacedList.getMoldRmsCode());
        if (nxpMold == null) {
            return AjaxResult.error("对应模具不存在");
        }
        // 通过id查询待更换列表,并更新状态为 待领新模具
        ReplacedList replaced = replacedListService.selectReplacedListById(replacedList.getReplaceId());
        // 执行人
        replaced.setExecuteUserId(replacedList.getUserInfoId().intValue());
        replaced.setMoldType(nxpMold.getMoldType());
        replaced.setStatus(2);
        replacedListService.updateReplacedList(replaced);

        // 构建返回map
        Map<String, Object> resultMap = new HashMap<>();

        //推荐模具
        List<NxpMold> recommendedMolds = recommendedMold(nxpFacility.getModel(), nxpMold);
        resultMap.put("recommendedMold", recommendedMolds);

        // 查询通过设备编号查询产品类型，再通过产品类型查询模具
        Long productTypeId = replaced.getProductTypeId();
        NxpMold moldParams = new NxpMold();
        moldParams.setMachineModel(nxpFacility.getModel());
        moldParams.setProductTypeId(productTypeId.toString());
        moldParams.setFacilityId(nxpFacility.getId().intValue());
        List<NxpMold> molds = nxpMoldService.selectNxpMoldListByMachineModel(moldParams);

        for (NxpMold mold : molds) {
            NxpMoldComponent nxpMoldComponent = new NxpMoldComponent();
            nxpMoldComponent.setMoldId(mold.getId());
            List<NxpMoldComponent> nxpMoldComponents = nxpMoldComponentService.selectNxpMoldComponentList(nxpMoldComponent);
            mold.setNxpMoldComponents(nxpMoldComponents);
            // 标记模具是否在设备上
            if (mold.getFacilityId() != null && mold.getFacilityId() != 0) {
                mold.setColorFlag(true);
                NxpFacility facility = nxpFacilityService.selectNxpFacilityById(mold.getFacilityId().longValue());
                mold.setMoldAddress(facility.getNumber());
            }
        }
        resultMap.put("molds", molds);
        resultMap.put("replaceId", replaced.getReplaceId());
        resultMap.put("replaceState", replaced.getStatus());
        return AjaxResult.success(resultMap);
    }

    /**
     * 领取模具
     *
     * @param receiveComponentSubmitVO
     * @return
     */
    @Override
    public AjaxResult receiveMoldBox(ReceiveComponentSubmitVO receiveComponentSubmitVO) {
        if (StringUtils.isEmpty(receiveComponentSubmitVO.getActualMoldNumber())) {
            return AjaxResult.error("实际模具编号不能为空");
        }
        if (StringUtils.isEmpty(receiveComponentSubmitVO.getFacilityNumber())) {
            return AjaxResult.error("设备编号不能为空");
        }
        if (receiveComponentSubmitVO.getMoldIsAbnormal() == null) {
            return AjaxResult.error("模具是否异常状态不能为空");
        }
        if (receiveComponentSubmitVO.getReplacedListId() == null) {
            return AjaxResult.error("待更换列表id不能为空");
        }

        // 通过实际领用的模具编号查询
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByCode(receiveComponentSubmitVO.getActualMoldNumber());

        // 如果模具标记异常，那么遍历模具部件id，标记为异常
        if (receiveComponentSubmitVO.getMoldIsAbnormal() == 0) {
            List<Long> moldComponentIds = receiveComponentSubmitVO.getMoldComponentIds();
            for (Long moldComponentId : moldComponentIds) {
                NxpMoldComponent nxpMoldComponentParams = new NxpMoldComponent();
                nxpMoldComponentParams.setMoldId(nxpMold.getId());
                nxpMoldComponentParams.setComponentId(moldComponentId);
                NxpMoldComponent nxpMoldComponent = nxpMoldComponentService.selectNxpMoldComponentByMoldIdAndComponentId(nxpMoldComponentParams);
                // 记录异常信息日志
                NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs = new NxpMoldComponentAbnormalLogs();
                nxpMoldComponentAbnormalLogs.setComponentId(moldComponentId);
                nxpMoldComponentAbnormalLogs.setComponentName(nxpMoldComponent.getComponentName());
                nxpMoldComponentAbnormalLogs.setMoldId(nxpMoldComponent.getMoldId());
                nxpMoldComponentAbnormalLogs.setMoldCode(nxpMoldComponent.getMoldCode());
                nxpMoldComponentAbnormalLogs.setRemark(receiveComponentSubmitVO.getRemark());
                nxpMoldComponentAbnormalLogs.setLogType(1);
                nxpMoldComponentAbnormalLogs.setMoldType(nxpMold.getMoldType());
                nxpMoldComponentAbnormalLogsService.insertNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs);
            }

            // 模具修改为异常状态
            nxpMold.setStatus(2);
            nxpMoldService.updateNxpMoldApi(nxpMold);
            // 如果标记为异常，需要刷新此页面，并不修改当前流程状态
            return AjaxResult.error("模具已标记为异常，请重新选择模具");
        }

        // 流程标记为已更换状态
        // 走到这里表示模具标记的是正常，那么给模具下所有的部件都标记为正常
        List<NxpMoldComponent> list = nxpMoldComponentService.selectNxpMoldComponentByMoldCode(nxpMold.getMoldCode());
        for (NxpMoldComponent nxpMoldComponent : list) {
            nxpMoldComponent.setStatus(1);
            nxpMoldComponentService.updateNxpMoldComponent(nxpMoldComponent);
        }

        NxpFacility nxpFacility = nxpFacilityService.selectByNumber(receiveComponentSubmitVO.getFacilityNumber());

        // 之前的模具进入待归还状态
        NxpMold mold = nxpMoldService.selectNxpMoldByFacility(nxpFacility.getId());
        if (mold != null) {
            mold.setStatus(6);
            // 老模具设备id变成0
            mold.setFacilityId(0);
            nxpMoldService.updateNxpMoldApi(mold);

            NxpMoldReturn nxpMoldReturn = new NxpMoldReturn();
            nxpMoldReturn.setMoldId(mold.getId());
            nxpMoldReturn.setMoldCode(mold.getMoldCode());
            nxpMoldReturn.setMoldRmsCode(mold.getMoldRmsCode());
            nxpMoldReturn.setFacilityId(nxpFacility.getId());
            nxpMoldReturn.setFacilityNumber(nxpFacility.getNumber());
            nxpMoldReturn.setProcessId(nxpFacility.getProcess());
            nxpMoldReturn.setStatus(1);
            nxpMoldReturn.setMoldType(mold.getMoldType());
            nxpMoldReturnService.insertNxpMoldReturn(nxpMoldReturn);
        }

        // 这里加判断，判断新模具是否是待归还的，如果是待归还，那么状态改为已归还，并且新模具与设备绑定
        if (nxpMold.getStatus() == 6){
            NxpMoldReturn nxpMoldReturn = new NxpMoldReturn();
            nxpMoldReturn.setMoldId(nxpMold.getId());
            nxpMoldReturn.setStatus(1);
            nxpMoldReturn.setMoldType(nxpMold.getMoldType());
            List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnService.selectNxpMoldReturnList(nxpMoldReturn);
            for (NxpMoldReturn moldReturn : nxpMoldReturns) {
                moldReturn.setStatus(3);
                moldReturn.setFacilityId(nxpFacility.getId());
                moldReturn.setFacilityNumber(nxpFacility.getNumber());
                moldReturn.setReturnPosition(2);
                nxpMoldReturnService.updateNxpMoldReturn(moldReturn);
            }
        }

        // 新模具与设备进行绑定
        nxpMold.setStatus(4);
        nxpMold.setFacilityId(nxpFacility.getId().intValue());
        nxpMoldService.updateNxpMoldApi(nxpMold);

        // 通过主键查询，将状态变更为待归还模具盒
        ReplacedList replacedList = replacedListService.selectReplacedListById(receiveComponentSubmitVO.getReplacedListId());
        if (replacedList.getMoldId() == null) {
            replacedList.setMoldId(nxpMold.getId());
            replacedList.setMoldCode(nxpMold.getMoldCode());
            replacedList.setMoldRmsCode(nxpMold.getMoldRmsCode());
            replacedList.setMoldName(nxpMold.getMoldName());
            replacedList.setMoldType(nxpMold.getMoldType());
            replacedList.setStatus(3);
            replacedList.setNewMoldCode(nxpMold.getMoldCode());
            replacedList.setNewMoldId(nxpMold.getId());
        } else {
            replacedList.setStatus(3);
            replacedList.setNewMoldCode(nxpMold.getMoldCode());
            replacedList.setNewMoldId(nxpMold.getId());
        }
        replacedListService.updateReplacedList(replacedList);

        // 构建返回对象
        Map<String, Object> map = new HashMap<>();
        map.put("moldType", receiveComponentSubmitVO.getMoldType());
        map.put("facilityNumber", receiveComponentSubmitVO.getFacilityNumber());
        map.put("moldBoxCode", replacedList.getMoldCode());
        map.put("replaceId", replacedList.getReplaceId());
        map.put("replaceState", replacedList.getStatus());
        return AjaxResult.success(map);
    }

    /**
     * 归还模具盒
     *
     * @param replacedList
     * @return
     */
    @Override
    public AjaxResult returnClaimBox(ReplacedList replacedList) {
        if (replacedList.getReplaceId() == null) {
            return AjaxResult.error("id不能为空");
        }
        // 归还模具盒后状态变更为已完成
        ReplacedList replacedList1 = replacedListService.selectReplacedListById(replacedList.getReplaceId());
        if (replacedList1 != null) {
            replacedList1.setStatus(4);
            replacedListService.updateReplacedList(replacedList1);
        }
        return AjaxResult.success();
    }

    /**
     * 部件标记异常
     *
     * @param nxpMoldComponent
     * @return
     */
    @Override
    public AjaxResult markException(NxpMoldComponent nxpMoldComponent) {
        if (StringUtils.isEmpty(nxpMoldComponent.getIds())) {
            return AjaxResult.error("id不能为空");
        }
        String ids = nxpMoldComponent.getIds();
        if (ids.contains(",")) {
            String[] split = ids.split(",");
            for (String id : split) {
                NxpMoldComponent moldComponent = nxpMoldComponentService.selectNxpMoldComponentById(Long.valueOf(id));
                if (moldComponent != null) {
                    moldComponent.setStatus(2);
                    nxpMoldComponentService.updateNxpMoldComponent(moldComponent);
                }
            }
        } else {
            NxpMoldComponent moldComponent = nxpMoldComponentService.selectNxpMoldComponentById(Long.valueOf(ids));
            if (moldComponent != null) {
                moldComponent.setStatus(2);
                nxpMoldComponentService.updateNxpMoldComponent(moldComponent);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 待更换列表
     *
     * @param replacedListVO
     * @return
     */
    @Override
    public List<ReplacedList> replacedList(ReplacedListVO replacedListVO,NxpUserLabel nxpUserLabel) {
        List<ReplacedList> replacedLists = replacedListService.selectReplacedListListByParams(replacedListVO);
        for (ReplacedList list : replacedLists) {
            String processId = list.getProcessId();
            // 工序
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(processId));
            if (nxpMachineProcess != null) {
                list.setProcess(nxpMachineProcess.getName());
            }
            // userInfo
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(list.getUserInfoId());
            if (nxpUserInfo != null) {
                list.setUserInfoName(nxpUserInfo.getChinese_name());
            }
            // 模具
            NxpMold nxpMold = nxpMoldService.selectNxpMoldById(list.getMoldId());
            list.setNxpMold(nxpMold);

            Long facilityId = list.getFacilityId();
            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityId);

            Map<String, Object> extendedData = new HashMap<>();
            // 如果状态是2-待领新模具 3-待归还模具盒 需要处理数据
            if (list.getStatus() == 2) { // 2-待领新模具
                //推荐模具
                List<NxpMold> recommendedMolds = recommendedMold(nxpFacility.getModel(), nxpMold);
                extendedData.put("recommendedMold", recommendedMolds);

                // 查询通过设备编号查询产品类型，再通过产品类型查询模具
                Long productTypeId = list.getProductTypeId();
                NxpMold moldParams = new NxpMold();
                moldParams.setMachineModel(nxpFacility.getModel());
                /*if (nxpMold == null) {
                    moldParams.setMachineModel(nxpFacility.getModel());
                } else {
                    moldParams.setMachineModel(nxpMold.getMachineModel());
                }*/
                moldParams.setProductTypeId(productTypeId.toString());
                moldParams.setFacilityId(facilityId.intValue());
                List<NxpMold> molds = nxpMoldService.selectNxpMoldListByMachineModel(moldParams);

                for (NxpMold mold : molds) {
                    NxpMoldComponent nxpMoldComponent = new NxpMoldComponent();
                    nxpMoldComponent.setMoldId(mold.getId());
                    List<NxpMoldComponent> nxpMoldComponents = nxpMoldComponentService.selectNxpMoldComponentList(nxpMoldComponent);
                    mold.setNxpMoldComponents(nxpMoldComponents);
                    // 标记模具是否在设备上
                    if (mold.getFacilityId() != null && mold.getFacilityId() != 0) {
                        mold.setColorFlag(true);
                        NxpFacility facility = nxpFacilityService.selectNxpFacilityById(mold.getFacilityId().longValue());
                        mold.setMoldAddress(facility.getNumber());
                    }
                }

                extendedData.put("molds", molds);
            }

            if (list.getStatus() == 3) { //3-待归还模具盒
                extendedData.put("mold", nxpMold);
            }

            list.setExtendedData(extendedData);

            if ((replacedListVO.getUserId().equals(list.getUserInfoId())) || (nxpUserLabel.getPosition() == 4 && list.getMoldType() == nxpUserLabel.getMoldType())){
                list.setIsDel(1);
            }
        }
        return replacedLists;
    }

    /**
     * 维修更换模具按钮触发接口
     *
     * @param inspectionReplaceMoldVO
     * @return
     */
    @Override
    public AjaxResult inspectionReplaceMoldButton(InspectionReplaceMoldVO inspectionReplaceMoldVO) {
//        if (inspectionReplaceMoldVO.getInspectionId() == null){
//            return AjaxResult.error("报修id不能为空");
//        }
//        if (inspectionReplaceMoldVO.getInspectionServiceId() == null){
//            return AjaxResult.error("维修id不能为空");
//        }
//        if (inspectionReplaceMoldVO.getInspectionPositionId() == null){
//            return AjaxResult.error("维修位置id不能为空");
//        }
//        if (inspectionReplaceMoldVO.getInspectionContentId() == null){
//            return AjaxResult.error("维修内容id不能为空");
//        }
        if (StringUtils.isEmpty(inspectionReplaceMoldVO.getFacilityNumber())) {
            return AjaxResult.error("设备编号不能为空");
        }
//        通过设备编号查询设备id
        NxpFacility facility = nxpFacilityService.selectByNumber(inspectionReplaceMoldVO.getFacilityNumber());
        if (facility == null) {
            return AjaxResult.error("该设备不存在");
        }

        // 将维修内容与维修位置id更新到维修表
//        NxpInspectionService nxpInspectionServiceParams = new NxpInspectionService();
//        nxpInspectionServiceParams.setId(inspectionReplaceMoldVO.getInspectionServiceId());
//        nxpInspectionServiceParams.setPosition_id(inspectionReplaceMoldVO.getInspectionPositionId());
//        nxpInspectionServiceParams.setContent_id(inspectionReplaceMoldVO.getInspectionContentId());
//        nxpInspectionServiceService.updateNxpInspectionService(nxpInspectionServiceParams);
//        // 将报修修改为维修中
//        NxpInspection nxpInspection = new NxpInspection();
//        nxpInspection.setId(inspectionReplaceMoldVO.getInspectionId());
//        nxpInspection.setStatus(2);
//        nxpInspectionService.updateNxpInspection(nxpInspection);
        // 构建返回map
        Map<String, Object> resultMap = new HashMap<>();

//        todo zhangxin ,考虑后续使用同一个字段进行查询 mold表中的facility_id
        NxpFacility nxpFacility = nxpFacilityService.selectByNumber(inspectionReplaceMoldVO.getFacilityNumber());
        NxpMold mold = nxpMoldService.selectNxpMoldByFacility(nxpFacility.getId());


        // 查询该设备当前使用模具
//        NxpFacility nxpFacility = nxpFacilityService.selectByNumber(inspectionReplaceMoldVO.getFacilityNumber());
//        ReplacedList replacedListParams = new ReplacedList();
//        replacedListParams.setFacilityId(nxpFacility.getId());
//        replacedListParams.setProcessId(nxpFacility.getProcess());
//        ReplacedList replacedList = replacedListService.selectByFacilityIdAndProcessIdLimit1(replacedListParams);

        if (mold == null) {
            resultMap.put("currentlyUsingMolds", "");
            resultMap.put("moldType", "");
            resultMap.put("componentList", "");
        } else {
            resultMap.put("currentlyUsingMolds", mold.getMoldCode());
            NxpMold nxpMold = nxpMoldService.selectNxpMoldById(mold.getId());
            resultMap.put("moldType", nxpMold.getMoldType());
            List<NxpMoldComponent> list = nxpMoldComponentService.selectNxpMoldComponentByMoldCode(nxpMold.getMoldCode());
            resultMap.put("componentList", list);
        }

        resultMap.put("facilityNumber", nxpFacility.getNumber());
        return AjaxResult.success(resultMap);
    }

    /**
     * 根据模具编号查询模具部件
     *
     * @param nxpMoldComponent
     * @return
     */
    @Override
    public AjaxResult selectComponentByMoldCode(NxpMoldComponent nxpMoldComponent) {
        if (StringUtils.isEmpty(nxpMoldComponent.getMoldCode())) {
            return AjaxResult.error("模具编号不能为空");
        }
        List<NxpMoldComponent> list = nxpMoldComponentService.selectNxpMoldComponentByMoldCode(nxpMoldComponent.getMoldCode());
        return AjaxResult.success(list);
    }

    /**
     * 维修更换模具确认类型下一步接口
     *
     * @param confirmTypeNextStepVO
     * @return
     */
    @Override
    public AjaxResult confirmTypeNextStep(ConfirmTypeNextStepVO confirmTypeNextStepVO) {
        if (StringUtils.isEmpty(confirmTypeNextStepVO.getMoldCode())) {
            return AjaxResult.error("模具编号不能为空");
        }
        if (confirmTypeNextStepVO.getMoldReplaceType() == null) {
            return AjaxResult.error("模具更换类型不能为空");
        }
        if (StringUtils.isEmpty(confirmTypeNextStepVO.getFacilityNumber())) {
            return AjaxResult.error("设备编号不能为空");
        }
        if (confirmTypeNextStepVO.getUserInfoId() == null) {
            return AjaxResult.error("用户id不能为空");
        }

        NxpFacility nxpFacility = nxpFacilityService.selectByNumber(confirmTypeNextStepVO.getFacilityNumber());
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByCode(confirmTypeNextStepVO.getMoldCode());
        if (nxpMold == null) {
            return AjaxResult.error("模具不存在");
        }

        // 保存到待更换列表
        NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(confirmTypeNextStepVO.getUserInfoId());
        ReplacedList replacedList = new ReplacedList();
        replacedList.setMoldCode(nxpMold.getMoldCode());
        replacedList.setMoldId(nxpMold.getId());
        replacedList.setMoldName(nxpMold.getMoldName());
        replacedList.setFacilityId(nxpFacility.getId());
        replacedList.setProcessId(nxpFacility.getProcess());
        replacedList.setUserInfoId(nxpUserInfo.getId());
        replacedList.setUserInfoName(nxpUserInfo.getChinese_name());
        replacedList.setStatus(1);
        replacedList.setProductTypeId(confirmTypeNextStepVO.getProductTypeId());
        replacedList.setMoldType(nxpMold.getMoldType());
        if (confirmTypeNextStepVO.getMoldReplaceType() == 1) {
            // 整体
            replacedList.setType(1);
        }
        if (confirmTypeNextStepVO.getMoldReplaceType() == 2) {
            // 部件
            replacedList.setType(3);
        }
        // todo 这里需确认是否要增加产品类型筛选
        replacedList.setFacilityNumber(nxpFacility.getNumber());
        //todo 这里看是否要保存部件id
        replacedListService.insertReplacedList(replacedList);
        // 构建返回map
        Map<String, Object> resultMap = new HashMap<>();

        // 推荐模具
        List<NxpMold> recommendedMolds = recommendedMold(nxpFacility.getModel(), nxpMold);
        resultMap.put("recommendedMold", recommendedMolds);

        // 查询通过设备编号查询产品类型，再通过产品类型查询模具
        Long productTypeId = confirmTypeNextStepVO.getProductTypeId();
        NxpMold moldParams = new NxpMold();
        moldParams.setMachineModel(nxpMold.getMachineModel());
        moldParams.setProductTypeId(productTypeId.toString());
        if (nxpMold == null) {
            moldParams.setMachineModel(nxpFacility.getModel());
        } else {
            moldParams.setMachineModel(nxpMold.getMachineModel());
        }
        moldParams.setProductTypeId(productTypeId.toString());
        moldParams.setFacilityId(nxpFacility.getId().intValue());
        List<NxpMold> molds = nxpMoldService.selectNxpMoldListByMachineModel(moldParams);

        for (NxpMold mold : molds) {
            NxpMoldComponent nxpMoldComponent = new NxpMoldComponent();
            nxpMoldComponent.setMoldId(mold.getId());
            List<NxpMoldComponent> nxpMoldComponents = nxpMoldComponentService.selectNxpMoldComponentList(nxpMoldComponent);
            mold.setNxpMoldComponents(nxpMoldComponents);
            // 标记模具是否在设备上
            if (mold.getFacilityId() != null && mold.getFacilityId() != 0) {
                mold.setColorFlag(true);
                NxpFacility facility = nxpFacilityService.selectNxpFacilityById(mold.getFacilityId().longValue());
                mold.setMoldAddress(facility.getNumber());
            }
        }

        resultMap.put("molds", molds);
        resultMap.put("facilityNumber", nxpFacility.getNumber());

        if (nxpMold != null) {
            resultMap.put("moldType", nxpMold.getMoldType());
        } else {
            resultMap.put("moldType", "");
        }
        resultMap.put("replacedListId", replacedList.getReplaceId());
        return AjaxResult.success(resultMap);
    }

    /**
     * 维修更换模具领取部件提交接口
     *
     * @param receiveComponentSubmitVO
     * @return
     */
    @Override
    public AjaxResult receiveComponentSubmit(ReceiveComponentSubmitVO receiveComponentSubmitVO) {
        if (StringUtils.isEmpty(receiveComponentSubmitVO.getActualMoldNumber())) {
            return AjaxResult.error("实际模具编号不能为空");
        }
        if (receiveComponentSubmitVO.getReplacedListId() == null) {
            return AjaxResult.error("更换列表id不能为空");
        }
        if (receiveComponentSubmitVO.getMoldComponentIds() == null) {
            return AjaxResult.error("更换部件id列表不能为空");
        }
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByCode(receiveComponentSubmitVO.getActualMoldNumber());
        if (nxpMold == null) {
            return AjaxResult.error("该模具不存在");
        }
        ReplacedList replacedList = replacedListService.selectReplacedListById(receiveComponentSubmitVO.getReplacedListId());
        replacedList.setMoldId(nxpMold.getId());
        replacedList.setMoldName(nxpMold.getMoldName());
        replacedList.setMoldCode(nxpMold.getMoldCode());
        replacedList.setStatus(2);
        replacedListService.updateReplacedList(replacedList);
        //todo 这里看是否要保存部件id
        return AjaxResult.success();
    }

    /**
     * 待归还列表
     *
     * @param nxpMoldReturn
     * @return
     */
    @Override
    public List<NxpMoldReturn> returnList(NxpMoldReturn nxpMoldReturn) {
        nxpMoldReturn.setStatus(1);
        List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnService.selectNxpMoldReturnListNew(nxpMoldReturn);
        /*for (NxpMoldReturn moldReturn : nxpMoldReturns) {
            Long moldId = moldReturn.getMoldId();
            NxpMold nxpMold = nxpMoldService.selectNxpMoldById(moldId);
            moldReturn.setMoldType(nxpMold.getMoldType());
        }*/
        return nxpMoldReturns;
    }

    /**
     * 模具归还提交按钮
     *
     * @param nxpMoldReturn
     * @return
     */
    @Override
    public AjaxResult moldReturnSubmit(NxpMoldReturn nxpMoldReturn) {
        if (nxpMoldReturn.getId() == null) {
            return AjaxResult.error("归还id不能为空");
        }
        if (StringUtils.isEmpty(nxpMoldReturn.getMoldRmsCode())) {
            return AjaxResult.error("模具rms编号不能为空");
        }
        if (nxpMoldReturn.getReturnPosition() == null) {
            return AjaxResult.error("模具归还位置不能为空");
        }

        NxpFacility nxpFacility = null;
        if (StringUtils.isNotEmpty(nxpMoldReturn.getFacilityNumber())){
            nxpFacility = facilityService.selectByNumber(nxpMoldReturn.getFacilityNumber());
            if (nxpFacility != null){
                nxpMoldReturn.setFacilityId(nxpFacility.getId());
            }
        }else {
            nxpMoldReturn.setFacilityId(0L);
        }

        if (nxpMoldReturn.getReturnPosition() == 1) {
            // 归还到仓库
            nxpMoldReturn.setStatus(2);
        } else {
            nxpMoldReturn.setStatus(3);
        }
        // 处理待归还列表状态并更新
        nxpMoldReturnService.updateNxpMoldReturnApi(nxpMoldReturn);

        NxpMoldReturn returnParams = new NxpMoldReturn();
        returnParams.setMoldRmsCode(nxpMoldReturn.getMoldRmsCode());
        returnParams.setStatus(1);
        List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnService.selectNxpMoldReturnList(returnParams);
        for (NxpMoldReturn moldReturn : nxpMoldReturns) {
            moldReturn.setStatus(nxpMoldReturn.getStatus());
            moldReturn.setReturnPosition(nxpMoldReturn.getReturnPosition());
            nxpMoldReturnService.updateNxpMoldReturnApi(moldReturn);
        }

        // 通过模具rms编号查询正在归还的模具，更新模具状态
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByRMSCode(nxpMoldReturn.getMoldRmsCode());
        if (nxpMold == null) {
            return AjaxResult.error("模具不存在");
        }
        if (nxpMold.getStatus() == 2 || nxpMold.getStatus() == 3) {
            // 模具为异常状态时不处理
        } else {
            nxpMold.setStatus(1);
        }
        if (nxpMoldReturn.getReturnPosition() == 2) {// 归还到设备上
            String facilityNumber = nxpMoldReturn.getFacilityNumber();
            if (StringUtils.isEmpty(facilityNumber)) {
                return AjaxResult.error("设备编号为必传字段");
            }
//            NxpFacility nxpFacility = facilityService.selectByNumber(facilityNumber);
            if (nxpFacility == null) {
                return AjaxResult.error("设备不存在");
            }
            // 查询现在的设备上有无模具,如果设备上没有模具，则绑定
            NxpMold moldFacility = nxpMoldService.selectNxpMoldByFacility(nxpFacility.getId());
            if (moldFacility == null) {
                nxpMold.setFacilityId(nxpFacility.getId().intValue());
                nxpMold.setStatus(4);
                nxpMoldService.updateNxpMoldApi(nxpMold);
            } else {
                // 如果设备上有模具，老模具变成待归还，新模具被绑定
                moldFacility.setStatus(6);
                // 老模具设备id变成0
                moldFacility.setFacilityId(0);
                nxpMoldService.updateNxpMoldApi(moldFacility);

                // 老模具新增数据到待归还列表
                NxpMoldReturn nxpMoldReturnParams = new NxpMoldReturn();
                nxpMoldReturnParams.setMoldId(moldFacility.getId());
                nxpMoldReturnParams.setMoldCode(moldFacility.getMoldCode());
                nxpMoldReturnParams.setMoldRmsCode(moldFacility.getMoldRmsCode());
                nxpMoldReturnParams.setMoldName(moldFacility.getMoldName());
                nxpMoldReturnParams.setFacilityId(nxpFacility.getId());
                nxpMoldReturnParams.setFacilityNumber(nxpFacility.getNumber());
                nxpMoldReturnParams.setProcessId(nxpFacility.getProcess());
                nxpMoldReturnParams.setStatus(1);
                nxpMoldReturnParams.setMoldType(moldFacility.getMoldType());
                nxpMoldReturnService.insertNxpMoldReturn(nxpMoldReturnParams);

                //新模具被绑定
                nxpMold.setFacilityId(nxpFacility.getId().intValue());
                nxpMold.setStatus(4);
                nxpMoldService.updateNxpMoldApi(nxpMold);
            }
        }else {
            // 归还到仓库
            nxpMold.setFacilityId(0);
            nxpMold.setStatus(1);
            nxpMoldService.updateNxpMoldApi(nxpMold);
        }

        // 处理模具待更换列表模具和模具盒
        ReplacedList params = new ReplacedList();
        params.setMoldCode(nxpMold.getMoldCode());
//        params.setType(1);
        params.setPageNum(null);
        params.setPageSize(null);
        ReplacedList moldReplacedList = replacedListService.selectByMoldCodeAndTypeLimit1(params);
        if (moldReplacedList != null) {
            moldReplacedList.setStatus(4);
            replacedListService.updateReplacedList(moldReplacedList);
        }
        /*params.setType(2);
        ReplacedList moldBoxReplacedList = replacedListService.selectByMoldCodeAndTypeLimit1(params);
        if (moldBoxReplacedList != null) {
            moldBoxReplacedList.setStatus(4);
            replacedListService.updateReplacedList(moldBoxReplacedList);
        }*/
        return AjaxResult.success();
    }

    /**
     * 根据模具和模具盒编号查询模具
     *
     * @param nxpMoldMaintenance
     * @return
     */
    @Override
    public AjaxResult moldMaintenanceSearch(NxpMoldMaintenance nxpMoldMaintenance) {
        if (nxpMoldMaintenance.getMoldRmsCode() == null) {
            return AjaxResult.error("模具RMS编号不能为空");
        }
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByRMSCode(nxpMoldMaintenance.getMoldRmsCode());
        if (nxpMold == null) {
            return AjaxResult.error("模具不存在");
        }
        // 标记模具是否在设备上
        if (nxpMold.getFacilityId() != null && nxpMold.getFacilityId() != 0) {
            nxpMold.setColorFlag(true);
            NxpFacility facility = nxpFacilityService.selectNxpFacilityById(nxpMold.getFacilityId().longValue());
            nxpMold.setMoldAddress(facility.getNumber());
        }
        return AjaxResult.success(nxpMold);
    }

    /**
     * 模具维护-开启维护检查
     *
     * @param nxpMoldMaintenance
     * @return
     */
    @Override
    public AjaxResult beginMoldMaintenanceCheck(NxpMoldMaintenance nxpMoldMaintenance) {
        if (nxpMoldMaintenance.getMoldId() == null) {
            return AjaxResult.error("模具id不能为空");
        }
        if (nxpMoldMaintenance.getUserInfoId() == null) {
            return AjaxResult.error("用户id不能为空");
        }
        // 查询模具
        NxpMold nxpMold = nxpMoldService.selectNxpMoldById(nxpMoldMaintenance.getMoldId());
        if (nxpMold == null) {
            return AjaxResult.error("模具不存在");
        }

        NxpMoldMaintenance moldMaintenance = nxpMoldMaintenanceService.selectNxpMoldMaintenanceById(nxpMoldMaintenance.getId());
        if (moldMaintenance == null) {
            // 如果模具维护记录为空，那么新增
            nxpMoldMaintenance.setMoldCode(nxpMold.getMoldCode());
            nxpMoldMaintenance.setMoldRmsCode(nxpMold.getMoldRmsCode());
            nxpMoldMaintenance.setMoldBoxCode(nxpMold.getMoldCode());
            nxpMoldMaintenance.setCheckTime(DateUtils.getCurrentByHour(12));
            nxpMoldMaintenance.setStatus(1);
            nxpMoldMaintenance.setMaintenanceType(3);
            nxpMoldMaintenance.setMoldType(nxpMold.getMoldType());
            nxpMoldMaintenanceService.insertNxpMoldMaintenance(nxpMoldMaintenance);
        } else {
            // 如果存在，那么更新
            // 更新维护记录
            moldMaintenance.setMaintenanceUserId(nxpMoldMaintenance.getUserInfoId());
            moldMaintenance.setRealCheckTime(DateUtils.getNowDate());
            nxpMoldMaintenanceService.updateNxpMoldMaintenance(moldMaintenance);
        }

        // 模具状态改为待维护
        nxpMold.setStatus(5);
        nxpMold.setFacilityId(0);
        nxpMoldService.updateNxpMoldApi(nxpMold);

        // 根据模具id查询分组
//        NxpGroupMold nxpGroupMold = nxpGroupMoldService.selectByMoldIds(nxpMold.getId().toString());
        NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind = nxpMoldMaintenanceGroupBindService.selectNxpMoldMaintenanceGroupBindByMoldId(nxpMoldMaintenance.getMoldId());
        if (nxpMoldMaintenanceGroupBind == null) {
            return AjaxResult.error("该模具未绑定分组");
        }
        // 根据分组id查询检查项
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupService.selectNxpMoldMaintenanceGroupById(nxpMoldMaintenanceGroupBind.getMoldMaintenanceGroupId());
        if (nxpMoldMaintenanceGroup == null) {
            return AjaxResult.error("分组不存在");
        }
        if (nxpMoldMaintenanceGroup.getNxpMoldMaintenanceGroupItemList().size() == 0) {
            return AjaxResult.error("该分组未配置维护项信息");
        }

        List<NxpMoldMaintenanceGroupItem> nxpMoldMaintenanceGroupItemList = nxpMoldMaintenanceGroup.getNxpMoldMaintenanceGroupItemList();
        for (NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem : nxpMoldMaintenanceGroupItemList) {
            Long componentId = nxpMoldMaintenanceGroupItem.getComponentId();
            if (componentId != null) {
                NxpMoldTypeComponent nxpMoldTypeComponent = nxpMoldTypeService.selectByComponentId(componentId);
                if (nxpMoldTypeComponent != null) {
                    nxpMoldMaintenanceGroupItem.setComponentName(nxpMoldTypeComponent.getComponentName());
                }
            }
        }
        nxpMoldMaintenanceGroup.setNxpMoldMaintenanceGroupItemList(nxpMoldMaintenanceGroupItemList);
        Map<String, Object> map = new HashMap<>();
        map.put("moldMaintenanceId", nxpMoldMaintenance.getId());
        map.put("checkGroup", nxpMoldMaintenanceGroup);
        map.put("checkItems", nxpMoldMaintenanceGroup.getNxpMoldMaintenanceGroupItemList());
        return AjaxResult.success(map);
    }

    /**
     * 待维护列表
     *
     * @param nxpMoldMaintenanceVO
     * @return
     */
    @Override
    public List<NxpMoldMaintenance> maintainedList(NxpMoldMaintenanceVO nxpMoldMaintenanceVO) {
        List<NxpMoldMaintenance> nxpMoldMaintenances = nxpMoldMaintenanceService.selectNxpMoldMaintenanceListApiParams(nxpMoldMaintenanceVO);
        for (NxpMoldMaintenance moldMaintenance : nxpMoldMaintenances) {
            Long moldId = moldMaintenance.getMoldId();
            NxpMold nxpMold = nxpMoldService.selectNxpMoldById(moldId);
            Integer facilityId = nxpMold.getFacilityId();
            if (facilityId != null) {
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityId.longValue());
                if (nxpFacility != null) {
                    NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess()));
                    if (nxpMachineProcess != null) {
                        moldMaintenance.setProcess(nxpMachineProcess.getName());
                    }
                }
            }
        }
        return nxpMoldMaintenances;
    }

    /**
     * 维护上传图片
     *
     * @param picture
     * @return
     */
    @Override
    public AjaxResult uploadFile(Picture picture) {
        String base64 = picture.getImg_picture();
        if (org.apache.commons.lang3.StringUtils.isBlank(base64)) {
            return AjaxResult.error("base64编码为空");
        }
        // 转换base64为MultipartFile
        MultipartFile multipartFile = MultipartFileUtils.base64ToMultipart(base64);
        NxpMoldMaintenanceFile nxpMoldMaintenanceFile = new NxpMoldMaintenanceFile();
        try {
            Map<String, String> uploadFile = uploadFile(multipartFile);
            if (uploadFile != null) {
                if (org.apache.commons.lang3.StringUtils.isNoneBlank(uploadFile.get("url"))) {
                    nxpMoldMaintenanceFile.setFilePath(uploadFile.get("url"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("图片上传失败");
        }
        nxpMoldMaintenanceFileService.insertNxpMoldMaintenanceFile(nxpMoldMaintenanceFile);
        return AjaxResult.success(nxpMoldMaintenanceFile);
    }

    /**
     * 维护提交按钮接口
     *
     * @param nxpMaintainedCheckDTO
     * @return
     */
    @Override
    public AjaxResult maintainedCheck(NxpMaintainedCheckDTO nxpMaintainedCheckDTO) {
        if (nxpMaintainedCheckDTO.getItems().size() == 0) {
            return AjaxResult.error("维护项不能为空");
        }
        if (nxpMaintainedCheckDTO.getMoldMaintenanceId() == null) {
            return AjaxResult.error("维护id不能为空");
        }
        if (nxpMaintainedCheckDTO.getUserInfoId() == null) {
            return AjaxResult.error("用户id不能为空");
        }

        Long moldMaintenanceId = nxpMaintainedCheckDTO.getMoldMaintenanceId();
        NxpMoldMaintenance nxpMoldMaintenance = nxpMoldMaintenanceService.selectNxpMoldMaintenanceById(moldMaintenanceId);
        if (nxpMoldMaintenance == null) {
            return AjaxResult.error("不存在该条维护记录");
        }

        // 判断该维护是否逾期
        if (nxpMoldMaintenance.getStatus() == 3 && nxpMoldMaintenance.getOverdueState() == 1) {
            nxpMoldMaintenance.setRealCheckTime(DateUtils.getCurrentByHour(7));
            // 更新维护表状态为已完成
            nxpMoldMaintenance.setStatus(2);
        } else {
            nxpMoldMaintenance.setRealCheckTime(DateUtils.getNowDate());
            nxpMoldMaintenance.setStatus(2);
        }
        nxpMoldMaintenanceService.updateNxpMoldMaintenance(nxpMoldMaintenance);

        // 更新模具状态为待归还
        Long moldId = nxpMoldMaintenance.getMoldId();
        NxpMold nxpMold = nxpMoldService.selectNxpMoldById(moldId);
        nxpMold.setStatus(6);
        nxpMoldService.updateNxpMoldApi(nxpMold);

        // 将维护好的模具插入到待归还模具列表，走归还流程
        NxpMoldReturn moldReturn = new NxpMoldReturn();
        moldReturn.setMoldId(moldId);
        moldReturn.setMoldCode(nxpMold.getMoldCode());
        moldReturn.setMoldRmsCode(nxpMold.getMoldRmsCode());
        moldReturn.setMoldName(nxpMold.getMoldName());
        moldReturn.setStatus(1);
        moldReturn.setMoldType(nxpMold.getMoldType());
        nxpMoldReturnService.insertNxpMoldReturn(moldReturn);

        // 将检查结果插入到检查表
        List<NxpMoldMaintenanceGroupItem> items = nxpMaintainedCheckDTO.getItems();
        for (NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem : items) {
            // 循环将检查项插入到检查表
            NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck = new NxpMoldMaintenanceCheck();
            nxpMoldMaintenanceCheck.setMoldMaintenanceId(moldMaintenanceId);
            nxpMoldMaintenanceCheck.setGroupItemId(nxpMoldMaintenanceGroupItem.getGroupItemId());
            nxpMoldMaintenanceCheck.setCheckTime(DateUtils.getNowDate());
            nxpMoldMaintenanceCheck.setUserInfoId(nxpMaintainedCheckDTO.getUserInfoId());
            nxpMoldMaintenanceCheck.setGroupItemResult(nxpMoldMaintenanceGroupItem.getItemResult().longValue());
            nxpMoldMaintenanceCheck.setFileId(nxpMoldMaintenanceGroupItem.getFileId());
            nxpMoldMaintenanceCheck.setRemark(nxpMoldMaintenanceGroupItem.getRemark());
            nxpMoldMaintenanceCheckService.insertNxpMoldMaintenanceCheck(nxpMoldMaintenanceCheck);
            // 处理fileId
            String fileId = nxpMoldMaintenanceGroupItem.getFileId();
            if (StringUtils.isNotEmpty(fileId)) {
                if (fileId.contains(",")) {
                    // 表示多个
                    String[] split = fileId.split(",");
                    for (String id : split) {
                        NxpMoldMaintenanceFile nxpMoldMaintenanceFile = nxpMoldMaintenanceFileService.selectNxpMoldMaintenanceFileById(Long.valueOf(id));
                        nxpMoldMaintenanceFile.setMoldCheckId(nxpMoldMaintenanceCheck.getId());
                        nxpMoldMaintenanceFile.setMoldMaintenanceId(moldMaintenanceId);
                        nxpMoldMaintenanceFileService.updateNxpMoldMaintenanceFile(nxpMoldMaintenanceFile);
                    }
                } else {
                    // 表示一个
                    NxpMoldMaintenanceFile nxpMoldMaintenanceFile = nxpMoldMaintenanceFileService.selectNxpMoldMaintenanceFileById(Long.valueOf(fileId));
                    nxpMoldMaintenanceFile.setMoldCheckId(nxpMoldMaintenanceCheck.getId());
                    nxpMoldMaintenanceFile.setMoldMaintenanceId(moldMaintenanceId);
                    nxpMoldMaintenanceFileService.updateNxpMoldMaintenanceFile(nxpMoldMaintenanceFile);
                }
            }
            // 判断是否异常
            if (nxpMoldMaintenanceGroupItem.getItemResult() == 2) {
                // 判断是否绑定部件
                Long groupItemId = nxpMoldMaintenanceGroupItem.getGroupItemId();
                NxpMoldMaintenanceGroupItem moldMaintenanceGroupItem = nxpMoldMaintenanceGroupItemService.selectNxpMoldMaintenanceGroupItemById(groupItemId);
                if (moldMaintenanceGroupItem != null && moldMaintenanceGroupItem.getComponentId() != null) {
                    // 根据模具id和部件id查询唯一部件
                    NxpMoldComponent nxpMoldComponentParams = new NxpMoldComponent();
                    nxpMoldComponentParams.setMoldId(nxpMold.getId());
                    nxpMoldComponentParams.setComponentId(moldMaintenanceGroupItem.getComponentId());
                    NxpMoldComponent nxpMoldComponent = nxpMoldComponentService.selectNxpMoldComponentByMoldIdAndComponentId(nxpMoldComponentParams);

                    // 记录异常信息日志
                    NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs = new NxpMoldComponentAbnormalLogs();
                    nxpMoldComponentAbnormalLogs.setComponentId(nxpMoldComponent.getId());
                    nxpMoldComponentAbnormalLogs.setComponentName(nxpMoldComponent.getComponentName());
                    nxpMoldComponentAbnormalLogs.setMoldId(nxpMoldComponent.getMoldId());
                    nxpMoldComponentAbnormalLogs.setMoldCode(nxpMoldComponent.getMoldCode());
                    nxpMoldComponentAbnormalLogs.setRemark(nxpMoldMaintenanceGroupItem.getRemark());
                    nxpMoldComponentAbnormalLogs.setLogType(2);
                    nxpMoldComponentAbnormalLogs.setMoldType(nxpMold.getMoldType());
                    nxpMoldComponentAbnormalLogsService.insertNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs);

                    // 维护部件为异常
                    nxpMoldComponent.setStatus(2);
                    nxpMoldComponentService.updateNxpMoldComponent(nxpMoldComponent);
                }

                // 设置模具为异常状态
                nxpMold.setStatus(2);
                nxpMoldService.updateNxpMoldApi(nxpMold);
            } else if (nxpMoldMaintenanceGroupItem.getItemResult() == 3) {
                // 标记为维修
                Long groupItemId = nxpMoldMaintenanceGroupItem.getGroupItemId();
                NxpMoldMaintenanceGroupItem moldMaintenanceGroupItem = nxpMoldMaintenanceGroupItemService.selectNxpMoldMaintenanceGroupItemById(groupItemId);
                if (moldMaintenanceGroupItem != null && moldMaintenanceGroupItem.getComponentId() != null) {
                    // 根据模具id和部件id查询唯一部件
                    NxpMoldComponent nxpMoldComponentParams = new NxpMoldComponent();
                    nxpMoldComponentParams.setMoldId(nxpMold.getId());
                    nxpMoldComponentParams.setComponentId(moldMaintenanceGroupItem.getComponentId());
                    NxpMoldComponent nxpMoldComponent = nxpMoldComponentService.selectNxpMoldComponentByMoldIdAndComponentId(nxpMoldComponentParams);

                    // 记录异常信息日志
                    NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs = new NxpMoldComponentAbnormalLogs();
                    nxpMoldComponentAbnormalLogs.setComponentId(nxpMoldComponent.getId());
                    nxpMoldComponentAbnormalLogs.setComponentName(nxpMoldComponent.getComponentName());
                    nxpMoldComponentAbnormalLogs.setMoldId(nxpMoldComponent.getMoldId());
                    nxpMoldComponentAbnormalLogs.setMoldCode(nxpMoldComponent.getMoldCode());
                    nxpMoldComponentAbnormalLogs.setRemark(nxpMoldMaintenanceGroupItem.getRemark());
                    nxpMoldComponentAbnormalLogs.setLogType(2);
                    nxpMoldComponentAbnormalLogs.setMoldType(nxpMold.getMoldType());
                    nxpMoldComponentAbnormalLogsService.insertNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs);

                    // 维护部件为异常
                    nxpMoldComponent.setStatus(1);
                    nxpMoldComponentService.updateNxpMoldComponent(nxpMoldComponent);
                }

                // 设置模具为异常状态
                nxpMold.setStatus(1);
                nxpMoldService.updateNxpMoldApi(nxpMold);
            } else {
                Long groupItemId = nxpMoldMaintenanceGroupItem.getGroupItemId();
                NxpMoldMaintenanceGroupItem moldMaintenanceGroupItem = nxpMoldMaintenanceGroupItemService.selectNxpMoldMaintenanceGroupItemById(groupItemId);
                if (moldMaintenanceGroupItem != null && moldMaintenanceGroupItem.getComponentId() != null) {
//                    NxpMoldComponent nxpMoldComponent = nxpMoldComponentService.selectNxpMoldComponentById(moldMaintenanceGroupItem.getComponentId());
                    // 根据模具id和部件id查询唯一部件
                    NxpMoldComponent nxpMoldComponentParams = new NxpMoldComponent();
                    nxpMoldComponentParams.setMoldId(nxpMold.getId());
                    nxpMoldComponentParams.setComponentId(moldMaintenanceGroupItem.getComponentId());
                    NxpMoldComponent nxpMoldComponent = nxpMoldComponentService.selectNxpMoldComponentByMoldIdAndComponentId(nxpMoldComponentParams);
                    nxpMoldComponent.setStatus(1);
                    nxpMoldComponentService.updateNxpMoldComponent(nxpMoldComponent);
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 模具列表
     *
     * @param nxpMold
     * @return
     */
    @Override
    public List<NxpMold> moldList(NxpMold nxpMold) {
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldList(nxpMold);
        for (NxpMold mold : nxpMolds) {
            Integer facilityId = mold.getFacilityId();
            if (facilityId != null) {
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityId.longValue());
                if (nxpFacility != null) {
                    String process = nxpFacility.getProcess();
                    NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(process));
                    if (nxpMachineProcess != null) {
                        mold.setProcess(nxpMachineProcess.getName());
                    }
                }
            }
        }
        return nxpMolds;
    }

    /**
     * 模具详情
     *
     * @param nxpMold
     * @return
     */
    @Override
    public AjaxResult moldDetails(NxpMold nxpMold) {
        if (nxpMold.getId() == null) {
            return AjaxResult.error("模具id不能为空");
        }
        NxpMold mold = nxpMoldService.selectNxpMoldById(nxpMold.getId());
        if (mold == null) {
            return AjaxResult.error("模具不存在");
        }
        // 标记模具是否在设备上
        if (mold.getFacilityId() != null && mold.getFacilityId() != 0) {
            NxpFacility facility = nxpFacilityService.selectNxpFacilityById(mold.getFacilityId().longValue());
            mold.setMoldAddress(facility.getNumber());
        }
        List<NxpMoldComponent> list = nxpMoldComponentService.selectNxpMoldComponentByMoldId(mold.getId());
        mold.setNxpMoldComponents(list);
        return AjaxResult.success(mold);
    }

    /**
     * 模具详情提交
     *
     * @param receiveComponentSubmitVO
     * @return
     */
    @Override
    public AjaxResult moldDetailsSubmit(ReceiveComponentSubmitVO receiveComponentSubmitVO) {
        if (receiveComponentSubmitVO.getMoldIsAbnormal() == null) {
            return AjaxResult.error("模具是否异常状态不能为空");
        }
        if (receiveComponentSubmitVO.getMoldId() == null) {
            return AjaxResult.error("模具id不能为空");
        }

        NxpMold nxpMold = nxpMoldService.selectNxpMoldById(receiveComponentSubmitVO.getMoldId());
        if (nxpMold == null) {
            return AjaxResult.error("模具不存在");
        }

        if (receiveComponentSubmitVO.getMoldIsAbnormal() == 0) {
            // 表示异常
            if (receiveComponentSubmitVO.getMoldIsAbnormal() == 0) {
                List<Long> moldComponentIds = receiveComponentSubmitVO.getMoldComponentIds();
                for (Long moldComponentId : moldComponentIds) {
                    NxpMoldComponent nxpMoldComponentParams = new NxpMoldComponent();
                    nxpMoldComponentParams.setMoldId(nxpMold.getId());
                    nxpMoldComponentParams.setComponentId(moldComponentId);
                    NxpMoldComponent nxpMoldComponent = nxpMoldComponentService.selectNxpMoldComponentByMoldIdAndComponentId(nxpMoldComponentParams);
                    // 记录异常信息日志
                    NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs = new NxpMoldComponentAbnormalLogs();
                    nxpMoldComponentAbnormalLogs.setComponentId(nxpMoldComponent.getId());
                    nxpMoldComponentAbnormalLogs.setComponentName(nxpMoldComponent.getComponentName());
                    nxpMoldComponentAbnormalLogs.setMoldId(nxpMoldComponent.getMoldId());
                    nxpMoldComponentAbnormalLogs.setMoldCode(nxpMoldComponent.getMoldCode());
                    nxpMoldComponentAbnormalLogs.setRemark(receiveComponentSubmitVO.getRemark());
                    nxpMoldComponentAbnormalLogs.setLogType(1);
                    nxpMoldComponentAbnormalLogs.setMoldType(nxpMold.getMoldType());
                    nxpMoldComponentAbnormalLogsService.insertNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs);

                    nxpMoldComponent.setStatus(2);
                    nxpMoldComponentService.updateNxpMoldComponent(nxpMoldComponent);
                }

                // 模具修改为异常状态
                nxpMold.setStatus(2);
                nxpMoldService.updateNxpMoldApi(nxpMold);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 模具列表筛选项下拉数据
     *
     * @return
     */
    @Override
    public AjaxResult searchData() {
        // 模具状态
        MoldStateDTO moldStateDTO1 = new MoldStateDTO();
        moldStateDTO1.setId(1);
        moldStateDTO1.setName("正常");
        MoldStateDTO moldStateDTO2 = new MoldStateDTO();
        moldStateDTO2.setId(2);
        moldStateDTO2.setName("异常");
        MoldStateDTO moldStateDTO3 = new MoldStateDTO();
        moldStateDTO3.setId(3);
        moldStateDTO3.setName("缺件");
        MoldStateDTO moldStateDTO4 = new MoldStateDTO();
        moldStateDTO4.setId(4);
        moldStateDTO4.setName("使用中");
        MoldStateDTO moldStateDTO5 = new MoldStateDTO();
        moldStateDTO5.setId(5);
        moldStateDTO5.setName("待维护");
        MoldStateDTO moldStateDTO6 = new MoldStateDTO();
        moldStateDTO6.setId(6);
        moldStateDTO6.setName("待归还");
        List<MoldStateDTO> moldStateDTOList = new ArrayList<>();
        moldStateDTOList.add(moldStateDTO1);
        moldStateDTOList.add(moldStateDTO2);
        moldStateDTOList.add(moldStateDTO3);
        moldStateDTOList.add(moldStateDTO4);
        moldStateDTOList.add(moldStateDTO5);
        moldStateDTOList.add(moldStateDTO6);
        // pkgType
        List<String> pkgTypes = nxpMoldService.selectPkgType();
        // 尺寸
//        List<String> lengths = nxpMoldService.selectLength();
//        NxpMachineModel nxpMachineModel = new NxpMachineModel();
//        nxpMachineModel.setStatus(1);
//        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectNxpMachineModelListNew(nxpMachineModel);
        // 查询所有mold的machineModel
        List<String> machineModes = nxpMoldService.selectMachineMode();
        Set<String> modelSet = new HashSet<>();
        for (String machineMode : machineModes) {
            if (machineMode.contains(",")){
                String[] split = machineMode.split(",");
                for (String s : split) {
                    modelSet.add(s);
                }
            }else {
                modelSet.add(machineMode);
            }
        }
        // 将set转换成list
        String[] array = modelSet.toArray(new String[0]);
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelService.selectBatchIds(array);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("moldState", moldStateDTOList);
        resultMap.put("pkgType", pkgTypes);
//        resultMap.put("length",lengths);
        resultMap.put("machineModels", nxpMachineModels);
        return AjaxResult.success(resultMap);
    }

    /**
     * 开锁
     *
     * @param nxpMold
     * @return
     */
    @Override
    public AjaxResult openLockOrPwd(NxpMold nxpMold) {
        if (StringUtils.isEmpty(nxpMold.getMoldCode())) {
            return AjaxResult.error("模具编号不能为空");
        }
        // 查询模具
        NxpMold mold = nxpMoldService.selectNxpMoldByCode(nxpMold.getMoldCode());
        if (mold == null) {
            return AjaxResult.error("模具不存在");
        }

        // 查询智能柜
        Long moldAddressId = mold.getMoldAddressId();
        NxpSmartCabinet nxpSmartCabinet = nxpSmartCabinetService.selectNxpSmartCabinetById(moldAddressId);
        if (nxpSmartCabinet == null) {
            return AjaxResult.error("模具未配置智能柜");
        }

        Map<String, Object> map = new HashMap<>();
        // 状态为4表示使用中，相当于在设备上
        /*if (mold.getStatus() == 4){
            map.put("cabinetState", 3);
            map.put("moldPwd", "");
            map.put("openState", 0);
            return AjaxResult.success(map);
        }*/

        if (StringUtils.isEmpty(nxpSmartCabinet.getPwd())) {
            // 表示智能锁
            map.put("cabinetState", 1);
            map.put("moldPwd", "");
            // 这里调用智能锁接口
            try {
                Map<String, String> result = tcpConnectService.sendOpenInstruct(nxpSmartCabinet.getIpAddress(), Integer.parseInt(nxpSmartCabinet.getPort()), nxpSmartCabinet.getBoxCode());
                String respData = result.get("respData");
                if (StringUtils.isNotEmpty(respData)) {
                    String[] s = respData.split(" ");
                    if (s.length >= 6) {
                        String switchStatus = s[4];
                        if (switchStatus.equals("70")) {
                            map.put("openState", 0);
                        } else {
                            map.put("openState", 1);
                        }
                    }
                }
                return AjaxResult.success(map);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("openState", 1);
                return AjaxResult.success(map);
            }
        }

        // 表示密码锁
        map.put("cabinetState", 2);
        map.put("moldPwd", nxpSmartCabinet.getPwd());
        map.put("openState", 0);
        return AjaxResult.success(map);
    }

    /**
     * 待更换列表删除
     *
     * @param replacedList
     * @return
     */
    @Override
    public AjaxResult replacedDel(ReplacedList replacedList) {
        if (replacedList.getReplaceId() == null) {
            return AjaxResult.error("replaceId不能为空");
        }
        if (replacedList.getUserInfoId() == null) {
            return AjaxResult.error("用户id不能为空");
        }
        NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelByUserInfoId(replacedList.getUserInfoId().intValue());
        if (nxpUserLabel == null){
            return AjaxResult.error("用户未配置权限");
        }
        ReplacedList replacedList1 = replacedListService.selectReplacedListById(replacedList.getReplaceId());
        if (replacedList1 == null) {
            return AjaxResult.error("数据不存在");
        }
        if (nxpUserLabel.getPosition() == 4 && replacedList1.getMoldType() == nxpUserLabel.getMoldType()){
            // 管理员
            Long moldId = replacedList1.getMoldId();
            NxpMold nxpMold = new NxpMold();
            nxpMold.setId(moldId);
            nxpMold.setStatus(1);
            nxpMoldService.updateNxpMoldApi(nxpMold);
            replacedListService.deleteReplacedListById(replacedList1.getReplaceId());
        }else {
            // 判断是否是提交人
            if (!replacedList1.getUserInfoId().equals(replacedList.getUserInfoId())) {
                return AjaxResult.error("提交人不符");
            }
            Long moldId = replacedList1.getMoldId();
            NxpMold nxpMold = new NxpMold();
            nxpMold.setId(moldId);
            nxpMold.setStatus(1);
            nxpMoldService.updateNxpMoldApi(nxpMold);
            replacedListService.deleteReplacedListById(replacedList1.getReplaceId());
        }
        return AjaxResult.success();
    }

    /**
     * 模具按钮右上角数字
     *
     * @param moldNumbersVO
     * @return
     */
    @Override
    public AjaxResult moldNumbers(MoldNumbersVO moldNumbersVO) {
        if (moldNumbersVO.getUserInfoId() == null) {
            return AjaxResult.error("用户id不能为空");
        }
        /*增加一个角拉
        1.操作员[没有]
        2.PM[待归还+待更换]
        3.模具维护人员[待维护]
        4.管理员[待归还+待更换+待维护]*/
        Map<String, Object> resultMap = new HashMap<>();
        NxpUserLabel nxpUserLabel = nxpUserLabelService.selectNxpUserLabelByUserInfoId(moldNumbersVO.getUserInfoId());
        if (nxpUserLabel == null) {
            resultMap.put("moldNumbers", 0);
            return AjaxResult.success(resultMap);
        }

        // 职位 1.操作员 2.PM 3.模具维护人员 4.管理员
        switch (nxpUserLabel.getPosition()) {
            case 1:
                resultMap.put("moldNumbers", 0);
                break;
            case 2:
                // pm 待归还+待更换
                ReplacedListVO replacedListVO = new ReplacedListVO();
                replacedListVO.setMoldType(nxpUserLabel.getMoldType());
                /*if (nxpUserLabel.getMoldType() == 1) {
                    replacedListVO.setProcess("9");
                } else {
                    replacedListVO.setProcess("6");
                }*/
                replacedListVO.setPageNum(null);
                replacedListVO.setPageSize(null);
                Integer replacedNum = 0;
                List<ReplacedList> replacedLists = replacedListService.selectReplacedListListByParams(replacedListVO);
                if (replacedLists != null) {
                    replacedNum = replacedLists.size();
                }

                Integer moldReturnNum = 0;
                NxpMoldReturn nxpMoldReturn = new NxpMoldReturn();
                nxpMoldReturn.setMoldType(nxpUserLabel.getMoldType());
                nxpMoldReturn.setStatus(1);
                nxpMoldReturn.setPageNum(null);
                nxpMoldReturn.setPageSize(null);
                List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnService.selectNxpMoldReturnListNew(nxpMoldReturn);
                if (nxpMoldReturns != null) {
                    moldReturnNum = nxpMoldReturns.size();
                }
                resultMap.put("moldNumbers", replacedNum + moldReturnNum);
                break;
            case 3:
                NxpMoldMaintenanceVO nxpMoldMaintenanceVO = new NxpMoldMaintenanceVO();
                nxpMoldMaintenanceVO.setMoldType(nxpUserLabel.getMoldType());
                nxpMoldMaintenanceVO.setPageNum(null);
                nxpMoldMaintenanceVO.setPageSize(null);
                List<NxpMoldMaintenance> nxpMoldMaintenances = nxpMoldMaintenanceService.selectNxpMoldMaintenanceListApiParams(nxpMoldMaintenanceVO);
                resultMap.put("moldNumbers", nxpMoldMaintenances == null ? 0 : nxpMoldMaintenances.size());
                break;
            case 4:
                ReplacedListVO replacedListVOAdmin = new ReplacedListVO();
                replacedListVOAdmin.setMoldType(nxpUserLabel.getMoldType());
                if (nxpUserLabel.getMoldType() == 1) {
                    replacedListVOAdmin.setProcess("9");
                } else {
                    replacedListVOAdmin.setProcess("6");
                }
                replacedListVOAdmin.setPageNum(null);
                replacedListVOAdmin.setPageSize(null);
                Integer replacedNumAdmin = 0;
                List<ReplacedList> replacedListsAdmin = replacedListService.selectReplacedListListByParams(replacedListVOAdmin);
                if (replacedListsAdmin != null) {
                    replacedNumAdmin = replacedListsAdmin.size();
                }

                Integer moldReturnNumAdmin = 0;
                NxpMoldReturn nxpMoldReturnAdmin = new NxpMoldReturn();
                nxpMoldReturnAdmin.setMoldType(nxpUserLabel.getMoldType());
                nxpMoldReturnAdmin.setStatus(1);
                nxpMoldReturnAdmin.setPageNum(null);
                nxpMoldReturnAdmin.setPageSize(null);
                List<NxpMoldReturn> nxpMoldReturnsAdmin = nxpMoldReturnService.selectNxpMoldReturnListNew(nxpMoldReturnAdmin);
                if (nxpMoldReturnsAdmin != null) {
                    moldReturnNumAdmin = nxpMoldReturnsAdmin.size();
                }

                Integer moldMaintenancesNumAdmin = 0;
                NxpMoldMaintenanceVO nxpMoldMaintenanceVOAdmin = new NxpMoldMaintenanceVO();
                nxpMoldMaintenanceVOAdmin.setMoldType(nxpUserLabel.getMoldType());
                nxpMoldMaintenanceVOAdmin.setPageNum(null);
                nxpMoldMaintenanceVOAdmin.setPageSize(null);
                List<NxpMoldMaintenance> nxpMoldMaintenancesAdmin = nxpMoldMaintenanceService.selectNxpMoldMaintenanceListApiParams(nxpMoldMaintenanceVOAdmin);
                if (nxpMoldMaintenancesAdmin != null) {
                    moldMaintenancesNumAdmin = nxpMoldMaintenancesAdmin.size();
                }
                resultMap.put("moldNumbers", replacedNumAdmin + moldReturnNumAdmin + moldMaintenancesNumAdmin);
                break;
            default:
                resultMap.put("moldNumbers", 0);
                break;
        }
        return AjaxResult.success(resultMap);
    }

    @Override
    public AjaxResult uploadNxpComponent() {
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListApi(new NxpMold());
        DynamicDataSourceContextHolder.clearDataSourceType();
        for (NxpMold nxpMold : nxpMolds) {
            // 先删除该模具的部件
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            nxpMoldComponentService.deleteNxpMoldComponentByMoldId(nxpMold.getId());
            DynamicDataSourceContextHolder.clearDataSourceType();
            // 新增部件
            String machineModelId = "";
            String machineModel = nxpMold.getMachineModel();
            if (StringUtils.isNotEmpty(machineModel)){
                if (machineModel.contains(",")){
                    machineModelId = machineModel.split(",")[0];
                }else {
                    machineModelId = machineModel;
                }
            }
            if (StringUtils.isNotEmpty(machineModelId)){
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                NxpMoldType nxpMoldType = nxpMoldTypeService.selectNxpMoldTypeByMachineModel(machineModelId);
                DynamicDataSourceContextHolder.clearDataSourceType();
                if (nxpMoldType != null){
                    List<NxpMoldTypeComponent> nxpMoldTypeComponentList = nxpMoldType.getNxpMoldTypeComponentList();
                    for (NxpMoldTypeComponent nxpMoldTypeComponent : nxpMoldTypeComponentList) {
                        NxpMoldComponent nxpMoldComponent = new NxpMoldComponent();
                        nxpMoldComponent.setMoldId(nxpMold.getId());
                        nxpMoldComponent.setMoldCode(nxpMold.getMoldCode());
                        nxpMoldComponent.setComponentName(nxpMoldTypeComponent.getComponentName());
                        nxpMoldComponent.setStatus(1);
                        nxpMoldComponent.setMoldTypeId(nxpMoldTypeComponent.getMoldTypeId());
                        nxpMoldComponent.setMoldRmsCode(nxpMold.getMoldRmsCode());
                        nxpMoldComponent.setComponentId(nxpMoldTypeComponent.getComponentId());
                        nxpMoldComponent.setMoldType(nxpMold.getMoldType());
                        nxpMoldComponent.setCreateTime(DateUtils.getNowDate());
                        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                        nxpMoldComponentService.insertNxpMoldComponentApi(nxpMoldComponent);
                        DynamicDataSourceContextHolder.clearDataSourceType();
                    }
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 通用上传请求
     */
    public Map<String, String> uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("fileName", fileName);
            resultMap.put("url", url);
            return resultMap;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    /**
     * 推荐模具
     *
     * @param machineModel
     * @param nxpMold
     * @return
     */
    private List<NxpMold> recommendedMold(String machineModel, NxpMold nxpMold) {
        //推荐模具 去掉自身编号推荐模具，用模具盒编号查询模具，拿到模具机型，反查模具，有推荐标识走推荐，无推荐标识走统计
        NxpMold paramsMold = new NxpMold();
        paramsMold.setMachineModel(machineModel);
        if (nxpMold != null) {
            paramsMold.setMoldCode(nxpMold.getMoldCode());
        }
        List<NxpMold> nxpMolds = nxpMoldService.selectNxpMoldListByMachineModel(paramsMold);

        // 这里通过模具再次查询部件，如果部件有异常则不推荐，优先推荐正常部件的模具
        List<NxpMold> paramsList = new ArrayList<>();
        for (NxpMold mold : nxpMolds) {
            List<NxpMoldComponent> components = nxpMoldComponentService.selectNxpMoldComponentByMoldCode(mold.getMoldCode());
            boolean b = false;
            loop:
            for (NxpMoldComponent moldComponent : components) {
                Integer status = moldComponent.getStatus();
                if (status != 1) {
                    b = false;
                    break loop;
                }
                b = true;
            }
            if (b) {
                paramsList.add(mold);
            }
            paramsList.add(mold);
        }

        Collections.sort(paramsList, (obj1, obj2) -> {
            if (obj1.getIsRecommend() > obj2.getIsRecommend()) {
                return 1;
            } else {
                return -1;
            }
        });
        return paramsList;
    }
}
