package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.NxpSparePartProcess;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 14:00
 */
public interface NxpSparePartProcessMapper {
    /**
     * 查询工序列表
     * @return
     */
    public List<NxpSparePartProcess> selectListByNxpSparePartProcess(NxpSparePartProcess nxpSparePartProcess);

}
