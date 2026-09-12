package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpMachineModelService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 设备型号Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-02-21
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMachineModelServiceImpl implements INxpMachineModelService 
{
    @Resource
    private NxpMachineModelMapper nxpMachineModelMapper;
    @Resource
    private NxpMachineModelVendorMapper nxpMachineModelVendorMapper;
    @Resource
    private NxpInspectionPositionMapper nxpInspectionPositionMapper;
    @Resource
    private NxpInspectionPositionModelMapper nxpInspectionPositionModelMapper;
    @Resource
    private NxpUserInfoMapper nxpUserInfoMapper;

    /**
     * 查询设备型号
     * 
     * @param id 设备型号ID
     * @return 设备型号
     */
    @Override
    public NxpMachineModel selectNxpMachineModelById(Long id)
    {
        return nxpMachineModelMapper.selectNxpMachineModelById(id);
    }

    /**
     * 查询设备型号列表
     * 
     * @param nxpMachineModel 设备型号
     * @return 设备型号
     */
    @Override
    public List<NxpMachineModel> selectNxpMachineModelList(NxpMachineModel nxpMachineModel)
    {
        List<NxpMachineModel> nxpMachineModels = nxpMachineModelMapper.selectNxpMachineModelList(nxpMachineModel);
        for (NxpMachineModel machineModel : nxpMachineModels) {
            Long id = machineModel.getId();
            //维修位置赋值
            String positionName = "";
            List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBymodelId(id);
            for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {

                NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionMapper.selectNxpInspectionPositionById(nxpInspectionPositionModel.getPositionId());
                if (nxpInspectionPosition != null){
                    positionName = positionName + nxpInspectionPosition.getPosition() + ",";
                }
            }

            if (StringUtils.isNoneBlank(positionName)){
                machineModel.setInspectionPosition(positionName.substring(0,positionName.length()-1));
            }

            if (machineModel.getOwner() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(machineModel.getOwner().longValue());
                if (nxpUserInfo != null){
                    machineModel.setOwnerName(nxpUserInfo.getChinese_name());
                }
            }
        }
        return nxpMachineModels;
    }

    @Override
    public List<NxpMachineModel> selectNxpMachineModelListMold(NxpMachineModel nxpMachineModel) {
        return nxpMachineModelMapper.selectNxpMachineModelList(nxpMachineModel);
    }


    @Override
    public List<NxpMachineModel> selectNxpMachineModelListByPositionId(NxpMachineModel nxpMachineModel, Long id )
    {
        List<NxpMachineModel> allModel = nxpMachineModelMapper.selectNxpMachineModelList(nxpMachineModel);
        List<NxpInspectionPositionModel> nxpInspectionPositionModels1 = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBypositionId(id);

        for (NxpMachineModel machineModel : allModel) {
            for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels1) {
                if (machineModel.getId().longValue() == nxpInspectionPositionModel.getModelId().longValue()){
                    machineModel.setFlag(true);
                }
            }
        }

        return allModel;
    }

    /**
     * 新增设备型号
     * 
     * @param nxpMachineModel 设备型号
     * @return 结果
     */
    @Override
    public int insertNxpMachineModel(NxpMachineModel nxpMachineModel)
    {
        //判断机型不可重名
        NxpMachineModel nxpMachineModel2 = nxpMachineModelMapper.selectNxpMachineModelByName(nxpMachineModel.getName());
        if (nxpMachineModel2 != null)
            return 2;

        //判断同一个vendor下，机型是否重名
        List<NxpMachineModelVendor> nxpMachineModelVendors = nxpMachineModelVendorMapper.selectNxpMachineModelVendorListByVendorId(nxpMachineModel.getVendorId());
        for (NxpMachineModelVendor nxpMachineModelVendor : nxpMachineModelVendors) {
            NxpMachineModel nxpMachineModel1 = nxpMachineModelMapper.selectNxpMachineModelById(nxpMachineModelVendor.getModelId());
            if (nxpMachineModel1 != null){
                if (nxpMachineModel.getName().equals(nxpMachineModel1.getName())){
                    return 2;
                }
            }
        }

        int i = nxpMachineModelMapper.insertNxpMachineModel(nxpMachineModel);
        NxpMachineModel nxpMachineModel1 = nxpMachineModelMapper.selectNxpMachineModelByName(nxpMachineModel.getName());
        nxpInspectionPositionModelMapper.deleteNxpInspectionPositionModelByModelId(nxpMachineModel1.getId());
        String inspection_position = nxpMachineModel.getInspection_position();
        if (inspection_position.indexOf(",") != -1){
            String[] split = inspection_position.split(",");
            for (String s : split) {
                NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
                nxpInspectionPositionModel.setModelId(nxpMachineModel.getId());
                nxpInspectionPositionModel.setPositionId(Long.valueOf(s));
                nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
            }
        }
        else {
            NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
            nxpInspectionPositionModel.setModelId(nxpMachineModel.getId());
            nxpInspectionPositionModel.setPositionId(Long.valueOf(inspection_position));
            nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
        }

        return i;
    }

    /**
     * 修改设备型号
     * 
     * @param nxpMachineModel 设备型号
     * @return 结果
     */
    @Override
    public int updateNxpMachineModel(NxpMachineModel nxpMachineModel)
    {
        //删除信号&vendor对应表
        nxpMachineModelVendorMapper.deleteNxpMachineModelVendorByModelId(nxpMachineModel.getId());

        //判断同一个vendor下，机型是否重名
        List<NxpMachineModelVendor> nxpMachineModelVendors = nxpMachineModelVendorMapper.selectNxpMachineModelVendorListByVendorId(nxpMachineModel.getVendorId());
        for (NxpMachineModelVendor nxpMachineModelVendor : nxpMachineModelVendors) {
            NxpMachineModel nxpMachineModel1 = nxpMachineModelMapper.selectNxpMachineModelById(nxpMachineModelVendor.getModelId());
            if (nxpMachineModel1 != null){
                if (nxpMachineModel.getName().equals(nxpMachineModel1.getName())){
                    return 2;
                }
            }
        }


        int i = nxpMachineModelMapper.updateNxpMachineModel(nxpMachineModel);
//        nxpInspectionPositionModelMapper.deleteNxpInspectionPositionModelByModelId(nxpMachineModel.getId());
        String inspection_position = nxpMachineModel.getInspection_position();
        if (StringUtils.isNoneBlank(inspection_position)){
            if (inspection_position.contains(",")){
                String[] split = inspection_position.split(",");
                for (String s : split) {
                    NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
                    nxpInspectionPositionModel.setModelId(nxpMachineModel.getId());
                    nxpInspectionPositionModel.setPositionId(Long.valueOf(s));
                    nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
                }
            }else {
                NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
                nxpInspectionPositionModel.setModelId(nxpMachineModel.getId());
                nxpInspectionPositionModel.setPositionId(Long.valueOf(inspection_position));
                nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
            }
        }
        return i;
    }

    /**
     * 删除设备型号对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineModelByIds(String ids)
    {
        return nxpMachineModelMapper.deleteNxpMachineModelByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备型号信息
     * 
     * @param id 设备型号ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineModelById(Long id)
    {
        return nxpMachineModelMapper.deleteNxpMachineModelById(id);
    }

    @Override
    public List<NxpMachineModel> selectNxpMachineModelListByPositionId(NxpMachineModel nxpMachineModel,String model_ids) {
        List<NxpMachineModel> allModel = nxpMachineModelMapper.selectNxpMachineModelList(nxpMachineModel);
        List<NxpMachineModel> selectedModels = new ArrayList<>();
        if (StringUtils.isNoneBlank(model_ids)){
            if (model_ids.indexOf(",") != -1){
                String[] model_id = model_ids.split(",");
                selectedModels.addAll(nxpMachineModelMapper.selectBatchIds(model_id));
            }else {
                selectedModels.add(nxpMachineModelMapper.selectNxpMachineModelById(Long.valueOf(model_ids)));
            }
        }
        for (NxpMachineModel machineModel : allModel) {
            for (NxpMachineModel selectedModel : selectedModels) {
                if (machineModel.getId().longValue() == selectedModel.getId().longValue()){
                    machineModel.setFlag(true);
                    break;
                }
            }
        }
        return allModel;
    }

    /**
     * 根据部件种类中的MachineModel 查询
     * @param nxpMoldType
     * @return
     */
    @Override
    public List<NxpMachineModel> selectNxpMachineModelListByMoldType(NxpMoldType nxpMoldType) {
        NxpMachineModel params = new NxpMachineModel();
        params.setStatus(1);
        List<NxpMachineModel> all = nxpMachineModelMapper.selectNxpMachineModelList(params);

        String machineModel = nxpMoldType.getMachineModel();
        if (StringUtils.isNotEmpty(machineModel)){

            List<String> modelIds = new ArrayList<>();

            if (machineModel.contains(",")){
                String[] split = machineModel.split(",");
                modelIds = Arrays.asList(split);
            }else {
                modelIds.add(machineModel);
            }

            List<NxpMachineModel> checked = nxpMachineModelMapper.selectBatchIds(modelIds.toArray(new String[modelIds.size()]));
            for (NxpMachineModel allModels : all) {
                for (NxpMachineModel checkedModels : checked) {
                    if (allModels.getId().longValue() == checkedModels.getId().longValue()){
                        allModels.setFlag(true);
                    }
                }
            }
        }
        return all;
    }

    @Override
    public List<NxpMachineModel> selectNxpMachineModelListByMold(NxpMold nxpMold) {
        NxpMachineModel params = new NxpMachineModel();
        params.setStatus(1);
        List<NxpMachineModel> all = nxpMachineModelMapper.selectNxpMachineModelList(params);

        List<String> modelIds = new ArrayList<>();
        String machineModel = nxpMold.getMachineModel();
        if (StringUtils.isNotEmpty(machineModel)){
            if (machineModel.contains(",")){
                String[] split = machineModel.split(",");
                modelIds = Arrays.asList(split);
            }else {
                modelIds.add(machineModel);
            }
        }
        List<NxpMachineModel> checked = nxpMachineModelMapper.selectBatchIds(modelIds.toArray(new String[modelIds.size()]));
        for (NxpMachineModel allModels : all) {
            for (NxpMachineModel checkedModels : checked) {
                if (allModels.getId().longValue() == checkedModels.getId().longValue()){
                    allModels.setFlag(true);
                }
            }
        }
        return all;
    }

    @Override
    public List<NxpMachineModel> selectBatchIds(String[] ids) {
        return nxpMachineModelMapper.selectBatchIds(ids);
    }

    @Override
    public List<NxpMachineModel> selectNxpMachineModelByProcessId(Long processId){
        return nxpMachineModelMapper.selectNxpMachineModelByProcessId(processId);
    }

    @Override
    public List<NxpMachineModel> selectNxpMachineModelListNew(NxpMachineModel nxpMachineModel) {
        return nxpMachineModelMapper.selectNxpMachineModelList(nxpMachineModel);
    }


}
