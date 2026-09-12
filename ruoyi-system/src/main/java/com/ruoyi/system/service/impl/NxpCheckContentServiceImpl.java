package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpCheckContentMapper;
import com.ruoyi.system.domain.NxpCheckContent;
import com.ruoyi.system.service.INxpCheckContentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查内容Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpCheckContentServiceImpl implements INxpCheckContentService 
{
    @Autowired
    private NxpCheckContentMapper nxpCheckContentMapper;

    /**
     * 查询检查内容
     * 
     * @param id 检查内容ID
     * @return 检查内容
     */
    @Override
    public NxpCheckContent selectNxpCheckContentById(Long id)
    {
        return nxpCheckContentMapper.selectNxpCheckContentById(id);
    }

    /**
     * 查询检查内容列表
     * 
     * @param nxpCheckContent 检查内容
     * @return 检查内容
     */
    @Override
    public List<NxpCheckContent> selectNxpCheckContentList(NxpCheckContent nxpCheckContent)
    {
        return nxpCheckContentMapper.selectNxpCheckContentList(nxpCheckContent);
    }

    /**
     * 新增检查内容
     * 
     * @param nxpCheckContent 检查内容
     * @return 结果
     */
    @Override
    public int insertNxpCheckContent(NxpCheckContent nxpCheckContent)
    {
        nxpCheckContent.setCreateTime(DateUtils.getNowDate());
        return nxpCheckContentMapper.insertNxpCheckContent(nxpCheckContent);
    }

    /**
     * 修改检查内容
     * 
     * @param nxpCheckContent 检查内容
     * @return 结果
     */
    @Override
    public int updateNxpCheckContent(NxpCheckContent nxpCheckContent)
    {
        nxpCheckContent.setUpdateTime(DateUtils.getNowDate());
        return nxpCheckContentMapper.updateNxpCheckContent(nxpCheckContent);
    }

    /**
     * 删除检查内容对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckContentByIds(String ids)
    {
        return nxpCheckContentMapper.deleteNxpCheckContentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查内容信息
     * 
     * @param id 检查内容ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckContentById(Long id)
    {
        return nxpCheckContentMapper.deleteNxpCheckContentById(id);
    }

    /**
     * 根据检查组查询检查项
     * @param checkGroupId
     * @return
     */
    @Override
    public List<NxpCheckContent> selectNxpCheckContentByCheckGroupId(Long checkGroupId) {
        return nxpCheckContentMapper.selectNxpCheckContentByCheckGroupId(checkGroupId);
    }
}
