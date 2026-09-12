package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.PersonDTO;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IAppSparePartService;
import com.ruoyi.system.service.IMailService;
import com.ruoyi.system.service.ITobuyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 13:56
 */
@Service("IAppSparePartService")
@DataSource(value = DataSourceType.SPARE)
public class AppSparePartServiceImpl implements IAppSparePartService {

    @Resource
    private NxpSparePartProcessMapper nxpSparePartProcessMapper;
    @Resource
    private NxpSparePartOutPutMapper nxpSparePartOutPutMapper;
    @Resource
    private NxpSparePartSparePartMapper nxpSparePartSparePartMapper;
    @Resource
    private NxpUserInfoServiceImpl nxpUserInfoService;
    @Resource
    private IMailService mailService;
    @Resource
    private ITobuyService tobuyService;
    @Resource
    private TobuyMapper tobuyMapper;

    @Override
    public BaseVO processList() {
        List<NxpSparePartProcess> nxpSparePartProcesses = nxpSparePartProcessMapper.selectListByNxpSparePartProcess(new NxpSparePartProcess());
        for (NxpSparePartProcess nxpSparePartProcess : nxpSparePartProcesses) {

            nxpSparePartProcess.setName(nxpSparePartProcess.getR_process());
            nxpSparePartProcess.setId(nxpSparePartProcess.getP_id());
        }
        return BaseVO.success(nxpSparePartProcesses);
    }

    @Override
    @Transactional
    public BaseVO receiveSparePart(OutPut outPut) {

//        Tobuy tobuy = tobuyMapper.selectTobuyById("BPLT-CC-MC0003");
//        return BaseVO.error(tobuy.getDescription());


        // check入参
        if (StringUtils.isBlank(outPut.getS_id())) {
            return BaseVO.error("s_id为必传参数");
        }
        if (outPut.getO_amount() == null) {
            return BaseVO.error("o_amount为必传参数");
        }
        if (StringUtils.isBlank(outPut.getP_id())) {
            return BaseVO.error("p_id为必传参数");
        }
        if (StringUtils.isBlank(outPut.getE_id())){
            outPut.setE_id(null);
        }

        // 1. 根据sid查询备件详情
        NxpSparePartSparePart nxpSparePartSparePart = nxpSparePartSparePartMapper.selectBySid(outPut.getS_id());
        // 1.1 判断现有备件数量是否满足领取
        Integer s_amount = nxpSparePartSparePart.getS_amount();
        PersonDTO personDTO = nxpSparePartSparePartMapper.selectPersonByPId(outPut.getP_id());
        // 2. 根据wid查询warehouse
        WareHouse wareHouse = nxpSparePartSparePartMapper.selectWareHouseByWid(nxpSparePartSparePart.getW_id());
        String wareHousePId = wareHouse.getP_id();
        PersonDTO wareHousePerson = nxpSparePartSparePartMapper.selectPersonByPId(wareHousePId);
        if ((s_amount - outPut.getO_amount()) >= 0) {
            outPut.setO_time(new Date());
            int i = nxpSparePartOutPutMapper.insertByOutPut(outPut);
            // 3. 备件数量减
            nxpSparePartSparePart.setS_amount(s_amount - outPut.getO_amount());
            nxpSparePartSparePartMapper.update(nxpSparePartSparePart);
            // 4. 如果备件数量低于或者等于安全数量，则发送邮件，提醒开始采购
            if (nxpSparePartSparePart.getS_amount() < nxpSparePartSparePart.getS_safeamount()) {

//                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.BEPR.name());
                tobuyService.insertToBuyResultBoolean(outPut,nxpSparePartSparePart);
//                DynamicDataSourceContextHolder.clearDataSourceType();

                // todo 发送邮件，插入到给的sqlserver库中一条数据
                sendPartMail(true,nxpSparePartSparePart,outPut.getO_amount(),personDTO.getP_name(),outPut.getE_id(),wareHousePerson.getP_mail());
            }else {
                sendPartMail(false,nxpSparePartSparePart,outPut.getO_amount(),personDTO.getP_name(),outPut.getE_id(),wareHousePerson.getP_mail());
            }


            if (i > 0) {
                // 备件领取成功之后，备件库数量建议
                Map<String, Object> map = new HashMap<>();
                map.put("pwd", wareHouse.getW_lock());
                return BaseVO.success(map);
            }
            return BaseVO.error("提交备件领用失败");
        } else {
//            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.BEPR.name());
            tobuyService.insertToBuyResultBoolean(outPut,nxpSparePartSparePart);
//            DynamicDataSourceContextHolder.clearDataSourceType();

            String outLastTime = nxpSparePartOutPutMapper.selectOutLastTimeBySid(nxpSparePartSparePart.getS_id());
            partUrgeBuySendMail(nxpSparePartSparePart,personDTO.getP_name(),outLastTime,wareHousePerson.getP_mail());
            return BaseVO.error("备件数量不足");
        }


    }

