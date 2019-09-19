package com.zxg.plustest.common.config.jwt;

import com.zxg.plustest.common.response.JsonResult;
import org.apache.shiro.authc.*;

/**
 * 异常和处理
 *
 * 直接能重复使用
 */
public class Exctption {

    public JsonResult exceptionHandling(Class<? extends Exception> exctption){
        String exceptionName = exctption.getName();
        JsonResult jsonResult = new JsonResult();
        String errorMsg = "系统异常，请重试";
        if (IncorrectCredentialsException.class.getName().equals(exceptionName) ||
                AuthenticationException.class.getName().equals(exceptionName)) {
            errorMsg = "用户名/密码错误";
        } else if (UnknownAccountException.class.getName().equals(exceptionName)) {
            errorMsg = "用户名/密码错误";
        } else if (LockedAccountException.class.getName().equals(exceptionName)) {
            errorMsg = "账户已被冻结，请联系系统管理员重新激活";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionName)) {
            errorMsg = "账户已被锁定，请10分钟后再试";
        }
        jsonResult.setCode("error");
        jsonResult.setMessage(errorMsg);

        return jsonResult;
    }

}
