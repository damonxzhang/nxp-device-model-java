package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.NxpInspectionPosition;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.mapper.NxpMachineProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionQuestionTypeMapper;
import com.ruoyi.system.domain.NxpInspectionQuestionType;
import com.ruoyi.system.service.INxpInspectionQuestionTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备问题类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionQuestionTypeServiceImpl implements INxpInspectionQuestionTypeService 
{
    @Autowired
    private NxpInspectionQuestionTypeMapper nxpInspectionQuestionTypeMapper;
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;

    /**
     * 查询设备问题类型
     * 
     * @param id 设备问题类型ID
     * @return 设备问题类型
     */
    @Override
    public NxpInspectionQuestionType selectNxpInspectionQuestionTypeById(Long id)
    {
        return nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeById(id);
    }

    /**
     * 查询设备问题类型列表
     * 
     * @param nxpInspectionQuestionType 设备问题类型
     * @return 设备问题类型
     */
    @Override
    public List<NxpInspectionQuestionType> selectNxpInspectionQuestionTypeList(NxpInspectionQuestionType nxpInspectionQuestionType)
    {

        List<NxpInspectionQuestionType> nxpInspectionQuestionTypes = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeList(nxpInspectionQuestionType);
        for (NxpInspectionQuestionType inspectionQuestionType : nxpInspectionQuestionTypes) {
            Integer process_id = inspectionQuestionType.getProcess_id();
            NxpMachineProcess machineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(process_id.longValue());
            if (machineProcess != null){
                inspectionQuestionType.setProcess(machineProcess.getName());
                if (machineProcess.getArea() != null){
                    switch (machineProcess.getArea()){
                        case "1":
                            inspectionQuestionType.setAreaName("Non-lead");
                            break;
                        case "2":
                            inspectionQuestionType.setAreaName("Leaded");
                            break;
                        case "3":
                            inspectionQuestionType.setAreaName("Plating");
                            break;
                        case "4":
                            inspectionQuestionType.setAreaName("PM");
                            break;
                        default:
                            inspectionQuestionType.setAreaName("OTHER");
                            break;
                    }
                }else{
                    inspectionQuestionType.setAreaName("OTHER");

                }

            }
        }
        return nxpInspectionQuestionTypes;
    }

    /**
     * 新增设备问题类型
     * 
     * @param nxpInspectionQuestionType 设备问题类型
     * @return 结果
     */
    @Override
    public int insertNxpInspectionQuestionType(NxpInspectionQuestionType nxpInspectionQuestionType)
    {
        nxpInspectionQuestionType.setCreate_time(new Date());
        return nxpInspectionQuestionTypeMapper.insertNxpInspectionQuestionType(nxpInspectionQuestionType);
    }

    /**
     * 修改设备问题类型
     * 
     * @param nxpInspectionQuestionType 设备问题类型
     * @return 结果
     */
    @Override
    public int updateNxpInspectionQuestionType(NxpInspectionQuestionType nxpInspectionQuestionType)
    {
        return nxpInspectionQuestionTypeMapper.updateNxpInspectionQuestionType(nxpInspectionQuestionType);
    }

    /**
     * 删除设备问题类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionQuestionTypeByIds(String ids)
    {
        return nxpInspectionQuestionTypeMapper.deleteNxpInspectionQuestionTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备问题类型信息
     * 
     * @param id 设备问题类型ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionQuestionTypeById(Long id)
    {
        return nxpInspectionQuestionTypeMapper.deleteNxpInspectionQuestionTypeById(id);
    }
}