    @DataSource(value = DataSourceType.BEPR)
    public boolean insertToBuy(OutPut outPut,NxpSparePartSparePart nxpSparePartSparePart){

//        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.BEPR.name());
        // 史工数据库提交信息
        Tobuy tobuy = new Tobuy();
        tobuy.setDescription(nxpSparePartSparePart.getS_description());
        tobuy.setPartID(nxpSparePartSparePart.getS_id());
        tobuy.setModel(nxpSparePartSparePart.getS_model());
        tobuy.setUserID(outPut.getP_id());
        if (nxpSparePartSparePart.getS_vendor() == null)
            tobuy.setVendor("");
        else
            tobuy.setVendor(nxpSparePartSparePart.getS_vendor());
        Long number = 2*nxpSparePartSparePart.getS_safeamount().longValue()-nxpSparePartSparePart.getS_amount().longValue();
        tobuy.setQuantity(number);
        tobuyMapper.insertTobuy(tobuy);

//        DynamicDataSourceContextHolder.clearDataSourceType();

        return true;
    }

    @Override
    public BaseVO detail(NxpSparePartSparePart nxpSparePartSparePart) {
        NxpSparePartSparePart sparePart = nxpSparePartSparePartMapper.selectBySid(nxpSparePartSparePart.getS_id());
        if (sparePart.getS_picture() != null){
            String rep = sparePart.getS_picture().replace("./web","http://10.192.144.113");
            sparePart.setS_picture(rep);

        }
        NxpUserInfo nxpUserInfo = new NxpUserInfo();
        nxpUserInfo.setWbi_code(sparePart.getP_id());
        List<NxpUserInfo> list = nxpUserInfoService.selectNxpUserInfoList(nxpUserInfo);
        if (list != null){
            for (NxpUserInfo userInfo : list) {
                sparePart.setHead_user(userInfo.getChinese_name());
            }
        }

        return BaseVO.success(sparePart);
    }

    @Override
    public BaseVO sparePartList(NxpSparePartSparePart nxpSparePartSparePart) {
        Map<String, Object> map = new HashMap<>();
        List<NxpSparePartSparePart> nxpSparePartSpareParts = nxpSparePartSparePartMapper.selectByNxpSparePartSparePart(nxpSparePartSparePart);
        int j = 0;
        List<NxpSparePartSparePart> list = new ArrayList<>();
        for (NxpSparePartSparePart sparePartSparePart : nxpSparePartSpareParts) {
            if (sparePartSparePart.getS_picture() != null){
                String rep = sparePartSparePart.getS_picture().replace("./web","http://10.192.144.113");
                sparePartSparePart.setS_picture(rep);
            }

            j++;
            if (j>=nxpSparePartSparePart.getPage() && j<=(nxpSparePartSparePart.getPage() + nxpSparePartSparePart.getDefault_page())){
                list.add(sparePartSparePart);
            }


        }
        int i = nxpSparePartSparePartMapper.countByNxpSparePartSparePart(nxpSparePartSparePart);
        map.put("data_content", list);
//        map.put("data_content", new PageInfo<>(nxpSparePartSpareParts));
        map.put("total", i);
        return BaseVO.success(map);
    }

    @Override
    public BaseVO getDeviceType(Equipment equipment) {
        List<Equipment> deviceModel = nxpSparePartSparePartMapper.getDeviceModel(equipment);
//        for (Equipment equipment1 : deviceModel) {
//            equipment1.setE_model(equipment1.getE_id());
//        }
        return BaseVO.success(deviceModel);
    }

    @Override
    public BaseVO getDeviceModel(Equipment equipment) {
        List<Equipment> deviceModel = nxpSparePartSparePartMapper.getDeviceModel1(equipment);
        return BaseVO.success(deviceModel);
    }


