package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.service.IMailService;
import com.ruoyi.system.service.INxpFacilityUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yancheng Liu
 * @create 2021-03-11 8:54
 */
@RestController
@RequestMapping("/api")
public class ApiCommonController {

    @Resource
    private IMailService mailService;
    @Resource
    private INxpFacilityUserService nxpFacilityUserService;

    @GetMapping("/sendHtmlMail")
    public BaseVO sendHtmlMail(){

        partUrgeBuySendMail();
//        mailService.sendSimpleMail("591098461@qq.com","测试发送邮件","测试发送邮件01");
        return BaseVO.success();
    }

    public boolean partUrgeBuySendMail(){
        String title = "抓紧采购备件 " + "part.id";
        String body =
                "<html>" +
                        "<body>" +
                        " <p style=\"font-size:12pt;\">以下内容为系统自动发送，请勿回复。</p>" +
                        "<hr>" +
                        " <p><h1 style=\"color:red\">请尽快进入POMS系统采购以下备件</h1></p>" +
                        " <ul> " +
                        " <li><span style = 'color:#4472C4' > 备件料号：</span> <span style = 'color:red'>" + "part.id.Trim()" + "</span> </li> " +
                        " <li><span style = 'color:#4472C4' > 备件类型：</span> " + "part.kind" + "</li>" +
                        " <li><span style = 'color:#4472C4' > 备件型号：</span> " + "part.model" + "</li>" +
                        " <li><span style = 'color:#4472C4' > 备件描述：</span> " + "part.description" + "</li>" +
                        " <li><span style = 'color:#4472C4' > 当前数量：</span> <font color = \"red\" > <b>0</b> </font></li>" +
                        " <li><span style = 'color:#4472C4' > 安全量&nbsp&nbsp：</span> " + "part.safeAmount.ToString()" + "</li>" +
                        " <li><span style = 'color:#4472C4' > 需求人&nbsp&nbsp：</span> " + "user.Name" + "</li>" +
                        " <li><span style = 'color:#4472C4' > 最后领用：</span> " + "time.ToString()" + "</li>" +
                        " </ul>" +
                        " <hr>" +
                        "<p><i>如邮件出现异常请将邮件转发给<span lang=EN-US><a href = \"mailto:Fred.han@nxp.com\"> Fred.Han@nxp.com</span></i></p>" +
                        " </body>" +
                        "</html>";

        try {
            mailService.sendHtmlMail("591098461@qq.com",title,body);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 初始化设备相关人员
     * @return
     */
    @GetMapping("/initFacilityUser")
    public BaseVO initFacilityUser(){
        return nxpFacilityUserService.initFacilityUser();
    }

}
