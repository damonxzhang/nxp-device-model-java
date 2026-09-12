package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpInspectionPositionService;
import com.ruoyi.common.core.text.Convert;

/**
 * 维修位置Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPositionServiceImpl implements INxpInspectionPositionService 
{
    @Autowired
    private NxpInspectionPositionMapper nxpInspectionPositionMapper;
    @Autowired
    private NxpMachineModelMapper nxpMachineModelMapper;
    @Autowired
    private NxpMachineVendorMapper nxpMachineVendorMapper;
    @Autowired
    private NxpMachineModelVendorMapper nxpMachineModelVendorMapper;
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;
    @Autowired
    private NxpInspectionPositionModelMapper nxpInspectionPositionModelMapper;

    /**
     * 查询维修位置
     * 
     * @param id 维修位置ID
     * @return 维修位置
     */
    @Override
    public NxpInspectionPosition selectNxpInspectionPositionById(Long id)
    {
        return nxpInspectionPositionMapper.selectNxpInspectionPositionById(id);
    }

    @Override
    public List<NxpInspectionPosition> selectNxpInspectionPositionByModelId(NxpInspectionPosition nxpInspectionPosition, Long id )
    {
        List<NxpInspectionPosition> allPosition = nxpInspectionPositionMapper.selectNxpInspectionPositionList(nxpInspectionPosition);
        List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBymodelId(id);

        for (NxpInspectionPosition inspectionPosition : allPosition) {
            for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {
                if (inspectionPosition.getId().longValue() == nxpInspectionPositionModel.getPositionId().longValue()) {
                    inspectionPosition.setFlag(true);
                    break;
                }
            }
        }

        return allPosition;
    }

    /**
     * 查询维修位置列表
     * 
     * @param nxpInspectionPosition 维修位置
     * @return 维修位置
     */
    @Override
    public List<NxpInspectionPosition> selectNxpInspectionPositionList(NxpInspectionPosition nxpInspectionPosition)
    {
        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionMapper.selectNxpInspectionPositionList(nxpInspectionPosition);
        for (NxpInspectionPosition inspectionPosition : nxpInspectionPositions) {
            Long process_id = inspectionPosition.getProcessId();
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(process_id);
            if (nxpMachineProcess != null)
                inspectionPosition.setProcessName(nxpMachineProcess.getName());

            Long id = inspectionPosition.getId();
            List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBypositionId(id);
            String modelName = "";
            String vendorName = "";
            for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {

                NxpMachineModel nxpMachineModel = nxpMachineModelMapper.selectNxpMachineModelById(nxpInspectionPositionModel.getModelId());
                if (nxpMachineModel != null){
                    modelName = modelName + nxpMachineModel.getName() + ",";

                    List<NxpMachineModelVendor> nxpMachineModelVendors = nxpMachineModelVendorMapper.selectNxpMachineModelVendorListByModelId(nxpInspectionPositionModel.getModelId());
                    for (NxpMachineModelVendor nxpMachineModelVendor : nxpMachineModelVendors) {
                        NxpMachineVendor nxpMachineVendor = nxpMachineVendorMapper.selectNxpMachineVendorById(nxpMachineModelVendor.getVendorId());
                        if (nxpMachineVendor != null){
                            vendorName = vendorName + nxpMachineVendor.getName() + ",";
                        }
                    }

                }

            }

            if (StringUtils.isNoneBlank(modelName)){
                inspectionPosition.setModelName(modelName.substring(0,modelName.length()-1));
            }

            if (StringUtils.isNoneBlank(vendorName)){
                inspectionPosition.setVendorName(vendorName.substring(0,vendorName.length()-1));
            }

        }
        return nxpInspectionPositions;
    }

    @Override
    public List<NxpInspectionPosition> selectNxpInspectionPositionListNew(NxpInspectionPosition nxpInspectionPosition) {
        return nxpInspectionPositionMapper.selectNxpInspectionPositionList(nxpInspectionPosition);
    }

    /**
     * 新增维修位置
     * 
     * @param nxpInspectionPosition 维修位置
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPosition(NxpInspectionPosition nxpInspectionPosition)
    {
//        //优先判断同一工序下，是否有重名的维修位置
//        List<NxpInspectionPosition> nxpInspectionPositions = nxpInspectionPositionMapper.selectCheckNxpInspectionPositionList(nxpInspectionPosition);
//        if (!nxpInspectionPositions.isEmpty())
//            return 2;

//        判断同一机型中，不能有相同维修位置
        List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBymodelId(Long.valueOf(nxpInspectionPosition.getModel_id()));
        for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {
            NxpInspectionPosition nxpInspectionPosition1 = nxpInspectionPositionMapper.selectNxpInspectionPositionById(nxpInspectionPositionModel.getPositionId());
            if (nxpInspectionPosition1 != null){
                if (nxpInspectionPosition1.getPosition().equals(nxpInspectionPosition.getPosition())){
                    return 2;
                }
            }
        }

        nxpInspectionPosition.setCreated_at(new Date());
        int i = nxpInspectionPositionMapper.insertNxpInspectionPosition(nxpInspectionPosition);
//        NxpInspectionPosition nxpInspectionPosition1 = nxpInspectionPositionMapper.selectNxpInspectionPositionByPosition(nxpInspectionPosition.getPosition());
//        nxpInspectionPositionModelMapper.deleteNxpInspectionPositionModelByPositionId(nxpInspectionPosition1.getId());
        String model_id = nxpInspectionPosition.getModel_id();
        if (model_id.indexOf(",") != -1){
            String[] split = model_id.split(",");
            for (String s : split) {
                NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
                nxpInspectionPositionModel.setPositionId(nxpInspectionPosition.getId());
                nxpInspectionPositionModel.setModelId(Long.valueOf(s));
                nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
            }
        }
        else{
            NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
            nxpInspectionPositionModel.setPositionId(nxpInspectionPosition.getId());
            nxpInspectionPositionModel.setModelId(Long.valueOf(model_id));
            nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
        }
        return i;
    }

    /**
     * 修改维修位置
     * 
     * @param nxpInspectionPosition 维修位置
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPosition(NxpInspectionPosition nxpInspectionPosition)
    {

//        判断同一机型中，不能有相同维修位置
//        List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBymodelId(Long.valueOf(nxpInspectionPosition.getModel_id()));
//        for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {
//            NxpInspectionPosition nxpInspectionPosition1 = nxpInspectionPositionMapper.selectNxpInspectionPositionById(nxpInspectionPositionModel.getPositionId());
//            if (nxpInspectionPosition1.getPosition().equals(nxpInspectionPosition.getPosition())){
//                return 2;
//
//            }
//        }

        nxpInspectionPosition.setUpdated_at(new Date());
        int i = nxpInspectionPositionMapper.updateNxpInspectionPosition(nxpInspectionPosition);
        nxpInspectionPositionModelMapper.deleteNxpInspectionPositionModelByPositionId(nxpInspectionPosition.getId());
        String model_id = nxpInspectionPosition.getModel_id();
        if (model_id.indexOf(",") != -1){
            String[] split = model_id.split(",");
            for (String s : split) {
                NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
                nxpInspectionPositionModel.setPositionId(nxpInspectionPosition.getId());
                nxpInspectionPositionModel.setModelId(Long.valueOf(s));
                nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
            }
        }
        else{
            NxpInspectionPositionModel nxpInspectionPositionModel = new NxpInspectionPositionModel();
            nxpInspectionPositionModel.setPositionId(nxpInspectionPosition.getId());
            nxpInspectionPositionModel.setModelId(Long.valueOf(model_id));
            nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
        }
        return i;
    }

    /**
     * 删除维修位置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPositionByIds(String ids)
    {
        return nxpInspectionPositionMapper.deleteNxpInspectionPositionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修位置信息
     * 
     * @param id 维修位置ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPositionById(Long id)
    {
        return nxpInspectionPositionMapper.deleteNxpInspectionPositionById(id);
    }

    @Override
    public List<NxpInspectionPosition> selectNxpInspectionPositionByProcessId(Long processId){
        return nxpInspectionPositionMapper.selectNxpInspectionPositionByProcessId(processId);
    }
}