    /**
     * 备件领取邮件或备件采购邮件发送
     * @param isBuy 是否为采购
     * @param nxpSparePartSparePart 备件基本信息
     * @param amount    领取数量
     * @param receiverName  领取人
     * @param facility  设备名
     * @return
     */
    public boolean sendPartMail(boolean isBuy,NxpSparePartSparePart nxpSparePartSparePart,int amount,String receiverName,String facility,String to) {
        // 1. 判断发送头，是采购通知还是领取通知
        String buy = "";
        String title = "";
        if (isBuy) {
            // 采购
            buy = " <p><h1 style=\"color:red\">库存量已小于安全量，请尽快登录POMS系统购买！</h1></p>";
            title = nxpSparePartSparePart.getS_id() + " 备件领取、采购通知(PDA)";
        } else {
            // 领取
            title = nxpSparePartSparePart.getS_id() + " 备件领取通知(PDA)";
        }

        String body = "<html>" +
                        "<body>" +
                            " <p style=\"font-size:12pt;\">以下内容为系统自动发送，请勿回复。</p>" +
                            "<hr>" +
                            " <p><h1>以下备件被 <span style=\"color:red\">领取</span></h1></p>" +
                            buy +
                            " <ul> " +
                                " <li><span style = 'color:#4472C4' > 备件料号：</span> <span style = 'color:red'>" + nxpSparePartSparePart.getS_id() + "</span> </li> " +
                                " <li><span style = 'color:#4472C4' > 备件类型：</span> " + nxpSparePartSparePart.getK_kind() + "</li>" +
                                " <li><span style = 'color:#4472C4' > 备件型号：</span> " + nxpSparePartSparePart.getS_model() + "</li>" +
                                " <li><span style = 'color:#4472C4' > 备件描述：</span> " + nxpSparePartSparePart.getS_description() + "</li>" +
                                " <li><span style = 'color:#4472C4' > 领取数量：</span> <font color = \"red\" > <b>" + amount + "</b> </font></li>" +
                                " <li><span style = 'color:#4472C4' > 剩余数量：</span> " + nxpSparePartSparePart.getS_amount() + "</li>" +
                                " <li><span style = 'color:#4472C4' > 安全量&nbsp&nbsp：</span> " + nxpSparePartSparePart.getS_safeamount() + "</li>" +
                                " <li><span style = 'color:#4472C4' > 领用人&nbsp&nbsp：</span> " + receiverName + "</li>" +
                                " <li><span style = 'color:#4472C4' > 领用时间：</span> " + DateUtil.now() + "</li>" +
                                " <li><span style = 'color:#4472C4' > 领用目的：</span> " + facility + "</li> " +
                            " </ul>" +
                            " <hr>" +
                            "<p><i>如邮件出现异常请将邮件转发给<span lang=EN-US><a href = \"mailto:Fred.han@nxp.com\"> Fred.Han@nxp.com</span></i></p>" +
                        " </body>" +
                       "</html>";
        try {
            mailService.sendHtmlMail(to,title,body);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 当库存为0时，催促采购邮件
     * @return
     */
    public boolean partUrgeBuySendMail(NxpSparePartSparePart nxpSparePartSparePart,String receiverName,String lastTime,String to){
        String title = "抓紧采购备件 " + nxpSparePartSparePart.getS_id();
        String body =
                "<html>" +
                    "<body>" +
                    " <p style=\"font-size:12pt;\">以下内容为系统自动发送，请勿回复。</p>" +
                    "<hr>" +
                    " <p><h1 style=\"color:red\">请尽快进入POMS系统采购以下备件</h1></p>" +
                    " <ul> " +
                        " <li><span style = 'color:#4472C4' > 备件料号：</span> <span style = 'color:red'>" + nxpSparePartSparePart.getS_id() + "</span> </li> " +
                        " <li><span style = 'color:#4472C4' > 备件类型：</span> " + nxpSparePartSparePart.getK_kind() + "</li>" +
                        " <li><span style = 'color:#4472C4' > 备件型号：</span> " + nxpSparePartSparePart.getS_model() + "</li>" +
                        " <li><span style = 'color:#4472C4' > 备件描述：</span> " + nxpSparePartSparePart.getS_description() + "</li>" +
                        " <li><span style = 'color:#4472C4' > 当前数量：</span> <font color = \"red\" > <b>0</b> </font></li>" +
                        " <li><span style = 'color:#4472C4' > 安全量&nbsp&nbsp：</span> " + nxpSparePartSparePart.getS_safeamount() + "</li>" +
                        " <li><span style = 'color:#4472C4' > 需求人&nbsp&nbsp：</span> " + receiverName + "</li>" +
                        " <li><span style = 'color:#4472C4' > 最后领用：</span> " + lastTime + "</li>" +
                    " </ul>" +
                    " <hr>" +
                    "<p><i>如邮件出现异常请将邮件转发给<span lang=EN-US><a href = \"mailto:Fred.han@nxp.com\"> Fred.Han@nxp.com</span></i></p>" +
                    " </body>" +
                "</html>";

        try {
            mailService.sendHtmlMail(to,title,body);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
