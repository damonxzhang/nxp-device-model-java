package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.mapper.NxpFacilityMapper;
import com.ruoyi.system.mapper.NxpUserInfoMapper;
import com.ruoyi.system.service.INxpFacilityService;
import com.ruoyi.system.service.INxpUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpSwcCheckSelfMapper;
import com.ruoyi.system.domain.NxpSwcCheckSelf;
import com.ruoyi.system.service.INxpSwcCheckSelfService;
import com.ruoyi.common.core.text.Convert;

/**
 * SWC自查问题Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpSwcCheckSelfServiceImpl implements INxpSwcCheckSelfService
{
    @Autowired
    private NxpSwcCheckSelfMapper nxpSwcCheckSelfMapper;
    @Autowired
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Autowired
    private NxpFacilityMapper nxpFacilityMapper;

    /**
     * 查询SWC自查问题
     * 
     * @param id SWC自查问题ID
     * @return SWC自查问题
     */
    @Override
    public NxpSwcCheckSelf selectNxpSwcCheckSelfById(Long id)
    {
        return nxpSwcCheckSelfMapper.selectNxpSwcCheckSelfById(id);
    }

    /**
     * 查询SWC自查问题列表
     * 
     * @param nxpSwcCheckSelf SWC自查问题
     * @return SWC自查问题
     */
    @Override
    public List<NxpSwcCheckSelf> selectNxpSwcCheckSelfList(NxpSwcCheckSelf nxpSwcCheckSelf)
    {
        List<NxpSwcCheckSelf> nxpSwcCheckSelves = nxpSwcCheckSelfMapper.selectNxpSwcCheckSelfList(nxpSwcCheckSelf);
        for (NxpSwcCheckSelf swcCheckSelf : nxpSwcCheckSelves) {
            Long userId = swcCheckSelf.getUserId();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(userId);
            swcCheckSelf.setUserName(nxpUserInfo.getChinese_name());
            swcCheckSelf.setUserShift(nxpUserInfo.getShift());

//            增加设备名称
            String facilityId = swcCheckSelf.getFacilityId();
            String facilityNumber = "";
            if (facilityId.indexOf(",") != -1){
                // 多个
                String[] split = facilityId.split(",");
                List<NxpFacility> nxpFacilityList = nxpFacilityMapper.selectNxpFacilityByIds(split);
                for (NxpFacility nxpFacility : nxpFacilityList) {
                    facilityNumber = facilityNumber + nxpFacility.getNumber() + ",";
                }
            }
            else {
                // 单个
                NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(Long.valueOf(facilityId));
                if (nxpFacility != null){
                    facilityNumber = nxpFacility.getNumber();
                }
            }
            if (facilityNumber.indexOf(",") != -1){
                swcCheckSelf.setFacilityNumber(facilityNumber.substring(0,facilityNumber.length() - 1));
            }else {
                swcCheckSelf.setFacilityNumber(facilityNumber);
            }

        }

        return nxpSwcCheckSelves;
    }

    /**
     * 新增SWC自查问题
     * 
     * @param nxpSwcCheckSelf SWC自查问题
     * @return 结果
     */
    @Override
    public int insertNxpSwcCheckSelf(NxpSwcCheckSelf nxpSwcCheckSelf)
    {
        return nxpSwcCheckSelfMapper.insertNxpSwcCheckSelf(nxpSwcCheckSelf);
    }

    /**
     * 修改SWC自查问题
     * 
     * @param nxpSwcCheckSelf SWC自查问题
     * @return 结果
     */
    @Override
    public int updateNxpSwcCheckSelf(NxpSwcCheckSelf nxpSwcCheckSelf)
    {
        return nxpSwcCheckSelfMapper.updateNxpSwcCheckSelf(nxpSwcCheckSelf);
    }

    /**
     * 删除SWC自查问题对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcCheckSelfByIds(String ids)
    {
        return nxpSwcCheckSelfMapper.deleteNxpSwcCheckSelfByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除SWC自查问题信息
     * 
     * @param id SWC自查问题ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcCheckSelfById(Long id)
    {
        return nxpSwcCheckSelfMapper.deleteNxpSwcCheckSelfById(id);
    }
}
