package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.NxpFacilityListDTO;
import com.ruoyi.system.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpInspectionContentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 维修内容Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionContentServiceImpl implements INxpInspectionContentService 
{
    @Autowired
    private NxpInspectionContentMapper nxpInspectionContentMapper;
    @Autowired
    private NxpMachineModelVendorMapper nxpMachineModelVendorMapper;
    @Autowired
    private NxpInspectionPositionMapper nxpInspectionPositionMapper;
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;
    @Autowired
    private NxpInspectionPositionModelMapper nxpInspectionPositionModelMapper;
    @Autowired
    private NxpMachineModelMapper nxpMachineModelMapper;
    @Autowired
    private NxpFacilityMapper nxpFacilityMapper;
    @Autowired
    private NxpMachineVendorMapper nxpMachineVendorMapper;

    /**
     * 查询维修内容
     * 
     * @param id 维修内容ID
     * @return 维修内容
     */
    @Override
    public NxpInspectionContent selectNxpInspectionContentById(Long id)
    {
        return nxpInspectionContentMapper.selectNxpInspectionContentById(id);
    }

    /**
     * 查询维修内容列表
     * 
     * @param nxpInspectionContent 维修内容
     * @return 维修内容
     */
    @Override
    public List<NxpInspectionContent> selectNxpInspectionContentList(NxpInspectionContent nxpInspectionContent)
    {
        List<NxpInspectionContent> nxpInspectionContents = nxpInspectionContentMapper.selectNxpInspectionContentList(nxpInspectionContent);
        for (NxpInspectionContent inspectionContent : nxpInspectionContents) {
            Long position_id = inspectionContent.getPosition_id();
            if (position_id != null){
                NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionMapper.selectNxpInspectionPositionById(position_id);
                if (nxpInspectionPosition != null){
                    inspectionContent.setPositionName(nxpInspectionPosition.getPosition());
                }
                // 处理时间
                if (inspectionContent.getCreated_at() != null){
                    inspectionContent.setCreateTime(new Date(inspectionContent.getCreated_at()*1000));
                }
                if (inspectionContent.getUpdated_at() != null){
                    inspectionContent.setUpdateTime(new Date(inspectionContent.getUpdated_at()*1000));
                }
            }
            Long processId = inspectionContent.getProcessId();
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(processId);
            if (nxpMachineProcess != null)
                inspectionContent.setProcessName(nxpMachineProcess.getName());


//            写入所有的 model 和 vendor
            List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBypositionId(position_id);
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
                inspectionContent.setModelName(modelName.substring(0,modelName.length()-1));
            }

            if (StringUtils.isNoneBlank(vendorName)){
                inspectionContent.setVendorName(vendorName.substring(0,vendorName.length()-1));
            }


        }
        return nxpInspectionContents;
    }

    @Override
    public List<NxpInspectionContent> selectNxpInspectionContentListNew(NxpInspectionContent nxpInspectionContent) {
        return nxpInspectionContentMapper.selectNxpInspectionContentList(nxpInspectionContent);
    }

    /**
     * 新增维修内容
     * 
     * @param nxpInspectionContent 维修内容
     * @return 结果
     */
    @Override
    public int insertNxpInspectionContent(NxpInspectionContent nxpInspectionContent)
    {
        nxpInspectionContent.setCreated_at(System.currentTimeMillis() / 1000);
        return nxpInspectionContentMapper.insertNxpInspectionContent(nxpInspectionContent);
    }

    /**
     * 修改维修内容
     * 
     * @param nxpInspectionContent 维修内容
     * @return 结果
     */
    @Override
    public int updateNxpInspectionContent(NxpInspectionContent nxpInspectionContent)
    {
        nxpInspectionContent.setUpdated_at(System.currentTimeMillis() / 1000);
        return nxpInspectionContentMapper.updateNxpInspectionContent(nxpInspectionContent);
    }

    /**
     * 删除维修内容对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionContentByIds(String ids)
    {
        return nxpInspectionContentMapper.deleteNxpInspectionContentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修内容信息
     * 
     * @param id 维修内容ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionContentById(Long id)
    {
        return nxpInspectionContentMapper.deleteNxpInspectionContentById(id);
    }
}
