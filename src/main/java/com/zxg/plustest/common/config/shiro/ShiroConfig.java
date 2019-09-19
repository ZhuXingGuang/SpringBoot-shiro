package com.zxg.plustest.common.config.shiro;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

    // remeberMe cookie 加密的密钥 各个项目不一样 默认AES算法 密钥长度（128 256 512）
    private static final String ENCRYPTION_KEY = "3AvVhmFLUs0KTA3Kprsdag==";

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());//必须设置的securityManager
        bean.setLoginUrl("/login");//设置登录的url
        bean.setSuccessUrl("/Index");//设置登陆成功的url
        bean.setUnauthorizedUrl("/unauthorized");//设置权限不足的url
        /*
         * 拦截器
         * anon:无需进行认证就能访问
         * authc:必须经过认证才能访问
         * user:如果使用rememberMe的功能可以直接访问
         * perms:该资源必须得到资源权限才能访问
         * role:该资源必须得到角色权限才可以访问
         * */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/jquery/**","anon");
        filterChainDefinitionMap.put("/ztree/**","anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        //所有的路径都拦截，被UserFilter拦截，这里会判断用户有没有登陆(基于session)
        //filterChainDefinitionMap.put("/**", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);//设置一个拦截器链
        return bean;
    }
    /*
     * 注入一个securityManager
     * 原本以前我们是可以通过ini配置文件完成的，代码如下：
     *  1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
     *  Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
     *  2、得到SecurityManager实例 并绑定给SecurityUtils
     *  SecurityManager securityManager = factory.getInstance();
     *  SecurityUtils.setSecurityManager(securityManager);
     * */
    @Bean
    public SecurityManager securityManager() {
        //这个DefaultWebSecurityManager构造函数,会对Subject，realm等进行基本的参数注入
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm());//往SecurityManager中注入Realm，代替原本的默认配置
        /**
         * manager.setRememberMeManager(rememberMeManager());//把cookie管理器交给SecurityManager
         */
        return manager;
    }

    /*
     * 自定义的方法用来获取db中的用户信息
     * 以及权限等信息！！！
     * 现在将其交个Spring的IOC进行管理
     * @param matcher
     * @return
     */
    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        //这边可以选择是否将认证的缓存到内存中，现在有了这句代码就将认证信息缓存的内存中了
        authRealm.setCacheManager(new MemoryConstrainedCacheManager());
        //最简单的情况就是明文直接匹配，然后就是加密匹配，这里的匹配工作则就是交给CredentialsMatcher来完成
        authRealm.setCredentialsMatcher(credentialMatcher());
        return authRealm;
    }

    /*
     * Realm在验证用户身份的时候，要进行密码匹配
     * 最简单的情况就是明文直接匹配，然后就是加密匹配
     * 这个是我们自定义的密码匹配方式！
     * 现在将其交个Spring的IOC进行管理
     * 时间磨灭了原有的单纯，生活践踏着年少的梦想.
     * 举杯邀月时眉头紧锁，低眉颔首间相思萦绕.
     */
    @Bean
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher(getEhCacheManager());
    }

    /*
     * 缓存管理配置
     * @return
     */
    @Bean
    public EhCacheManager getEhCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:cacheConfig/ehcache.xml");
        return ehCacheManager;
    }

    /*
     * Cookie 对象 用户免登陆操作，但是需要配置filter /** 权限为user生效
     * @return
     */
    public SimpleCookie rememMeCookie() {
        // 初始化设置cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("shiro-remember");
        simpleCookie.setMaxAge(60*60*24*15);// 设置cookie的生效时间
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /*
     * cookie 管理对象，记住我功能
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememMeCookie());
        // remeberMe cookie 加密的密钥 各个项目不一样 默认AES算法 密钥长度（128 256 512）
        cookieRememberMeManager.setCipherKey(Base64.decode(ENCRYPTION_KEY));
        return cookieRememberMeManager;
    }

    /*
     * 以下AuthorizationAttributeSourceAdvisor,
     * DefaultAdvisorAutoProxyCreator两个类是为了支持shiro注解
     * */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


}
