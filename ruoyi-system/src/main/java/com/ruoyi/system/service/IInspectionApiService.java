package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.*;

import java.util.List;

/**
 * 报修Service
 * @author Yancheng Liu
 * @create 2021-02-05 16:41
 */
public interface IInspectionApiService {

    /**
     * 查询报修信息
     * @param nxpInspectionService
     * @return
     */
    BaseVO inspectionInfo(NxpInspectionService nxpInspectionService);

    /**
     * 新增报修信息
     * @param nxpInspection
     * @return
     */
    BaseVO inspectionCreate(NxpInspection nxpInspection);

    /**
     * 查询维修详情信息
     * @param nxpInspectionService
     * @return
     */
    BaseVO inspectionServiceInfo(NxpInspectionService nxpInspectionService);

    /**
     * 查询指定设备历史报修列表
     * @param nxpInspection
     * @return
     */
    BaseVO inspectionList(NxpInspection nxpInspection);

    /**
     * 提交维修信息
     * @param nxpInspectionService
     * @return
     */
    BaseVO inspectionServiceCreate(NxpInspectionService nxpInspectionService);
    BaseVO inspectionServiceCreateNew(NxpInspectionService nxpInspectionService);

    /**
     * 设备维修列表
     * @param nxpInspectionService
     * @return
     */
    List<NxpInspectionService> inspectionServiceList(NxpInspectionService nxpInspectionService);

    /**
     * 更新维修状态
     * @param nxpInspection
     * @return
     */
    BaseVO inspectionServiceUpdate(NxpInspection nxpInspection);

    /**
     * 上传图片
     * @param picture
     * @return
     */
    BaseVO uploadFile(Picture picture);

    /**
     * 位置列表
     * @return
     */
    BaseVO getPosition(NxpFacility nxpFacility);

    /**
     * 内容列表
     * @return
     */
    BaseVO getContent(NxpInspectionContent nxpInspectionContent);

    /**
     * 设备列表
     * @param nxpFacility
     * @return
     */
    BaseVO selectFacilityList(NxpFacility nxpFacility);

    /**
     * 设备详情
     * @param nxpFacility
     * @return
     */
    BaseVO facility(NxpFacility nxpFacility);

    /**
     * 设备问题类型
     * @return
     */
    BaseVO inspectionQuestionType(NxpMaintainType nxpMaintainType);

    /**
     * 待处理设备列表
     * @param nxpInspection
     * @return
     */
    BaseVO pendingInspection(NxpInspection nxpInspection);

    /**
     * 获取工序列表
     * @return
     */
    BaseVO getProcess();

    /**
     * 提交IR（报修确认）
     * @param nxpInspection
     * @return
     */
    BaseVO submitIr(NxpInspection nxpInspection);

    /**
     * 更新保修状态为维修中
     * @param nxpInspection
     * @return
     */
    BaseVO updateInspectionServiceStatus(NxpInspection nxpInspection);

    /**
     * 设备维修记录
     * @param nxpInspectionService
     * @return
     */
    BaseVO inspectionServiceRecord(NxpInspectionService nxpInspectionService);

    /**
     * 中止维修原因列表
     * @return
     */
    BaseVO inspectionAboutReason();

    /**
     * 无需维修原因
     * @return
     */
    BaseVO inspectionMaintenanceReason();

    /**
     * 设备报修列表
     * @param nxpInspection
     * @return
     */
    BaseVO inspectionListByFacilityId(NxpInspection nxpInspection);

    /**
     * 检查报修设备状态
     * @param nxpFacility
     * @return
     */
    BaseVO checkFacilityInspectionStatus(NxpFacility nxpFacility);

    /**
     * 维修详情接口
     * @param nxpInspectionService
     * @return
     */
    BaseVO inspectionServiceDetails(NxpInspectionService nxpInspectionService);
}
