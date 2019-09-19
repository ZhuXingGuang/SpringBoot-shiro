package com.zxg.plustest.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zxg.plustest.entity.User;
import com.zxg.plustest.common.response.JsonResult;
import com.zxg.plustest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxg
 * @since 2019-07-22
 */
@RestController
public class UserController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/getUserList")
    public JsonResult getUserList(){

        JsonResult jsonResult = new JsonResult();

        try {
            Page page = iUserService.selectPage(new Page(1, 10), new EntityWrapper<User>());

            List<User> users = iUserService.selectList(new EntityWrapper<>());

            jsonResult.setCode("ok");

            jsonResult.setDate(users);

        } catch (Exception e) {

            jsonResult.setMessage("系统异常，请刷新重试");
        }

        return jsonResult;
    }




}
