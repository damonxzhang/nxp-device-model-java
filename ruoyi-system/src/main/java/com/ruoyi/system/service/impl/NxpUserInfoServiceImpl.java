package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpFacilityUser;
import com.ruoyi.system.mapper.NxpFacilityUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpUserInfoMapper;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.service.INxpUserInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户信息Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpUserInfoServiceImpl implements INxpUserInfoService 
{
    @Autowired
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Autowired
    private NxpFacilityUserMapper nxpFacilityUserMapper;

    /**
     * 查询用户信息
     * 
     * @param id 用户信息ID
     * @return 用户信息
     */
    @Override
    public NxpUserInfo selectNxpUserInfoById(Long id)
    {
        return nxpUserInfoMapper.selectNxpUserInfoById(id);
    }

    /**
     * 查询用户信息列表
     * 
     * @param nxpUserInfo 用户信息
     * @return 用户信息
     */
    @Override
    public List<NxpUserInfo> selectNxpUserInfoList(NxpUserInfo nxpUserInfo)
    {
        return nxpUserInfoMapper.selectNxpUserInfoList(nxpUserInfo);
    }

    /**
     * 查询用户信息列表
     *
     * @param nxpUserInfo 用户信息
     * @return 用户信息
     */
    @Override
    public List<NxpUserInfo> selectNxpUserInfoListByFacility(NxpUserInfo nxpUserInfo,Long id)
    {
        NxpUserInfo nxpUserInfo1 = new NxpUserInfo();
        nxpUserInfo1.setStatus(1);
        List<NxpUserInfo> allUserInfo = nxpUserInfoMapper.selectNxpUserInfoList(nxpUserInfo1);

        List<NxpFacilityUser> nxpFacilityUsers = nxpFacilityUserMapper.selectNxpFacilityUserByFacilityId(id);

        for (NxpUserInfo userInfo : allUserInfo) {
            for (NxpFacilityUser nxpFacilityUser : nxpFacilityUsers) {
                if (userInfo.getId().longValue() == nxpFacilityUser.getUserId().longValue()){
                    userInfo.setFlag(true);
                    break;
                }
            }
        }

        return allUserInfo;
    }

    /**
     * 新增用户信息
     * 
     * @param nxpUserInfo 用户信息
     * @return 结果
     */
    @Override
    public int insertNxpUserInfo(NxpUserInfo nxpUserInfo)
    {
        return nxpUserInfoMapper.insertNxpUserInfo(nxpUserInfo);
    }

    /**
     * 修改用户信息
     * 
     * @param nxpUserInfo 用户信息
     * @return 结果
     */
    @Override
    public int updateNxpUserInfo(NxpUserInfo nxpUserInfo)
    {
        return nxpUserInfoMapper.updateNxpUserInfo(nxpUserInfo);
    }

    /**
     * 删除用户信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpUserInfoByIds(String ids)
    {
        return nxpUserInfoMapper.deleteNxpUserInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户信息信息
     * 
     * @param id 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteNxpUserInfoById(Long id)
    {
        return nxpUserInfoMapper.deleteNxpUserInfoById(id);
    }
}
