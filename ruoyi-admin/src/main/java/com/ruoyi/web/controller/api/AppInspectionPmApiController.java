package com.ruoyi.web.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.NxpInspectionPmMapper;
import com.ruoyi.system.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.beans.SimpleBeanInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 17:09
 */
@RestController
@RequestMapping("/api/inspection-pm-api")
public class AppInspectionPmApiController extends BaseController {

    @Resource
    private IInspectionPmApiService inspectionPmApiService;
    @Resource
    private INxpInspectionPmRuleService iNxpInspectionPmRuleService;
    @Resource
    private INxpFacilityService iNxpFacilityService;
    @Resource
    private INxpInspectionPmService iNxpInspectionPmService;
    @Resource
    private INxpInspectionPmFacilityService iNxpInspectionPmFacilityService;
    @Resource
    private INxpInspectionPmGroupItemService iNxpInspectionPmGroupItemService;
    @Resource
    private INxpInspectionPmCheckService iNxpInspectionPmCheckService;
    @Resource
    private NxpInspectionPmMapper nxpInspectionPmMapper;

    /**
     * 获取pm检查结果
     *
     * @return
     */
    @PostMapping("/get-pm-check")
    public BaseVO getPmCheck(@RequestBody NxpInspectionPmCheck nxpInspectionPmCheck) {
        return inspectionPmApiService.getPmCheck(nxpInspectionPmCheck);
    }

    /**
     * 获取pm复查结果
     *
     * @return
     */
    @PostMapping("/get-pm-review")
    public BaseVO getPmReview(@RequestBody NxpInspectionPmCheck nxpInspectionPmCheck) {
        return inspectionPmApiService.getPmReview(nxpInspectionPmCheck);
    }

    /**
     * 与我相关PM列表
     *
     * @param nxpInspectionPm
     * @return
     */
    @PostMapping("/pm-list")
    public BaseVO pmList(@RequestBody NxpInspectionPm nxpInspectionPm) {
        if (nxpInspectionPm.getPageNum() == null) {
            nxpInspectionPm.setPageNum(1);
        }
        if (nxpInspectionPm.getPageSize() == null) {
            nxpInspectionPm.setPageSize(10);
        }
        PageHelper.startPage(nxpInspectionPm.getPageNum(), nxpInspectionPm.getPageSize());
        return BaseVO.success(new PageInfo(inspectionPmApiService.pmList(nxpInspectionPm)));
    }

    /**
     * PM列表
     *
     * @param nxpInspectionPm
     * @return
     */
    @PostMapping("/pm-list-facility")
    public BaseVO pmListByFacility(@RequestBody NxpInspectionPm nxpInspectionPm) {
        if (nxpInspectionPm.getPageNum() == null) {
            nxpInspectionPm.setPageNum(1);
        }
        if (nxpInspectionPm.getPageSize() == null) {
            nxpInspectionPm.setPageSize(10);
        }
        PageHelper.startPage(nxpInspectionPm.getPageNum(), nxpInspectionPm.getPageSize());
        return BaseVO.success(new PageInfo(inspectionPmApiService.pmListByFacility(nxpInspectionPm)));
    }

    /**
     * PM复查列表
     *
     * @param nxpInspectionPm
     * @return
     */
    @PostMapping("/pm-review-list-facility")
    public BaseVO pmReviewListByFacility(@RequestBody NxpInspectionPm nxpInspectionPm) {
        if (nxpInspectionPm.getPageNum() == null) {
            nxpInspectionPm.setPageNum(1);
        }
        if (nxpInspectionPm.getPageSize() == null) {
            nxpInspectionPm.setPageSize(10);
        }
        PageHelper.startPage(nxpInspectionPm.getPageNum(), nxpInspectionPm.getPageSize());
        return BaseVO.success(new PageInfo(inspectionPmApiService.pmReviewListByFacility(nxpInspectionPm)));
    }

    /**
     * 检查周PM内容
     *
     * @param nxpInspectionPm
     * @return
     */
    @PostMapping("/pm-check-info")
    public BaseVO pmCheckInfo(@RequestBody NxpInspectionPm nxpInspectionPm) {
        return BaseVO.success(inspectionPmApiService.pmInfo(nxpInspectionPm));
    }

