package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpSparePartSparePart;
import com.ruoyi.system.domain.OutPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TobuyMapper;
import com.ruoyi.system.domain.Tobuy;
import com.ruoyi.system.service.ITobuyService;
import com.ruoyi.common.core.text.Convert;

/**
 * 史工输出表Service业务层处理
 * 
 * @author d
 * @date 2021-05-21
 */
@Service
@DataSource(value = DataSourceType.BEPR)
public class TobuyServiceImpl implements ITobuyService 
{
    @Autowired
    private TobuyMapper tobuyMapper;

    /**
     * 查询史工输出表
     * 
     * @param partID 史工输出表ID
     * @return 史工输出表
     */
    @Override
    public Tobuy selectTobuyById(String partID)
    {
        return tobuyMapper.selectTobuyById(partID);
    }

    /**
     * 查询史工输出表列表
     * 
     * @param tobuy 史工输出表
     * @return 史工输出表
     */
    @Override
    public List<Tobuy> selectTobuyList(Tobuy tobuy)
    {
        return tobuyMapper.selectTobuyList(tobuy);
    }

    /**
     * 新增史工输出表
     * 
     * @param tobuy 史工输出表
     * @return 结果
     */
    @Override
    public int insertTobuy(Tobuy tobuy)
    {
        return tobuyMapper.insertTobuy(tobuy);
    }

    /**
     * 修改史工输出表
     * 
     * @param tobuy 史工输出表
     * @return 结果
     */
    @Override
    public int updateTobuy(Tobuy tobuy)
    {
        return tobuyMapper.updateTobuy(tobuy);
    }

    /**
     * 删除史工输出表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTobuyByIds(String ids)
    {
        return tobuyMapper.deleteTobuyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除史工输出表信息
     * 
     * @param partID 史工输出表ID
     * @return 结果
     */
    @Override
    public int deleteTobuyById(String partID)
    {
        return tobuyMapper.deleteTobuyById(partID);
    }

    @Override
    public boolean insertToBuyResultBoolean(OutPut outPut, NxpSparePartSparePart nxpSparePartSparePart){

//        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.BEPR.name());
//        判断史工表中是否已经存在购买了
        Tobuy tobuy1 = tobuyMapper.selectTobuyById(nxpSparePartSparePart.getS_id());
        if (tobuy1 != null)
            return true;

        // 史工数据库提交信息
        Tobuy tobuy = new Tobuy();
        tobuy.setDescription(nxpSparePartSparePart.getS_description());
        tobuy.setPartID(nxpSparePartSparePart.getS_id());
        tobuy.setModel(nxpSparePartSparePart.getS_model());
        tobuy.setUserID(nxpSparePartSparePart.getP_id());
//        tobuy.setUserID(outPut.getP_id());
        if (nxpSparePartSparePart.getS_vendor() == null || nxpSparePartSparePart.getS_vendor().equals("") )
            tobuy.setVendor(" ");
        else
            tobuy.setVendor(nxpSparePartSparePart.getS_vendor());
        Long number = 2*nxpSparePartSparePart.getS_safeamount().longValue()-nxpSparePartSparePart.getS_amount().longValue();
        tobuy.setQuantity(number);
        tobuyMapper.insertTobuy(tobuy);

//        DynamicDataSourceContextHolder.clearDataSourceType();

        return true;
    }
}
