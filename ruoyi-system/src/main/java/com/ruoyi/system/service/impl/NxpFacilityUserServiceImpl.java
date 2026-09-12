package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.NxpFacilityListDTO;
import com.ruoyi.system.mapper.NxpFacilityMapper;
import com.ruoyi.system.mapper.NxpMachineProcessMapper;
import com.ruoyi.system.mapper.NxpUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpFacilityUserMapper;
import com.ruoyi.system.service.INxpFacilityUserService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 设备相关人员Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-04-09
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpFacilityUserServiceImpl implements INxpFacilityUserService 
{
    @Autowired
    private NxpFacilityUserMapper nxpFacilityUserMapper;
    @Resource
    private NxpFacilityMapper nxpFacilityMapper;
    @Resource
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Resource
    private NxpMachineProcessMapper nxpMachineProcessMapper;

    /**
     * 查询设备相关人员
     * 
     * @param id 设备相关人员ID
     * @return 设备相关人员
     */
    @Override
    public NxpFacilityUser selectNxpFacilityUserById(Long id)
    {
        return nxpFacilityUserMapper.selectNxpFacilityUserById(id);
    }

    /**
     * 通过facilityId查询设备相关人员
     *
     * @param id 设备相关人员ID
     * @return 设备相关人员
     */
    @Override
    public List<NxpFacilityUser> selectNxpFacilityUserByFacilityId(Long id)
    {
        return nxpFacilityUserMapper.selectNxpFacilityUserByFacilityId(id);
    }

    /**
     * 查询设备相关人员列表
     * 
     * @param nxpFacilityUser 设备相关人员
     * @return 设备相关人员
     */
    @Override
    public List<NxpFacilityUser> selectNxpFacilityUserList(NxpFacilityUser nxpFacilityUser)
    {

        List<NxpFacilityUser> nxpFacilityUsers1 = nxpFacilityUserMapper.selectNxpFacilityUserGroupByFacilityId(nxpFacilityUser);
        for (NxpFacilityUser facilityUser : nxpFacilityUsers1) {

            // 设备名称
            Long facilityId = facilityUser.getFacilityId();
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
            if (nxpFacility != null){
                facilityUser.setFacilityName(nxpFacility.getNumber());
                NxpMachineProcess nxpMachineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess()));
                if (nxpMachineProcess != null){
                    facilityUser.setFacilityProcess(nxpMachineProcess.getName());

                }
            }

            // 人员名称
            String userNames = "";
            List<NxpFacilityUser> nxpFacilityUsers = nxpFacilityUserMapper.selectNxpFacilityUserByFacilityId(facilityId);
            for (NxpFacilityUser user : nxpFacilityUsers) {
                Long userId = user.getUserId();
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(userId);
                if (nxpUserInfo != null)
                    userNames = userNames + nxpUserInfo.getChinese_name() + "," ;
            }
            facilityUser.setUserNames(userNames);

        }
        return nxpFacilityUsers1;

//        List<NxpFacilityUser> nxpFacilityUsers = nxpFacilityUserMapper.selectNxpFacilityUserList(nxpFacilityUser);
//        for (NxpFacilityUser facilityUser : nxpFacilityUsers) {
//            // 设备名称
//            Long facilityId = facilityUser.getFacilityId();
//            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
//            if (nxpFacility != null){
//                facilityUser.setFacilityName(nxpFacility.getNumber());
//            }
//            // 人员名称
//            Long userId = facilityUser.getUserId();
//            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(userId);
//            if (nxpUserInfo != null){
//                facilityUser.setUserName(nxpUserInfo.getChinese_name());
//            }
//        }
//        return nxpFacilityUsers;
    }

    /**
     * 新增设备相关人员
     * 
     * @param nxpFacilityUser 设备相关人员
     * @return 结果
     */
    @Override
    public int insertNxpFacilityUser(NxpFacilityUser nxpFacilityUser)
    {

        nxpFacilityUserMapper.deleteNxpFacilityUserByFacilityId(nxpFacilityUser.getFacilityId());
        String userIds = nxpFacilityUser.getUserIds();
        if (userIds.indexOf(",") != -1){
            String[] split = userIds.split(",");
            for (String s : split) {
                NxpFacilityUser nxpFacilityUser1 = new NxpFacilityUser();
                nxpFacilityUser1.setFacilityId(nxpFacilityUser.getFacilityId());
                nxpFacilityUser1.setUserId(Long.valueOf(s));
                nxpFacilityUserMapper.insertNxpFacilityUser(nxpFacilityUser1);
            }
        }
        else {
            NxpFacilityUser nxpFacilityUser1 = new NxpFacilityUser();
            nxpFacilityUser1.setFacilityId(nxpFacilityUser.getFacilityId());
            nxpFacilityUser1.setUserId(Long.valueOf(userIds));
            nxpFacilityUserMapper.insertNxpFacilityUser(nxpFacilityUser1);
        }

        return 1;

    }

    /**
     * 修改设备相关人员
     * 
     * @param nxpFacilityUser 设备相关人员
     * @return 结果
     */
    @Override
    public int updateNxpFacilityUser(NxpFacilityUser nxpFacilityUser)
    {

//        return nxpFacilityUserMapper.updateNxpFacilityUser(nxpFacilityUser);
        nxpFacilityUserMapper.deleteNxpFacilityUserByFacilityId(nxpFacilityUser.getId());
        String userIds = nxpFacilityUser.getUserIds();
        if (userIds.indexOf(",") != -1){
            String[] split = userIds.split(",");
            for (String s : split) {
                NxpFacilityUser nxpFacilityUser1 = new NxpFacilityUser();
                nxpFacilityUser1.setFacilityId(nxpFacilityUser.getId());
                nxpFacilityUser1.setUserId(Long.valueOf(s));
                nxpFacilityUserMapper.insertNxpFacilityUser(nxpFacilityUser1);
            }
        }
        else {
            NxpFacilityUser nxpFacilityUser1 = new NxpFacilityUser();
            nxpFacilityUser1.setFacilityId(nxpFacilityUser.getId());
            nxpFacilityUser1.setUserId(Long.valueOf(userIds));
            nxpFacilityUserMapper.insertNxpFacilityUser(nxpFacilityUser1);
        }

        return 1;
    }

    /**
     * 删除设备相关人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpFacilityUserByIds(String ids)
    {
        return nxpFacilityUserMapper.deleteNxpFacilityUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备相关人员信息
     * 
     * @param id 设备相关人员ID
     * @return 结果
     */
    @Override
    public int deleteNxpFacilityUserById(Long id)
    {
        return nxpFacilityUserMapper.deleteNxpFacilityUserById(id);
    }

    @Override
    @Transactional
    public BaseVO initFacilityUser() {
        List<NxpFacilityListDTO> nxpFacilityListDTOS = nxpFacilityMapper.selectFacilityList(new NxpFacility());
        int i = 0;
        for (NxpFacilityListDTO nxpFacilityListDTO : nxpFacilityListDTOS) {
            NxpFacilityUser nxpFacilityUser = new NxpFacilityUser();
            nxpFacilityUser.setFacilityId(Long.valueOf(nxpFacilityListDTO.getId()));
            nxpFacilityUser.setUserId(1L);
            i += nxpFacilityUserMapper.insertNxpFacilityUser(nxpFacilityUser);
        }
        return BaseVO.success(i);
    }
}