    /**
     * 提交周PM检查项
     *
     * @return
     */
    @PostMapping("/pm-check")
    public BaseVO pmCheck(@RequestBody ParamsDTO paramsDTO) {
        return inspectionPmApiService.pmCheck(paramsDTO);
    }

    /**
     * 提交周PM复查项
     *
     * @return
     */
    @PostMapping("/pm-review")
    public BaseVO pmReview(@RequestBody PmReviewDTO pmReviewDTO) {
        return inspectionPmApiService.pmReview(pmReviewDTO);
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/camera")
    public BaseVO uploadFile(@RequestBody Picture picture) {
        return inspectionPmApiService.uploadFile(picture);
    }

    /**
     * pm大列表
     *
     * @param nxpInspectionPm
     * @return
     */
    @PostMapping("/inspectionPmList")
    public BaseVO inspectionPmList(@RequestBody NxpInspectionPm nxpInspectionPm) {
        return inspectionPmApiService.getPmList(nxpInspectionPm);
    }

    /**
     * pm定时任务 - 每月1日生成正月的复查计划
     *
     * @param
     * @return
     */
    @GetMapping("/regularReview/{id}")
    public BaseVO regularReview(@PathVariable("id") Long id) throws ParseException {
//        判断当前是否为当月的一号
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH) + 1;
        int date = rightNow.get(Calendar.DATE);
        if (date != id)
            return BaseVO.error(id.toString());

//        获取当月的天数
        rightNow.set(Calendar.DATE, 1);
        rightNow.roll(Calendar.DATE, -1);
        int maxDate = rightNow.get(Calendar.DATE) - 7;

//        获取当前设备数量（因为设备数量不全，只能通过 rule 表获取设备数量）
        NxpInspectionPmRule nxpInspectionPmRule = new NxpInspectionPmRule();
        List<NxpInspectionPmRule> nxpInspectionPmRules = iNxpInspectionPmRuleService.selectNxpInspectionPmRuleListGroupBy(nxpInspectionPmRule);
        int size = nxpInspectionPmRules.size();

//        计算平均每天检查设备数量
        int countWork = countWorkDay(year, month) - 5;
        int countEveryDay = size / countWork;
        int remainder = size % countWork;

        int countRuleFacility = 0;
        for (int j = 1; j <= maxDate; j++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.DATE, j);

            boolean result = isWorkDay(cal);
            if (result) {
//                计算是否需要增加余数
                int cycle = 0;
                if (j > remainder) {
                    cycle = countEveryDay;
                } else {
                    cycle = countEveryDay + 1;
                }

//                循环规则取设备
                int k = 0;
                for (NxpInspectionPmRule inspectionPmRule : nxpInspectionPmRules) {
                    if (countRuleFacility > 0) {
                        if (k < countRuleFacility) {
                            k++;
                            continue;
                        }
                    }
                    if (k >= (countRuleFacility + cycle)) {
//                    记录当前遍历到哪一个设备了
                        countRuleFacility = k;
                        break;
                    }

//                    获取复查人信息
                    NxpFacility nxpFacility = iNxpFacilityService.selectNxpFacilityById(Long.valueOf(inspectionPmRule.getFacility_id()));
                    if (nxpFacility != null) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateTemp = year + "-" + month + "-" + j;

                        NxpInspectionPm nxpInspectionPm = new NxpInspectionPm();
                        nxpInspectionPm.setFacility_id(Long.valueOf(inspectionPmRule.getFacility_id()));
                        nxpInspectionPm.setReview_user_info_id(nxpFacility.getReview_user());
                        nxpInspectionPm.setCheck_time(sdf.parse(dateTemp));
                        nxpInspectionPm.setStatus(1);
//                        Date datetemp1=new Date();
//                        long timestamp=datetemp1.getTime();
//                        nxpInspectionPm.setCreated_at(timestamp);
//                        nxpInspectionPm.setUpdated_at(timestamp);
                        nxpInspectionPm.setType((long) 2);
                        nxpInspectionPm.setDeal_user_id(nxpFacility.getReview_user());
                        iNxpInspectionPmService.insertNxpInspectionPm(nxpInspectionPm);

                    }
                    k++;


                }
            }
        }

        return BaseVO.success();
    }

    /**
     *      * 获取指定年月有多少个bai工作日）
     *      * @param year
     *      * @param month
     *      
     */
    public static int countWorkDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        // 月份是从0开始du计算，所以需要减去1
        c.set(Calendar.MONTH, month - 1);
        // 当月最后一天的日期dao
        int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 开始日期为1号
        int start = 1;
        // 计数
        int count = 0;
        while (start <= max) {
            c.set(Calendar.DAY_OF_MONTH, start);
            if (isWorkDay(c)) {
                count++;
            }
            start++;
        }
        return count;
    }

    // 判断是否工作日（未排除法定节假日，由于涉及到农历节日，处理很麻烦）
    public static boolean isWorkDay(Calendar c) {
        // 获取星期,1~7,其中1代表星期日，2代表星期一 ... 7代表星期六
        int week = c.get(Calendar.DAY_OF_WEEK);
        // 不是周六和周日的都认为是工作日
        return week != Calendar.SUNDAY && week != Calendar.SATURDAY;
    }


    /**
     * pm定时任务 - 每天生成具体检查项 pmCheck
     *
     * @param
     * @return
     */
    @PostMapping("/everydayCreatePmCheck")
    public BaseVO everydayCreatePmCheck() throws ParseException {
//        获取当前日期
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH) + 1;
        int date = rightNow.get(Calendar.DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateTemp = year + "-" + month + "-" + date;

//        获取过期未检查的周PM
        NxpInspectionPm nxpInspectionPm1 = new NxpInspectionPm();
        nxpInspectionPm1.setStatus(1);
        nxpInspectionPm1.setCheck_time(sdf.parse(dateTemp));
        List<NxpInspectionPm> nxpInspectionPms1 = iNxpInspectionPmService.selectNxpInspectionPmOverDue(nxpInspectionPm1);
        if (nxpInspectionPms1 != null) {
            for (NxpInspectionPm nxpInspectionPm : nxpInspectionPms1) {
                nxpInspectionPm.setStatus(3);
                nxpInspectionPmMapper.updateNxpInspectionPm(nxpInspectionPm);

//                推送逾期消息
//                推送逾期邮件
            }
        }


//        筛选需要生成check详情的pm
        NxpInspectionPm nxpInspectionPm = new NxpInspectionPm();
        nxpInspectionPm.setStatus(1);
        nxpInspectionPm.setCheck_time(sdf.parse(dateTemp));
        List<NxpInspectionPm> nxpInspectionPms = iNxpInspectionPmService.selectNxpInspectionPmList(nxpInspectionPm);
        for (NxpInspectionPm inspectionPm : nxpInspectionPms) {

//            获取检查项
            NxpInspectionPmFacility nxpInspectionPmFacility1 = new NxpInspectionPmFacility();
            nxpInspectionPmFacility1.setFacility_id(inspectionPm.getFacility_id());
            List<NxpInspectionPmFacility> nxpInspectionPmFacilities = iNxpInspectionPmFacilityService.selectNxpInspectionPmFacilityListByFacilityId(nxpInspectionPmFacility1);
            NxpInspectionPmGroupItem nxpInspectionPmGroupItem = new NxpInspectionPmGroupItem();
            for (NxpInspectionPmFacility nxpInspectionPmFacility : nxpInspectionPmFacilities) {
                nxpInspectionPmGroupItem.setGroup_id(nxpInspectionPmFacility.getGroup_id());
            }
            List<NxpInspectionPmGroupItem> nxpInspectionPmGroupItems = iNxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemList(nxpInspectionPmGroupItem);
            for (NxpInspectionPmGroupItem inspectionPmGroupItem : nxpInspectionPmGroupItems) {
                NxpInspectionPmCheck nxpInspectionPmCheck = new NxpInspectionPmCheck();
                nxpInspectionPmCheck.setPm_id(inspectionPm.getId());
                nxpInspectionPmCheck.setType(inspectionPm.getType());
                nxpInspectionPmCheck.setUser_info_id(inspectionPm.getUserInfo_id());
                nxpInspectionPmCheck.setGroup_item_id(inspectionPmGroupItem.getId());
                iNxpInspectionPmCheckService.insertNxpInspectionPmCheck(nxpInspectionPmCheck);
            }

        }
        return BaseVO.success();

    }

}
