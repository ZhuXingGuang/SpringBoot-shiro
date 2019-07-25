package com.zxg.plustest.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zxg.plustest.entity.User;
import com.zxg.plustest.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private IUserService iUserService;

    /*
     * 真实授权抽象方法，供子类调用(验权four)
     *
     * 这个是当登陆成功之后会被调用，看当前的登陆角色是有有权限来进行操作
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("doGetAuthorizationInfo方法");
        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
//        Set<Role> roleSet = user.getRoles();//拿到角色
//        if (CollectionUtils.isNotEmpty(roleSet)) {
//            for(Role role : roleSet) {
//                roleNameList.add(role.getRole());//拿到角色
//                Set<Permission> permissionSet = role.getPermissions();
//                if (CollectionUtils.isNotEmpty(permissionSet)) {
//                    for (Permission permission : permissionSet) {
//                        permissionList.add(permission.getMenuName());
//                    }
//                }
//            }
//        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);//拿到权限
        info.addRoles(roleNameList);//拿到角色
        return info;
    }

    /*
     * 简单的说就是获取用户登陆的信息（two）
     * 用于认证登录，认证接口实现方法，该方法的回调一般是通过subject.login(token)方法来实现的
     * AuthenticationToken 用于收集用户提交的身份（如用户名）及凭据（如密码）：
     * AuthenticationInfo是包含了用户根据username返回的数据信息，用于在匹马比较的时候进行相互比较
     *
     * shiro的核心是java servlet规范中的filter，通过配置拦截器，使用拦截器链来拦截请求，如果允许访问，则通过。
     * 通常情况下，系统的登录、退出会配置拦截器。登录的时候，调用subject.login(token),token是用户验证信息，
     * 这个时候会在Realm中doGetAuthenticationInfo方法中进行认证。这个时候会把用户提交的验证信息与数据库中存储的认证信息，将所有的数据拿到，在匹配器中进行比较
     * 这边是我们自己实现的CredentialMatcher类的doCredentialsMatch方法，返回true则一致，false则登陆失败
     * 退出的时候，调用subject.logout()，会清除回话信息
     *
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("将用户，密码填充完UsernamePasswordToken之后，进行subject.login(token)之后");
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken) token;//这边是界面的登陆数据，将数据封装成token
        String username = userpasswordToken.getUsername();
        User user = iUserService.selectOne(new EntityWrapper<User>().eq("userName", username));
        return new SimpleAuthenticationInfo(user,user.getPassWord(),this.getClass().getName());
    }


}
