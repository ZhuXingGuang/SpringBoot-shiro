package com.zxg.plustest.common.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码验证
 * 自认密码可以再取出来的时候进行自定义的组装（md5,sha1,加盐这种操作）
 * 本质上那个可以继承（SimpleCredentialsMatcher这个类）
 * 在这里我们还可进行错误次数限制（一般都会有这些登录需求）
 * 通过用户的唯一标识得到 AuthenticationInfo 然后和
 * AuthenticationToken （用户名 密码），进行比较
 */
public class CredentialMatcher extends HashedCredentialsMatcher {
    //定义允许错误次数
    private static final int ERROR_COUNT = 5;
    /**
     * shiro给我们提供的缓存方法，以键值对的形式进行存储
     * 具体的有效时间是多久，需要结合cacheConfig中的xml文件进行决定
     *
     * 该缓存信息只适合单机版，如果进行集群操作
     * 那就需要使用redis或其他进行缓存共享
     */
    private Cache<String, AtomicInteger> passwordRetryCache;

    public CredentialMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }
    /**
     * 密码校验
     * @param token 用户提交的个人信息
     * @param info  db中的用户信息
     * @return
     *
     * 由于该方法是一个整体，在同一个变量会保证最后的结果
     * 即使存进去的登录错误次数是0但是只要登录错误该值机会变成1
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
       //进行数据类型的转换
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取用户上传的用户名
        String username = new String(usernamePasswordToken.getUsername());
        //根据用户名在缓存中获取存放在AtomicInteger中的用户登录错误信息
        AtomicInteger errorCount = null;
        if(passwordRetryCache!=null){
            errorCount = passwordRetryCache.get(username);
        }
        //如果用户的登录错误次数是null的话，说明用户没有出现错误登录
        if(errorCount==null){
            //把用户的错误登录次数初始化为0
            errorCount = new AtomicInteger(0);
            //把用户的错路次数放入到缓存当中
            passwordRetryCache.put(username,errorCount);
        }
        // incrementAndGet和getAndIncrement都调用了Unsafe
        // 类中的 getAndAddInt（） 方法，区别是：
        //① 前者，先+1，再返回
        //② 后者，先返回，再 +1
        if(errorCount.incrementAndGet() > ERROR_COUNT){
            //抛出异常禁止接下来的操作
            throw new ExcessiveAttemptsException();
        }
        String password = new String(usernamePasswordToken.getPassword());
        String dbPassword = (String) info.getCredentials();//数据库里的密码
        //对比用户填写的登录的密码是否与数据库一致
        boolean matches = this.equals(password, dbPassword);
        //如果正确把用户登录错误信息信息在缓存中删除
        if(matches){
            passwordRetryCache.remove(username);
        }
        //返回验证结果
        return matches;
    }

}
