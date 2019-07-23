package com.zxg.plustest.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zxg.plustest.entity.User;
import com.zxg.plustest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private IUserService iUserService;

    @RequestMapping("/gologin")
    public String Login(){

        System.out.println("访问登录页面成功!!!");

        return "view/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public String GoIndex(User user){

        String report = "error";

        if(!StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassWord())){

            System.out.println("登录成功");

            report = "ok";
        }else{

            System.out.println("登陆失败");
        }

        return report;
    }

    @RequestMapping("/goIndex")
    public String  goIndex(){

        System.out.println("成功到达主页面");

        return "view/index";
    }


    public User getUserByUserName(User user) {


        User userDb = iUserService.selectOne(new EntityWrapper<User>().eq("userName", user.getUserName()));

        return userDb;

    }





}
