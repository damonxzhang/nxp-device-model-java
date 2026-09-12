package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Tobuy;

/**
 * 史工输出表Mapper接口
 * 
 * @author d
 * @date 2021-05-21
 */
public interface TobuyMapper 
{
    /**
     * 查询史工输出表
     *
     * @param partID 史工输出表ID
     * @return 史工输出表
     */
    public Tobuy selectTobuyById(String partID);

    /**
     * 查询史工输出表列表
     *
     * @param tobuy 史工输出表
     * @return 史工输出表集合
     */
    public List<Tobuy> selectTobuyList(Tobuy tobuy);

    /**
     * 新增史工输出表
     * 
     * @param tobuy 史工输出表
     * @return 结果
     */
    public int insertTobuy(Tobuy tobuy);

    /**
     * 修改史工输出表
     *
     * @param tobuy 史工输出表
     * @return 结果
     */
    public int updateTobuy(Tobuy tobuy);

    /**
     * 删除史工输出表
     *
     * @param partID 史工输出表ID
     * @return 结果
     */
    public int deleteTobuyById(String partID);

    /**
     * 批量删除史工输出表
     *
     * @param partIDs 需要删除的数据ID
     * @return 结果
     */
    public int deleteTobuyByIds(String[] partIDs);
}
