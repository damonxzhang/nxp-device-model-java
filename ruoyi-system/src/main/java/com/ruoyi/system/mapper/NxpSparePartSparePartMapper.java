package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.DTO.PersonDTO;
import com.ruoyi.system.domain.Equipment;
import com.ruoyi.system.domain.NxpSparePartSparePart;
import com.ruoyi.system.domain.Use;
import com.ruoyi.system.domain.WareHouse;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 15:04
 */
public interface NxpSparePartSparePartMapper {
    /**
     * 获取备件详细信息
     * @return
     */
    NxpSparePartSparePart selectBySid(String s_id);

    /**
     * 查询列表
     * @param nxpSparePartSparePart
     * @return
     */
    List<NxpSparePartSparePart> selectByNxpSparePartSparePart(NxpSparePartSparePart nxpSparePartSparePart);

    int countByNxpSparePartSparePart(NxpSparePartSparePart nxpSparePartSparePart);

    /**
     * 通过wid查询仓库表
     * @param w_id
     * @return
     */
    WareHouse selectWareHouseByWid(String w_id);

    /**
     * 通过sid查询use
     * @param s_id
     * @return
     */
    Use selectUseBySid(String s_id);

    /**
     * 通过工序查询设备型号
     * @param equipment
     * @return
     */
    List<Equipment> getDeviceModel(Equipment equipment);
    List<Equipment> getDeviceModel1(Equipment equipment);

    /**
     * 更新
     * @param nxpSparePartSparePart
     * @return
     */
    int update(NxpSparePartSparePart nxpSparePartSparePart);

    /**
     * 根据pid查询person
     * @param pid
     * @return
     */
    PersonDTO selectPersonByPId(String pid);
}
