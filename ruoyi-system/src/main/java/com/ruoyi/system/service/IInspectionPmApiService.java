package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.NxpInspectionPmDTO;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 17:11
 */
public interface IInspectionPmApiService {
    /**
     * 获取pm检查结果
     * @param nxpInspectionPmCheck
     * @return
     */
    BaseVO getPmCheck(NxpInspectionPmCheck nxpInspectionPmCheck);

    /**
     * 获取pm复查结果
     * @param nxpInspectionPmCheck
     * @return
     */
    BaseVO getPmReview(NxpInspectionPmCheck nxpInspectionPmCheck);

    /**
     * 与我相关PM列表
     * @param nxpInspectionPm
     * @return
     */
    List<NxpInspectionPm> pmList(NxpInspectionPm nxpInspectionPm);
    List<NxpInspectionPm> pmListByFacility(NxpInspectionPm nxpInspectionPm);
    List<NxpInspectionPm> pmReviewListByFacility(NxpInspectionPm nxpInspectionPm);

    /**
     * 检查周PM内容
     * @param nxpInspectionPm
     * @return
     */
    NxpInspectionPmDTO pmInfo(NxpInspectionPm nxpInspectionPm);

    /**
     * 上传
     * @param picture
     * @return
     */
    BaseVO uploadFile(Picture picture);

    /**
     * 提交周PM检查项
     * @param paramsDTO
     * @return
     */
    BaseVO pmCheck(ParamsDTO paramsDTO);

    /**
     * 提交周PM复查项
     * @param pmReviewDTO
     * @return
     */
    BaseVO pmReview(PmReviewDTO pmReviewDTO);

    /**
     * pm 大列表
     * @param nxpInspectionPm
     * @return
     */
    BaseVO getPmList(NxpInspectionPm nxpInspectionPm);
}
