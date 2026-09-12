package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpSwcJobContentMapper;
import com.ruoyi.system.domain.NxpSwcJobContent;
import com.ruoyi.system.service.INxpSwcJobContentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 作业内容管理Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpSwcJobContentServiceImpl implements INxpSwcJobContentService 
{
    @Autowired
    private NxpSwcJobContentMapper nxpSwcJobContentMapper;

    /**
     * 查询作业内容管理
     * 
     * @param id 作业内容管理ID
     * @return 作业内容管理
     */
    @Override
    public NxpSwcJobContent selectNxpSwcJobContentById(Long id)
    {
        return nxpSwcJobContentMapper.selectNxpSwcJobContentById(id);
    }

    /**
     * 查询作业内容管理列表
     * 
     * @param nxpSwcJobContent 作业内容管理
     * @return 作业内容管理
     */
    @Override
    public List<NxpSwcJobContent> selectNxpSwcJobContentList(NxpSwcJobContent nxpSwcJobContent)
    {
        return nxpSwcJobContentMapper.selectNxpSwcJobContentList(nxpSwcJobContent);
    }

    /**
     * 新增作业内容管理
     * 
     * @param nxpSwcJobContent 作业内容管理
     * @return 结果
     */
    @Override
    public int insertNxpSwcJobContent(NxpSwcJobContent nxpSwcJobContent)
    {
        nxpSwcJobContent.setCreateTime(DateUtils.getNowDate());
        return nxpSwcJobContentMapper.insertNxpSwcJobContent(nxpSwcJobContent);
    }

    /**
     * 修改作业内容管理
     * 
     * @param nxpSwcJobContent 作业内容管理
     * @return 结果
     */
    @Override
    public int updateNxpSwcJobContent(NxpSwcJobContent nxpSwcJobContent)
    {
        nxpSwcJobContent.setUpdateTime(DateUtils.getNowDate());
        return nxpSwcJobContentMapper.updateNxpSwcJobContent(nxpSwcJobContent);
    }

    /**
     * 删除作业内容管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcJobContentByIds(String ids)
    {
        return nxpSwcJobContentMapper.deleteNxpSwcJobContentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除作业内容管理信息
     * 
     * @param id 作业内容管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcJobContentById(Long id)
    {
        return nxpSwcJobContentMapper.deleteNxpSwcJobContentById(id);
    }
}
