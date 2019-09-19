package com.zxg.plustest.web;


import com.zxg.plustest.common.response.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxg
 * @since 2019-07-22
 */
@RestController
public class PermissionController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/showIndex")
    public JsonResult Index() {

        JsonResult jsonResult = new JsonResult();

        try {
            String user = (String) SecurityUtils.getSubject().getSession().getAttribute("user");

            StringBuffer sql = new StringBuffer();

            sql.append(" select * from permission where id in ( ");

            sql.append(" select pid from role_permission where rid in ( ");

            sql.append(" select rid from user_role where uid = ");

            sql.append("(SELECT id from user where userName = ?)))");

            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql.toString(), user);

            jsonResult.setCode("ok");

            jsonResult.setDate(maps);

        } catch (Exception e) {

            jsonResult.setCode("error");

            jsonResult.setMessage("系统异常，请刷新试试");
        }

        return jsonResult;
    }

}
