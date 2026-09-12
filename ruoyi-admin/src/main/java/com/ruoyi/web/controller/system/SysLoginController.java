package com.ruoyi.web.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.shiro.token.LoginType;
import com.ruoyi.framework.shiro.token.UserToken;
import com.ruoyi.system.domain.NxpUser;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.INxpUserInfoService;
import com.ruoyi.system.service.INxpUserService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Controller
public class SysLoginController extends BaseController
{

    @Resource
    private INxpUserService nxpUserService;
    @Resource
    private INxpUserInfoService nxpUserInfoService;
    @Resource
    private SysPasswordService passwordService;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private ISysUserService sysUserService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String idMd5,String password, Boolean rememberMe)
    {
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
//        Subject subject = SecurityUtils.getSubject();
        // username等同于userId
        // 1. 传输的id判断md5加密是否一致
        String idMd5Str = SecureUtil.md5(username);
        if (!idMd5.equalsIgnoreCase(idMd5Str)){
            return error("认证失败");
        }
        // 1. 先要去onebe表查userId存不存在
        NxpUser nxpUser = nxpUserService.selectByUserInfoId(Long.valueOf(username));
        if (nxpUser == null){
            return error("用户不存在");
        }
        NxpUserInfo userInfo = nxpUserInfoService.selectNxpUserInfoById(nxpUser.getUserId());
        username = nxpUser.getName();
        // 2. 如果存在，判断自己库中是否存在该username，如不存在，则新增一条存到自己数据库
        SysUser sysUser = sysUserService.selectUserByLoginName(username);
        if (sysUser == null){
            SysUser user = new SysUser();
            user.setUserId(nxpUser.getId());
            user.setLoginName(nxpUser.getName());
            user.setUserName(userInfo.getChinese_name());
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(passwordService.encryptPassword(user.getLoginName(), "123456", user.getSalt()));
            boolean regFlag = sysUserService.registerUser(user);
            if (regFlag){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(user.getUserId());
                sysUserRole.setRoleId(2L);
                List<SysUserRole> userRoles = new ArrayList<>();
                userRoles.add(sysUserRole);
                sysUserRoleMapper.batchUserRole(userRoles);
            }
        }

        UserToken token = new UserToken(username, LoginType.NOPASSWD);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    /*@GetMapping("/unauth")
    public String unauth()
    {
        return "redirect:http://www.mcdkj.cn/site/login";
    }*/
    @GetMapping("/unauth")
    public void testRed(HttpServletResponse response) throws Exception{
        response.sendRedirect("http://onebe.ap.freescale.net:9001/site/login");
    }


    /**
     *   对字符串 MD5 无盐值加密
     *
     * @param plainText
     * 		传入要加密的字符串
     * @return
     *  	MD5加密后生成32位(小写字母+数字)字符串
     */
    public static String MD5Lower(String plainText) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            md.update(plainText.getBytes());

            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值。1 固定值
            return new BigInteger(1,  md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Lower("221"));
        System.out.println(SecureUtil.md5("221"));
    }
}
