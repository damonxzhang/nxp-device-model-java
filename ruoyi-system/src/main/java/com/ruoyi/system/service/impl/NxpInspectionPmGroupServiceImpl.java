package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.mapper.NxpInspectionPmGroupItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.NxpInspectionPmGroupItem;
import com.ruoyi.system.mapper.NxpInspectionPmGroupMapper;
import com.ruoyi.system.domain.NxpInspectionPmGroup;
import com.ruoyi.system.service.INxpInspectionPmGroupService;
import com.ruoyi.common.core.text.Convert;

/**
 * 周PM分组Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-21
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmGroupServiceImpl implements INxpInspectionPmGroupService 
{
    @Autowired
    private NxpInspectionPmGroupMapper nxpInspectionPmGroupMapper;
    @Autowired
    private NxpInspectionPmGroupItemMapper nxpInspectionPmGroupItemMapper;

    /**
     * 查询周PM分组
     * 
     * @param id 周PM分组ID
     * @return 周PM分组
     */
    @Override
    public NxpInspectionPmGroup selectNxpInspectionPmGroupById(Long id)
    {
        return nxpInspectionPmGroupMapper.selectNxpInspectionPmGroupById(id);
    }

    /**
     * 查询周PM分组列表
     * 
     * @param nxpInspectionPmGroup 周PM分组
     * @return 周PM分组
     */
    @Override
    public List<NxpInspectionPmGroup> selectNxpInspectionPmGroupList(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
        return nxpInspectionPmGroupMapper.selectNxpInspectionPmGroupList(nxpInspectionPmGroup);
    }

    /**
     * 新增周PM分组
     * 
     * @param nxpInspectionPmGroup 周PM分组
     * @return 结果
     */
    @Transactional
    @Override
    public int insertNxpInspectionPmGroup(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
        int rows = nxpInspectionPmGroupMapper.insertNxpInspectionPmGroup(nxpInspectionPmGroup);
        insertNxpInspectionPmGroupItem(nxpInspectionPmGroup);
        return rows;
    }

    /**
     * 修改周PM分组
     * 
     * @param nxpInspectionPmGroup 周PM分组
     * @return 结果
     */
    @Transactional
    @Override
    public int updateNxpInspectionPmGroup(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
//        List<NxpInspectionPmGroup> nxpInspectionPmGroups = nxpInspectionPmGroupMapper.selectNxpInspectionPmGroupListById(nxpInspectionPmGroup.getId());
//        for (NxpInspectionPmGroup inspectionPmGroup : nxpInspectionPmGroups) {
            for (NxpInspectionPmGroupItem nxpInspectionPmGroupItem : nxpInspectionPmGroup.getNxpInspectionPmGroupItemList()) {
                if (nxpInspectionPmGroupItem.getItem_id() != null){
                    NxpInspectionPmGroupItem nxpInspectionPmGroupItem1 = new NxpInspectionPmGroupItem();
                    nxpInspectionPmGroupItem1.setId(nxpInspectionPmGroupItem.getItem_id());
                    nxpInspectionPmGroupItem1.setItem_name(nxpInspectionPmGroupItem.getItem_name());
                    nxpInspectionPmGroupItem1.setItem_status(nxpInspectionPmGroupItem.getItem_status());
                    nxpInspectionPmGroupItem1.setIs_check(nxpInspectionPmGroupItem.getIs_check());
                    nxpInspectionPmGroupItem1.setCheck_group_id(nxpInspectionPmGroupItem.getCheck_group_id());
                    nxpInspectionPmGroupItem1.setCheck_process_id(nxpInspectionPmGroupItem.getCheck_process_id());
                    nxpInspectionPmGroupItemMapper.updateNxpInspectionPmGroupItem(nxpInspectionPmGroupItem1);
                }else {
                    NxpInspectionPmGroupItem nxpInspectionPmGroupItem1 = new NxpInspectionPmGroupItem();
                    nxpInspectionPmGroupItem1.setItem_name(nxpInspectionPmGroupItem.getItem_name());
                    nxpInspectionPmGroupItem1.setItem_status(nxpInspectionPmGroupItem.getItem_status());
                    nxpInspectionPmGroupItem1.setGroup_id(nxpInspectionPmGroup.getId());
                    nxpInspectionPmGroupItem1.setIs_check(nxpInspectionPmGroupItem.getIs_check());
                    nxpInspectionPmGroupItem1.setCheck_group_id(nxpInspectionPmGroupItem.getCheck_group_id());
                    nxpInspectionPmGroupItem1.setCheck_process_id(nxpInspectionPmGroupItem.getCheck_process_id());
                    nxpInspectionPmGroupItemMapper.insertNxpInspectionPmGroupItem(nxpInspectionPmGroupItem1);
                }
            }

//        }
//        nxpInspectionPmGroupMapper.deleteNxpInspectionPmGroupItemByGroupId(nxpInspectionPmGroup.getId());
//        insertNxpInspectionPmGroupItem(nxpInspectionPmGroup);
        return nxpInspectionPmGroupMapper.updateNxpInspectionPmGroup(nxpInspectionPmGroup);
    }

    /**
     * 删除周PM分组对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteNxpInspectionPmGroupByIds(String ids)
    {
        nxpInspectionPmGroupMapper.deleteNxpInspectionPmGroupItemByGroupIds(Convert.toStrArray(ids));
        return nxpInspectionPmGroupMapper.deleteNxpInspectionPmGroupByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周PM分组信息
     * 
     * @param id 周PM分组ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmGroupById(Long id)
    {
        nxpInspectionPmGroupMapper.deleteNxpInspectionPmGroupItemByGroupId(id);
        return nxpInspectionPmGroupMapper.deleteNxpInspectionPmGroupById(id);
    }

    /**
     * 新增周PM检查项信息
     * 
     * @param nxpInspectionPmGroup 周PM分组对象
     */
    public void insertNxpInspectionPmGroupItem(NxpInspectionPmGroup nxpInspectionPmGroup)
    {
        List<NxpInspectionPmGroupItem> nxpInspectionPmGroupItemList = nxpInspectionPmGroup.getNxpInspectionPmGroupItemList();
        Long id = nxpInspectionPmGroup.getId();
        if (StringUtils.isNotNull(nxpInspectionPmGroupItemList))
        {
            List<NxpInspectionPmGroupItem> list = new ArrayList<NxpInspectionPmGroupItem>();
            for (NxpInspectionPmGroupItem nxpInspectionPmGroupItem : nxpInspectionPmGroupItemList)
            {
                nxpInspectionPmGroupItem.setGroup_id(id);
                list.add(nxpInspectionPmGroupItem);
            }
            if (list.size() > 0)
            {
                nxpInspectionPmGroupMapper.batchNxpInspectionPmGroupItem(list);
            }
        }
    }
}
