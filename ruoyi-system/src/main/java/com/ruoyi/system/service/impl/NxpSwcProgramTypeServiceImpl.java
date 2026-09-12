package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpSwcProgramGroup;
import com.ruoyi.system.mapper.NxpSwcProgramGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.NxpSwcProgram;
import com.ruoyi.system.mapper.NxpSwcProgramTypeMapper;
import com.ruoyi.system.domain.NxpSwcProgramType;
import com.ruoyi.system.service.INxpSwcProgramTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查问题类型Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpSwcProgramTypeServiceImpl implements INxpSwcProgramTypeService
{
    @Autowired
    private NxpSwcProgramTypeMapper nxpSwcProgramTypeMapper;
    @Autowired
    private NxpSwcProgramGroupMapper nxpSwcProgramGroupMapper;

    /**
     * 查询检查问题类型
     * 
     * @param id 检查问题类型ID
     * @return 检查问题类型
     */
    @Override
    public NxpSwcProgramType selectNxpSwcProgramTypeById(Long id)
    {
        return nxpSwcProgramTypeMapper.selectNxpSwcProgramTypeById(id);
    }

    /**
     * 查询检查问题类型列表
     * 
     * @param nxpSwcProgramType 检查问题类型
     * @return 检查问题类型
     */
    @Override
    public List<NxpSwcProgramType> selectNxpSwcProgramTypeList(NxpSwcProgramType nxpSwcProgramType)
    {
        List<NxpSwcProgramType> nxpSwcProgramTypes = nxpSwcProgramTypeMapper.selectNxpSwcProgramTypeList(nxpSwcProgramType);
        for (NxpSwcProgramType swcProgramType : nxpSwcProgramTypes) {
            Long groupId = swcProgramType.getGroupId();
            NxpSwcProgramGroup nxpSwcProgramGroup = nxpSwcProgramGroupMapper.selectNxpSwcProgramGroupById(groupId);
            swcProgramType.setGroupName(nxpSwcProgramGroup.getGroupName());
        }
        return nxpSwcProgramTypes;
    }

    /**
     * 新增检查问题类型
     * 
     * @param nxpSwcProgramType 检查问题类型
     * @return 结果
     */
    @Transactional
    @Override
    public int insertNxpSwcProgramType(NxpSwcProgramType nxpSwcProgramType)
    {
        nxpSwcProgramType.setCreatedAt(new Date());
        int rows = nxpSwcProgramTypeMapper.insertNxpSwcProgramType(nxpSwcProgramType);
        insertNxpSwcProgram(nxpSwcProgramType);
        return rows;
    }

    /**
     * 修改检查问题类型
     * 
     * @param nxpSwcProgramType 检查问题类型
     * @return 结果
     */
    @Transactional
    @Override
    public int updateNxpSwcProgramType(NxpSwcProgramType nxpSwcProgramType)
    {
        nxpSwcProgramTypeMapper.deleteNxpSwcProgramByTypeId(nxpSwcProgramType.getId());
        insertNxpSwcProgram(nxpSwcProgramType);
        return nxpSwcProgramTypeMapper.updateNxpSwcProgramType(nxpSwcProgramType);
    }

    /**
     * 删除检查问题类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteNxpSwcProgramTypeByIds(String ids)
    {
        nxpSwcProgramTypeMapper.deleteNxpSwcProgramByTypeIds(Convert.toStrArray(ids));
        return nxpSwcProgramTypeMapper.deleteNxpSwcProgramTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查问题类型信息
     * 
     * @param id 检查问题类型ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcProgramTypeById(Long id)
    {
        nxpSwcProgramTypeMapper.deleteNxpSwcProgramByTypeId(id);
        return nxpSwcProgramTypeMapper.deleteNxpSwcProgramTypeById(id);
    }

    @Override
    public List<NxpSwcProgramType> selectNxpSwcProgramTypeByParam(NxpSwcProgramType nxpSwcProgramType) {
        List<NxpSwcProgramType> nxpSwcProgramTypes = nxpSwcProgramTypeMapper.selectNxpSwcProgramTypeByParam(nxpSwcProgramType);
        return nxpSwcProgramTypes;
    }

    /**
     * 新增${subTable.functionName}信息
     * 
     * @param nxpSwcProgramType 检查问题类型对象
     */
    public void insertNxpSwcProgram(NxpSwcProgramType nxpSwcProgramType)
    {
        List<NxpSwcProgram> nxpSwcProgramList = nxpSwcProgramType.getNxpSwcProgramList();
        Long id = nxpSwcProgramType.getId();
        if (StringUtils.isNotNull(nxpSwcProgramList))
        {
            List<NxpSwcProgram> list = new ArrayList<NxpSwcProgram>();
            for (NxpSwcProgram nxpSwcProgram : nxpSwcProgramList)
            {
                nxpSwcProgram.setCreatedAt(new Date());
                nxpSwcProgram.setTypeId(id);
                list.add(nxpSwcProgram);
            }
            if (list.size() > 0)
            {
                nxpSwcProgramTypeMapper.batchNxpSwcProgram(list);
            }
        }
    }
}
