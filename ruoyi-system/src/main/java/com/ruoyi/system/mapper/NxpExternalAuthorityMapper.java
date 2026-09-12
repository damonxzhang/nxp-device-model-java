package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpExternalAuthority;

/**
 * 外部关联Mapper接口
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
public interface NxpExternalAuthorityMapper 
{
    /**
     * 查询外部关联
     * 
     * @param id 外部关联ID
     * @return 外部关联
     */
    public NxpExternalAuthority selectNxpExternalAuthorityById(Long id);

    /**
     * 查询外部关联列表
     * 
     * @param nxpExternalAuthority 外部关联
     * @return 外部关联集合
     */
    public List<NxpExternalAuthority> selectNxpExternalAuthorityList(NxpExternalAuthority nxpExternalAuthority);

    /**
     * 新增外部关联
     * 
     * @param nxpExternalAuthority 外部关联
     * @return 结果
     */
    public int insertNxpExternalAuthority(NxpExternalAuthority nxpExternalAuthority);

    /**
     * 修改外部关联
     * 
     * @param nxpExternalAuthority 外部关联
     * @return 结果
     */
    public int updateNxpExternalAuthority(NxpExternalAuthority nxpExternalAuthority);

    /**
     * 删除外部关联
     * 
     * @param id 外部关联ID
     * @return 结果
     */
    public int deleteNxpExternalAuthorityById(Long id);

    /**
     * 批量删除外部关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpExternalAuthorityByIds(String[] ids);
}
