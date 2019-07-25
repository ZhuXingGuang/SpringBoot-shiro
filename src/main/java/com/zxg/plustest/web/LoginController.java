package com.zxg.plustest.web;


import com.zxg.plustest.entity.User;
import com.zxg.plustest.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private IUserService iUserService;

    //跳转到登录界面
    @RequestMapping("/login")
    public String Login(){

        System.out.println("成功到达登录界面！！！");

        return "view/login";
    }

    //登录成功之跳转到主页面
    @RequestMapping("/Index")
    public String  Index(){

        System.out.println("成功到达主页面！！！");

        return "view/index";
    }

    //退出登录
    @RequestMapping("/logout")
    public String logout() {

        System.out.println("成功退出系统！！！");

        Subject subject = SecurityUtils.getSubject();//取出当前验证主体
        if (subject != null) {
            subject.logout();//不为空，执行一次logout的操作，将session全部清空
        }
        return "view/login";
    }

    //验证不通过的情况
    @RequestMapping("/unauthorized")
    public String unauthorized() {

        return "view/unauthorized";
    }

    @ResponseBody
    @RequestMapping("/admin")
    //注解之后只是返回json数据,不返回界面
    public String admin() {
        return "admin success";
    }

    @ResponseBody
    @RequestMapping("/edit")
    public String edit() {
        return "edit success";
    }

    //进行验证登录
    @ResponseBody
    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("userName") String userName,
                            @RequestParam("passWord") String passWord,
                            HttpSession session) {

        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        Subject subject = SecurityUtils.getSubject();
        try {
            System.out.println("获取到信息，开始验证！！");
            subject.login(token);//登陆成功的话，放到session中（one）
            User user = (User) subject.getPrincipal();
            session.setAttribute("user", user);
            System.out.println("登录成功");
            return "ok";
        } catch (Exception e) {
            System.out.println("登陆失败");
            return "error";
        }
    }

}
