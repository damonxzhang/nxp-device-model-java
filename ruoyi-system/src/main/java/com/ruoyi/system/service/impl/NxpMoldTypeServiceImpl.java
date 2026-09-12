package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.NxpMachineModel;
import com.ruoyi.system.mapper.NxpMachineModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.NxpMoldTypeComponent;
import com.ruoyi.system.mapper.NxpMoldTypeMapper;
import com.ruoyi.system.domain.NxpMoldType;
import com.ruoyi.system.service.INxpMoldTypeService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 部件种类Service业务层处理
 *
 * @author YanCheng Liu
 * @date 2023-10-06
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldTypeServiceImpl implements INxpMoldTypeService {
    @Resource
    private NxpMoldTypeMapper nxpMoldTypeMapper;
    @Resource
    private NxpMachineModelMapper nxpMachineModelMapper;

    /**
     * 查询部件种类
     *
     * @param id 部件种类ID
     * @return 部件种类
     */
    @Override
    public NxpMoldType selectNxpMoldTypeById(Long id) {
        return nxpMoldTypeMapper.selectNxpMoldTypeById(id);
    }

    /**
     * 查询部件种类列表
     *
     * @param nxpMoldType 部件种类
     * @return 部件种类
     */
    @Override
    public List<NxpMoldType> selectNxpMoldTypeList(NxpMoldType nxpMoldType) {
        List<NxpMoldType> nxpMoldTypes = nxpMoldTypeMapper.selectNxpMoldTypeList(nxpMoldType);
        for (NxpMoldType moldType : nxpMoldTypes) {
            String machineModelName = "";
            String machineModel = moldType.getMachineModel();
            if (StringUtils.isNotEmpty(machineModel)){
                if (machineModel.contains(",")) {
                    String[] split = machineModel.split(",");
                    for (String machineModelId : split) {
                        NxpMachineModel nxpMachineModel = nxpMachineModelMapper.selectNxpMachineModelById(Long.valueOf(machineModelId));
                        machineModelName = machineModelName + nxpMachineModel.getName() + ",";
                    }
                } else {
                    NxpMachineModel nxpMachineModel = nxpMachineModelMapper.selectNxpMachineModelById(Long.valueOf(machineModel));
                    machineModelName = machineModelName + nxpMachineModel.getName() + ",";
                }
            }
            if (StringUtils.isNotEmpty(machineModelName)) {
                moldType.setMachineModelName(machineModelName.substring(0, machineModelName.length() - 1));
            }
        }
        return nxpMoldTypes;
    }

    /**
     * 新增部件种类
     *
     * @param nxpMoldType 部件种类
     * @return 结果
     */
    @Transactional
    @Override
    public int insertNxpMoldType(NxpMoldType nxpMoldType) {
        nxpMoldType.setCreateTime(DateUtils.getNowDate());
        int rows = nxpMoldTypeMapper.insertNxpMoldType(nxpMoldType);
        insertNxpMoldTypeComponent(nxpMoldType);
        return rows;
    }

    /**
     * 修改部件种类
     *
     * @param nxpMoldType 部件种类
     * @return 结果
     */
    @Transactional
    @Override
    public int updateNxpMoldType(NxpMoldType nxpMoldType) {
        nxpMoldType.setUpdateTime(DateUtils.getNowDate());
        List<NxpMoldTypeComponent> nxpMoldTypeComponentList = nxpMoldType.getNxpMoldTypeComponentList();
        for (NxpMoldTypeComponent nxpMoldTypeComponent : nxpMoldTypeComponentList) {
            if (nxpMoldTypeComponent.getComponentId() == null) {
                // 如果主键id为空，则进行新增操作
                nxpMoldTypeComponent.setMoldTypeId(nxpMoldType.getId());
                nxpMoldTypeMapper.insertNxpMoldTypeComponent(nxpMoldTypeComponent);
            } else {
                // 主键不为空，做更新操作
                nxpMoldTypeMapper.updateNxpMoldTypeComponent(nxpMoldTypeComponent);
            }
        }
        return nxpMoldTypeMapper.updateNxpMoldType(nxpMoldType);
    }

    /**
     * 删除部件种类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteNxpMoldTypeByIds(String ids) {
        nxpMoldTypeMapper.deleteNxpMoldTypeComponentByMoldTypeIds(Convert.toStrArray(ids));
        return nxpMoldTypeMapper.deleteNxpMoldTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除部件种类信息
     *
     * @param id 部件种类ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldTypeById(Long id) {
        nxpMoldTypeMapper.deleteNxpMoldTypeComponentByMoldTypeId(id);
        return nxpMoldTypeMapper.deleteNxpMoldTypeById(id);
    }

    @Override
    public List<NxpMoldTypeComponent> getByMoldTypeId(NxpMoldTypeComponent nxpMoldTypeComponent) {
        nxpMoldTypeComponent.setStatus(1);
        return nxpMoldTypeMapper.getByMoldTypeId(nxpMoldTypeComponent);
    }

    @Override
    public NxpMoldTypeComponent selectByComponentId(Long componentId) {
        return nxpMoldTypeMapper.getByComponentId(componentId);
    }

    @Override
    public List<NxpMoldType> selectNxpMoldTypeListAdmin(NxpMoldType nxpMoldType) {
        return nxpMoldTypeMapper.selectNxpMoldTypeList(nxpMoldType);
    }

    @Override
    public NxpMoldType selectNxpMoldTypeByMachineModel(String machineModelId) {
        return nxpMoldTypeMapper.selectNxpMoldTypeByMachineModel(machineModelId);
    }

    /**
     * 新增模具部件信息
     *
     * @param nxpMoldType 部件种类对象
     */
    public void insertNxpMoldTypeComponent(NxpMoldType nxpMoldType) {
        List<NxpMoldTypeComponent> nxpMoldTypeComponentList = nxpMoldType.getNxpMoldTypeComponentList();
        Long id = nxpMoldType.getId();
        if (StringUtils.isNotNull(nxpMoldTypeComponentList)) {
            List<NxpMoldTypeComponent> list = new ArrayList<NxpMoldTypeComponent>();
            for (NxpMoldTypeComponent nxpMoldTypeComponent : nxpMoldTypeComponentList) {
                nxpMoldTypeComponent.setMoldTypeId(id);
                list.add(nxpMoldTypeComponent);
            }
            if (list.size() > 0) {
                nxpMoldTypeMapper.batchNxpMoldTypeComponent(list);
            }
        }
    }


}
