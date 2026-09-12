package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OutPut;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 14:30
 */
public interface NxpSparePartOutPutMapper {
    /**
     * 提交备件领用
     * @param outPut
     * @return
     */
    int insertByOutPut(OutPut outPut);

    /**
     * 根据备件编号查询备件最后领取时间
     * @param sid
     * @return
     */
    String selectOutLastTimeBySid(String sid);
}
