package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpTeam;
import com.ruoyi.system.mapper.NxpTeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpSwcCarMapper;
import com.ruoyi.system.domain.NxpSwcCar;
import com.ruoyi.system.service.INxpSwcCarService;
import com.ruoyi.common.core.text.Convert;

/**
 * CAR扣分Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
@Service
//@DataSource(value = DataSourceType.SLAVE)

public class NxpSwcCarServiceImpl implements INxpSwcCarService 
{
    @Autowired
    private NxpSwcCarMapper nxpSwcCarMapper;
    @Autowired
    private NxpTeamMapper nxpTeamMapper;

    /**
     * 查询CAR扣分
     * 
     * @param id CAR扣分ID
     * @return CAR扣分
     */
    @Override
    public NxpSwcCar selectNxpSwcCarById(Long id)
    {
        return nxpSwcCarMapper.selectNxpSwcCarById(id);
    }

    /**
     * 查询CAR扣分列表
     * 
     * @param nxpSwcCar CAR扣分
     * @return CAR扣分
     */
    @Override
    public List<NxpSwcCar> selectNxpSwcCarList(NxpSwcCar nxpSwcCar)
    {
        List<NxpSwcCar> nxpSwcCars = nxpSwcCarMapper.selectNxpSwcCarList(nxpSwcCar);
        for (NxpSwcCar swcCar : nxpSwcCars) {
            Long gembaGroupId1 = swcCar.getGembaGroupId();
            NxpTeam nxpTeam = nxpTeamMapper.selectNxpTeamById(gembaGroupId1);
            if (nxpTeam != null){
                swcCar.setGembaName(nxpTeam.getTeamName());
            }
        }
        return nxpSwcCars;
    }

    /**
     * 新增CAR扣分
     * 
     * @param nxpSwcCar CAR扣分
     * @return 结果
     */
    @Override
    public int insertNxpSwcCar(NxpSwcCar nxpSwcCar)
    {
        nxpSwcCar.setCreatedAt(new Date());
        return nxpSwcCarMapper.insertNxpSwcCar(nxpSwcCar);
    }

    /**
     * 修改CAR扣分
     * 
     * @param nxpSwcCar CAR扣分
     * @return 结果
     */
    @Override
    public int updateNxpSwcCar(NxpSwcCar nxpSwcCar)
    {
        return nxpSwcCarMapper.updateNxpSwcCar(nxpSwcCar);
    }

    /**
     * 删除CAR扣分对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcCarByIds(String ids)
    {
        return nxpSwcCarMapper.deleteNxpSwcCarByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除CAR扣分信息
     * 
     * @param id CAR扣分ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcCarById(Long id)
    {
        return nxpSwcCarMapper.deleteNxpSwcCarById(id);
    }
}
