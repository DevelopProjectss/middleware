package org.wyyt.kafka.monitor.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wyyt.kafka.monitor.service.dto.SysAdminService;
import org.wyyt.kafka.monitor.service.dto.SysPageService;
import org.wyyt.kafka.monitor.shiro.AuthRealm;
import org.wyyt.kafka.monitor.shiro.CredentialsMatcher;

import java.util.LinkedHashMap;

/**
 * The configuration of Shiro
 * <p>
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize         10/1/2020      Initialize   *
 * *****************************************************************
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(final SecurityManager manager) {
        final ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);

        bean.setLoginUrl("/l"); // 配置登录的url和登录成功的url
        bean.setSuccessUrl("/index"); // 登录成功后要跳转的链接

        final LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/css/**", "anon"); // 静态资源
        filterChainDefinitionMap.put("/js/**", "anon"); // 静态资源
        filterChainDefinitionMap.put("/images/**", "anon"); // 静态资源
        filterChainDefinitionMap.put("/fonts/**", "anon"); // 静态资源
        filterChainDefinitionMap.put("/layuiadmin/**", "anon"); // 静态资源
        filterChainDefinitionMap.put("/favicon.ico", "anon"); // 静态资源
        filterChainDefinitionMap.put(bean.getLoginUrl(), "anon"); // 登录页面
        filterChainDefinitionMap.put("/login", "anon"); // 登录逻辑
        filterChainDefinitionMap.put("/quit", "anon"); // 登出逻辑
        filterChainDefinitionMap.put("/**", "authc");// 需要认证才可以访问
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    // 配置核心安全事务管理器
    @Bean
    public SecurityManager securityManager(@Qualifier("authRealm") final AuthRealm authRealm) {
        final DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm); // 设置自定义realm
        return manager;
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());

        sessionManager.setGlobalSessionTimeout(1800000); // 全局会话超时时间 单位毫秒,默认30分钟
        sessionManager.setDeleteInvalidSessions(true);// 是否开启删除无效的session对象 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true); // 是否开启定时调度器进行检测过期session 默认为true

        sessionManager.setSessionValidationInterval(3600000);
        sessionManager.setSessionIdUrlRewritingEnabled(false); // 取消url 后面的 JSESSIONID
        return sessionManager;
    }

    // 配置保存sessionId的cookie 注意：这里的cookie 不是[记住我]的cookie, 记住我需要一个cookie session管理
    // 也需要自己的cookie 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
    @Bean
    public SimpleCookie sessionIdCookie() {
        // 这个参数是cookie的名称
        final SimpleCookie simpleCookie = new SimpleCookie("sidKafkaMonitor");
        // setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        // 只能通过http访问，javascript无法访问
        // 防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/kafka_monitor");
        // maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    // 配置自定义的权限登录器
    @Bean
    public AuthRealm authRealm(@Qualifier("credentialsMatcher") final CredentialsMatcher matcher,
                               final SysAdminService sysAdminService) {
        final AuthRealm authRealm = new AuthRealm(sysAdminService);
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    // 配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(final SysPageService sysPageService) {
        return new CredentialsMatcher(sysPageService);
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}