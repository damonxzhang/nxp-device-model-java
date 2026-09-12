package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.NxpMoldDTO;
import com.ruoyi.system.mapper.*;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpMoldService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具管理Service业务层处理
 *
 * @author YanCheng Liu
 * @date 2023-06-23
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldServiceImpl implements INxpMoldService {

    @Resource
    private NxpMoldMapper nxpMoldMapper;
    @Resource
    private NxpProductTypeMapper nxpProductTypeMapper;
    @Resource
    private NxpSmartCabinetMapper nxpSmartCabinetMapper;
    @Resource
    private NxpMoldComponentMapper nxpMoldComponentMapper;
    @Resource
    private NxpMachineModelMapper nxpMachineModelMapper;
    @Resource
    private NxpMoldTypeMapper nxpMoldTypeMapper;
    @Resource
    private NxpFacilityMapper nxpFacilityMapper;

    /**
     * 查询模具管理
     *
     * @param id 模具管理ID
     * @return 模具管理
     */
    @Override
    public NxpMold selectNxpMoldById(Long id) {
        NxpMold mold = nxpMoldMapper.selectNxpMoldById(id);
        return mold;
    }

    /**
     * 查询模具管理
     *
     * @param facilityId 模具管理ID
     * @return 模具管理
     */
    @Override
    public NxpMold selectNxpMoldByFacility(Long facilityId) {
        return nxpMoldMapper.selectNxpMoldByFacility(facilityId);
    }

    /**
     * 查询模具管理列表
     *
     * @param nxpMold 模具管理
     * @return 模具管理
     */
    @Override
    public List<NxpMold> selectNxpMoldList(NxpMold nxpMold) {
        List<NxpMold> nxpMolds = nxpMoldMapper.selectNxpMoldList(nxpMold);
        for (NxpMold mold : nxpMolds) {
            String machineModel = mold.getMachineModel();
            String machineModelName = "";
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
                mold.setMachineModelName(machineModelName.substring(0, machineModelName.length() - 1));
            }
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(mold.getFacilityId().longValue());
            if (nxpFacility != null){
                mold.setFacilityNumber(nxpFacility.getNumber());
            }
        }
        return nxpMolds;
    }

    @Override
    public List<NxpMold> selectNxpMoldListApi(NxpMold nxpMold) {
        return nxpMoldMapper.selectNxpMoldList(nxpMold);
    }

    /**
     * 新增模具管理
     *
     * @param nxpMold 模具管理
     * @return 结果
     */
    @Override
    public int insertNxpMold(NxpMold nxpMold) {
        nxpMold.setFacilityId(0);
        if (nxpMold.getMoldAddressId() != null){
            NxpSmartCabinet nxpSmartCabinet = nxpSmartCabinetMapper.selectNxpSmartCabinetById(nxpMold.getMoldAddressId());
            if (nxpSmartCabinet != null){
                nxpMold.setMoldAddress(nxpSmartCabinet.getPosition());
            }
        }
        nxpMold.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isNotEmpty(nxpMold.getProductTypeId())){
            String productTypeName = "";
            String productTypeId = nxpMold.getProductTypeId();
            if (productTypeId.contains(",")){
                String[] split = productTypeId.split(",");
                for (String productTypeIdStr : split) {
                    NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(productTypeIdStr));
                    productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
                }
            }else {
                NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(productTypeId));
                productTypeName = nxpProductType.getProductTypeName();
            }
            nxpMold.setProductTypeName(productTypeName.contains(",") ? productTypeName.substring(0,productTypeName.length() - 1) :productTypeName);
        }
        int i = nxpMoldMapper.insertNxpMold(nxpMold);

        // 新增部件
        String machineModelId = "";
        String machineModel = nxpMold.getMachineModel();
        if (StringUtils.isNotEmpty(machineModel)){
            if (machineModel.contains(",")){
                machineModelId = machineModel.split(",")[0];
            }else {
                machineModelId = machineModel;
            }
        }
        if (StringUtils.isNotEmpty(machineModelId)){
            NxpMoldType nxpMoldType = nxpMoldTypeMapper.selectNxpMoldTypeByMachineModel(machineModelId);
            if (nxpMoldType != null){
                List<NxpMoldTypeComponent> nxpMoldTypeComponentList = nxpMoldType.getNxpMoldTypeComponentList();
                for (NxpMoldTypeComponent nxpMoldTypeComponent : nxpMoldTypeComponentList) {
                    NxpMoldComponent nxpMoldComponent = new NxpMoldComponent();
                    nxpMoldComponent.setMoldId(nxpMold.getId());
                    nxpMoldComponent.setMoldCode(nxpMold.getMoldCode());
                    nxpMoldComponent.setComponentName(nxpMoldTypeComponent.getComponentName());
                    nxpMoldComponent.setStatus(1);
                    nxpMoldComponent.setMoldTypeId(nxpMoldTypeComponent.getMoldTypeId());
                    nxpMoldComponent.setMoldRmsCode(nxpMold.getMoldRmsCode());
                    nxpMoldComponent.setComponentId(nxpMoldTypeComponent.getComponentId());
                    nxpMoldComponent.setMoldType(nxpMold.getMoldType());
                    nxpMoldComponent.setCreateTime(DateUtils.getNowDate());
                    nxpMoldComponentMapper.insertNxpMoldComponent(nxpMoldComponent);
                }
            }
        }
        return i;
    }

    /**
     * 修改模具管理
     *
     * @param nxpMold 模具管理
     * @return 结果
     */
    @Override
    public int updateNxpMold(NxpMold nxpMold) {
        if (nxpMold.getMoldAddressId() != null){
            NxpSmartCabinet nxpSmartCabinet = nxpSmartCabinetMapper.selectNxpSmartCabinetById(nxpMold.getMoldAddressId());
            if (nxpSmartCabinet != null){
                nxpMold.setMoldAddress(nxpSmartCabinet.getPosition());
            }
        }
        nxpMold.setUpdateTime(DateUtils.getNowDate());

        if (StringUtils.isNotEmpty(nxpMold.getProductTypeId())){
            String productTypeName = "";
            String productTypeId = nxpMold.getProductTypeId();
            if (productTypeId.contains(",")){
                String[] split = productTypeId.split(",");
                for (String productTypeIdStr : split) {
                    NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(productTypeIdStr));
                    productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
                }
            }else {
                NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(productTypeId));
                productTypeName = nxpProductType.getProductTypeName();
            }
            nxpMold.setProductTypeName(productTypeName.contains(",") ? productTypeName.substring(0,productTypeName.length() - 1) :productTypeName);
        }else {
            nxpMold.setProductTypeName("");
        }

        // 如果状态为正常，那么部件也更新为正常
        Integer status = nxpMold.getStatus();
        if (status == 1){
            List<NxpMoldComponent> components = nxpMoldComponentMapper.selectNxpMoldComponentByMoldId(nxpMold.getId());
            for (NxpMoldComponent component : components) {
                component.setStatus(1);
                nxpMoldComponentMapper.updateNxpMoldComponent(component);
            }
        }

        int i = nxpMoldMapper.updateNxpMold(nxpMold);

        // 先删除该模具的部件
        nxpMoldComponentMapper.deleteNxpMoldComponentByMoldId(nxpMold.getId());
        // 新增部件
        String machineModelId = "";
        String machineModel = nxpMold.getMachineModel();
        if (StringUtils.isNotEmpty(machineModel)){
            if (machineModel.contains(",")){
                machineModelId = machineModel.split(",")[0];
            }else {
                machineModelId = machineModel;
            }
        }
        if (StringUtils.isNotEmpty(machineModelId)){
            NxpMoldType nxpMoldType = nxpMoldTypeMapper.selectNxpMoldTypeByMachineModel(machineModelId);
            if (nxpMoldType != null){
                List<NxpMoldTypeComponent> nxpMoldTypeComponentList = nxpMoldType.getNxpMoldTypeComponentList();
                for (NxpMoldTypeComponent nxpMoldTypeComponent : nxpMoldTypeComponentList) {
                    NxpMoldComponent nxpMoldComponent = new NxpMoldComponent();
                    nxpMoldComponent.setMoldId(nxpMold.getId());
                    nxpMoldComponent.setMoldCode(nxpMold.getMoldCode());
                    nxpMoldComponent.setComponentName(nxpMoldTypeComponent.getComponentName());
                    nxpMoldComponent.setStatus(1);
                    nxpMoldComponent.setMoldTypeId(nxpMoldTypeComponent.getMoldTypeId());
                    nxpMoldComponent.setMoldRmsCode(nxpMold.getMoldRmsCode());
                    nxpMoldComponent.setComponentId(nxpMoldTypeComponent.getComponentId());
                    nxpMoldComponent.setMoldType(nxpMold.getMoldType());
                    nxpMoldComponent.setCreateTime(DateUtils.getNowDate());
                    nxpMoldComponentMapper.insertNxpMoldComponent(nxpMoldComponent);
                }
            }
        }
        return i;
    }

    @Override
    public int updateNxpMoldApi(NxpMold nxpMold) {
        nxpMold.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldMapper.updateNxpMoldApi(nxpMold);
    }

    /**
     * 删除模具管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldByIds(String ids) {
        return nxpMoldMapper.deleteNxpMoldByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具管理信息
     *
     * @param id 模具管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldById(Long id) {
        return nxpMoldMapper.deleteNxpMoldById(id);
    }

    /**
     * 根据编号查询
     *
     * @param moldCode
     * @return
     */
    @Override
    public NxpMold selectNxpMoldByCode(String moldCode) {
        return nxpMoldMapper.selectNxpMoldByCode(moldCode);
    }

    /**
     * 根据编号查询
     *
     * @param moldCode
     * @return
     */
    @Override
    public NxpMold selectNxpMoldByRMSCode(String moldCode) {
        return nxpMoldMapper.selectNxpMoldByRMSCode(moldCode);
    }

    /**
     * 推荐模具
     *
     * @param number
     * @return
     */
    @Override
    public NxpMoldDTO recommendedMold(String number) {
        return nxpMoldMapper.recommendedMold(number);
    }

    /**
     * 根据所属机型查询
     * @param nxpMold
     * @return
     */
    @Override
    public List<NxpMold> selectNxpMoldListByMachineModel(NxpMold nxpMold) {
        return nxpMoldMapper.selectNxpMoldListByMachineModel(nxpMold);
    }

    /**
     * 查询pkgType
     * @return
     */
    @Override
    public List<String> selectPkgType() {
        return nxpMoldMapper.selectPkgType();
    }

    /**
     * 查询length
     * @return
     */
    @Override
    public List<String> selectLength() {
        return nxpMoldMapper.selectLengths();
    }

    /**
     * 查询machineModel
     * @return
     */
    @Override
    public List<String> selectMachineMode() {
        return nxpMoldMapper.selectMachineMode();
    }

    @Override
    public List<NxpMold> selectNxpMoldListNew(NxpMold nxpMold) {
        return nxpMoldMapper.selectNxpMoldList(nxpMold);
    }

    @Override
    public List<NxpMold> selectByProductId(String productTypeId) {
        return nxpMoldMapper.selectByProductId(productTypeId);
    }

    @Override
    public List<NxpMold> selectMoldListByGroup(String moldIds,Integer moldType) {
        // 所有模具
        NxpMold nxpMold = new NxpMold();
        nxpMold.setMoldType(moldType);
        List<NxpMold> allMolds = nxpMoldMapper.selectNxpMoldList(nxpMold);
        // 已选择模具
        List<String> moldIdList = new ArrayList<>();
        if (moldIds.contains(",")){
            String[] split = moldIds.split(",");
            moldIdList = Arrays.asList(split);
        }else {
            moldIdList.add(moldIds);
        }
        List<NxpMold> checkMolds = nxpMoldMapper.selectByIdList(moldIdList);

        for (NxpMold all : allMolds)
        {
            for (NxpMold check : checkMolds)
            {
                if (all.getId().longValue() == check.getId().longValue())
                {
                    all.setFlag(true);
                    break;
                }
            }
        }
        return allMolds;
    }

    @Override
    public List<NxpMold> selectNxpMoldListAdmin(NxpMold nxpMold) {
        return nxpMoldMapper.selectNxpMoldList(nxpMold);
    }
}
