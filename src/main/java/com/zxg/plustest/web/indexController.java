package com.zxg.plustest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class indexController {

    @RequestMapping("/userManagement")
    public String userManagement(){

        System.out.println("成功到达用户管理界面！！！");

        return "user/userManagement";
    }

    @RequestMapping("/roleManagement")
    public String roleManagement(){

        System.out.println("成功到达角色管理界面！！！");

        return "user/roleManagement";
    }

    @RequestMapping("/authorityManagement")
    public String authorityManagement(){

        System.out.println("成功到达权限管理界面！！！");

        return "user/userManagement";
    }



}
