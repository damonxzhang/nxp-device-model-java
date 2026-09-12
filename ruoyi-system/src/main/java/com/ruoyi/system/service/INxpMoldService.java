package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.DTO.NxpMoldDTO;
import com.ruoyi.system.domain.NxpMachineModel;
import com.ruoyi.system.domain.NxpMold;

/**
 * 模具管理Service接口
 *
 * @author YanCheng Liu
 * @date 2023-06-23
 */
public interface INxpMoldService {
    /**
     * 查询模具管理
     *
     * @param id 模具管理ID
     * @return 模具管理
     */
    public NxpMold selectNxpMoldById(Long id);

    /**
     * 查询模具管理
     *
     * @param id 模具管理ID
     * @return 模具管理
     */
    NxpMold selectNxpMoldByFacility(Long facilityId);

    /**
     * 查询模具管理列表
     *
     * @param nxpMold 模具管理
     * @return 模具管理集合
     */
    public List<NxpMold> selectNxpMoldList(NxpMold nxpMold);
    public List<NxpMold> selectNxpMoldListApi(NxpMold nxpMold);

    /**
     * 新增模具管理
     *
     * @param nxpMold 模具管理
     * @return 结果
     */
    public int insertNxpMold(NxpMold nxpMold);

    /**
     * 修改模具管理
     *
     * @param nxpMold 模具管理
     * @return 结果
     */
    public int updateNxpMold(NxpMold nxpMold);

    public int updateNxpMoldApi(NxpMold nxpMold);

    /**
     * 批量删除模具管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldByIds(String ids);

    /**
     * 删除模具管理信息
     *
     * @param id 模具管理ID
     * @return 结果
     */
    public int deleteNxpMoldById(Long id);

    /**
     * 根据编号查询
     *
     * @param moldCode
     * @return
     */
    NxpMold selectNxpMoldByCode(String moldCode);

    /**
     * 根据编号查询
     *
     * @param moldCode
     * @return
     */
    NxpMold selectNxpMoldByRMSCode(String moldCode);

    /**
     * 推荐模具
     *
     * @param number
     * @return
     */
    NxpMoldDTO recommendedMold(String number);

    /**
     * 根据所属机型查询
     * @param nxpMold
     * @return
     */
    List<NxpMold> selectNxpMoldListByMachineModel(NxpMold nxpMold);

    /**
     * 查询pkgType
     * @return
     */
    List<String> selectPkgType();

    /**
     * 查询length
     * @return
     */
    List<String> selectLength();

    /**
     * 查询machineModel
     * @return
     */
    List<String> selectMachineMode();

    List<NxpMold> selectNxpMoldListNew(NxpMold nxpMold);

    List<NxpMold> selectByProductId(String productTypeId);

    List<NxpMold> selectMoldListByGroup(String moldIds,Integer moldType);

    List<NxpMold> selectNxpMoldListAdmin(NxpMold nxpMold);
}
