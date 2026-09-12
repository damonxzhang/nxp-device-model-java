package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcCar;

/**
 * CAR扣分Mapper接口
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
public interface NxpSwcCarMapper 
{
    /**
     * 查询CAR扣分
     * 
     * @param id CAR扣分ID
     * @return CAR扣分
     */
    public NxpSwcCar selectNxpSwcCarById(Long id);

    /**
     * 查询CAR扣分列表
     * 
     * @param nxpSwcCar CAR扣分
     * @return CAR扣分集合
     */
    public List<NxpSwcCar> selectNxpSwcCarList(NxpSwcCar nxpSwcCar);

    /**
     * 新增CAR扣分
     * 
     * @param nxpSwcCar CAR扣分
     * @return 结果
     */
    public int insertNxpSwcCar(NxpSwcCar nxpSwcCar);

    /**
     * 修改CAR扣分
     * 
     * @param nxpSwcCar CAR扣分
     * @return 结果
     */
    public int updateNxpSwcCar(NxpSwcCar nxpSwcCar);

    /**
     * 删除CAR扣分
     * 
     * @param id CAR扣分ID
     * @return 结果
     */
    public int deleteNxpSwcCarById(Long id);

    /**
     * 批量删除CAR扣分
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcCarByIds(String[] ids);
}
