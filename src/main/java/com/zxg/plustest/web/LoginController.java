package com.zxg.plustest.web;


import com.zxg.plustest.common.config.jwt.Exctption;
import com.zxg.plustest.common.response.JsonResult;
import com.zxg.plustest.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class LoginController {

    @Autowired
    private IUserService iUserService;

    //跳转到登录界面
    @RequestMapping("/login")
    public String Login(){

        System.out.println("成功到达登录界面！！！");

        return "login/login";
    }

    //登录成功之跳转到主页面
    @RequestMapping("/Index")
    public String  Index(){

        System.out.println("成功到达主页面！！！");

        return "index/index";
    }

    //验证不通过的情况
    @RequestMapping("/unauthorized")
    public String unauthorized() {

        return "error/unauthorized";
    }

    //退出登录
    @RequestMapping("/logout")
    public String logout() {

        System.out.println("成功退出系统！！！");

        Subject subject = SecurityUtils.getSubject();//取出当前验证主体
        if (subject != null) {
            subject.logout();//不为空，执行一次logout的操作，将session全部清空
        }
        return "login/login";
    }

    //进行验证登录
    @ResponseBody
    @RequestMapping("/loginUser")
    public JsonResult loginUser(@RequestParam("userName") String userName,
                                @RequestParam("passWord") String passWord,
                                @RequestParam("rememberMe")String rememberMe) {
        //使用用户的登录信息创建令牌！
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        Subject subject = SecurityUtils.getSubject();
        if (rememberMe.equals("YES")) {
            token.setRememberMe(true);
        }
        try {
            subject.login(token);//提交认证
            Object user = subject.getPrincipal();
            subject.getSession().setAttribute("user", user);
            System.out.println("登录成功");
            JsonResult jsonResult = new JsonResult();
            jsonResult.setCode("ok");
            return jsonResult;
        } catch (Exception e) {
            JsonResult jsonResult = new Exctption().exceptionHandling(e.getClass());
            System.out.println("登陆失败");
            return jsonResult;
        }
    }
}
