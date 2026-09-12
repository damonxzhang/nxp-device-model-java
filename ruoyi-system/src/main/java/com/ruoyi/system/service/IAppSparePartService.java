package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.Equipment;
import com.ruoyi.system.domain.NxpSparePartSparePart;
import com.ruoyi.system.domain.OutPut;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 13:55
 */
public interface IAppSparePartService {
    /**
     * 备件工序列表
     * @return
     */
    BaseVO processList();

    /**
     * 提交备件领用
     * @param outPut
     * @return
     */
    BaseVO receiveSparePart(OutPut outPut);

    /**
     * 获取备件详细信息
     * @param nxpSparePartSparePart
     * @return
     */
    BaseVO detail(NxpSparePartSparePart nxpSparePartSparePart);

    /**
     * 备件列表
     * @param nxpSparePartSparePart
     * @return
     */
    BaseVO sparePartList(NxpSparePartSparePart nxpSparePartSparePart);

    /**
     * 通过工序获取设备型号
     * @param equipment
     * @return
     */
    BaseVO getDeviceType(Equipment equipment);

    /**
     * 通过工序获取设备型号
     * @param equipment
     * @return
     */
    BaseVO getDeviceModel(Equipment equipment);

}
