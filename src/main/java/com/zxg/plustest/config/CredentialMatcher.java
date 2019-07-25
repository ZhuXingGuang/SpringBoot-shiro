package com.zxg.plustest.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CredentialMatcher extends SimpleCredentialsMatcher {
    /*
     * 这就是传说中说的密码验证大关(three)
     * 这里是进行密码匹配的方法，自己定义
     * 通过用户的唯一标识得到 AuthenticationInfo 然后和 AuthenticationToken （用户名 密码），进行比较
     * */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("这边是密码校对");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());//用户上传的密码
        String dbPassword = (String) info.getCredentials();//数据库里的密码
        return this.equals(password, dbPassword);
    }
}
