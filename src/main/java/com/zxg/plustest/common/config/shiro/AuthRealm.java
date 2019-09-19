package com.zxg.plustest.common.config.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zxg.plustest.entity.*;
import com.zxg.plustest.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUser_roleService user_roleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRole_permissionService role_permissionService;
    @Autowired
    private IPermissionService permissionService;


    /*
     *
     * 授权，即权限验证，验证某个已经认证的用户是否拥有某个权限，即判断用户是否能做事情
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("登录成功开始进行权限认证！！！");
        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        Set<String> permissionList = new HashSet<>();
        Set<String> roleNameList = new HashSet<>();
        List<User_role> user_roleSet =
                //拿到角色用户中间表信息(可根据用户的id在中间表中查找对应的角色的集合)
                user_roleService.selectList(new EntityWrapper<User_role>().eq("uid", user.getId()));
        if (CollectionUtils.isNotEmpty(user_roleSet)) {
            for (User_role role : user_roleSet) {
                Role roleValue =
                        //根据角色用户中间表信息查找对应的角色信息
                        roleService.selectOne(new EntityWrapper<Role>().eq("id", role.getRid()));
                roleNameList.add(roleValue.getRole());//拿到角色
                List<Role_permission> role_permissionSet =
                        //拿到权限角色中间表信息(可根据角色的id在中间表中查找对应的权限的集合)
                        role_permissionService.selectList(new EntityWrapper<Role_permission>().eq("rid", roleValue.getId()));
                if (CollectionUtils.isNotEmpty(role_permissionSet)) {
                    for (Role_permission permission : role_permissionSet) {
                        Permission permissionvalue =
                                //根据权限角色中间表信息查找对应的权限信息
                                permissionService.selectOne(new EntityWrapper<Permission>().eq("id", permission.getPid()));
                        permissionList.add(permissionvalue.getMenuName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);  //权限信息
        info.addRoles(roleNameList);//角色信息
        //将权限放入shiro中
        return info;
    }

    /*
     * 身份认证/登录
     * 进行自己个性化的定制(判断账号是否被锁定)
     * AuthenticationToken这里面存放的是用户提交的表单信息
     *
     * 在这里我们可以在这里个性化自己的密码（md5/sha1）
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken) token;//进行数据类型的转换，目的是为了获取用户提交的表单信息
        String username = userpasswordToken.getUsername();//表单传递的用户名
        User user = userService.selectOne(new EntityWrapper<User>().eq("userName", username));//db中的用户信息
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(),//用户名
                user.getPassWord(),//密码
                this.getName()//realm name
        );
        return simpleAuthenticationInfo;
    }




}
