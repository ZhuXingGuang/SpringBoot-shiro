package com.zxg.plustest.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zxg.plustest.entity.Role;
import com.zxg.plustest.common.response.JsonResult;
import com.zxg.plustest.service.IRoleService;
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
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/getRoleList")
    public JsonResult getRoleList(){

        JsonResult jsonResult = new JsonResult();

        try {
            List<Role> roles = roleService.selectList(new EntityWrapper<Role>());

            jsonResult.setCode("ok");

            jsonResult.setDate(roles);

        } catch (Exception e) {

            jsonResult.setDate("系统异常，请刷新重试!!!");
        }

        return jsonResult;
    }

}
