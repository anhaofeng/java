package com.ahf.exam.config;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Filter;

@Configuration
public class ShiroConfig {
    @Bean
    LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return  new LifecycleBeanPostProcessor();
    }
    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();//此处为AccessToken
    }
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    static DefaultAdvisorAutoProxyCreator getLifecycleBeanPostProcessor(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return  defaultAdvisorAutoProxyCreator;
    }
    @Bean
    public  ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
     ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
     shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
     shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url;
     shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", jwtFilter());
        filterMap.put("logout", new SystemLogoutFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // 设置拦截器
        Map<String,String> fileChaineDefinitionMap= new LinkedHashMap<>();
        //游客，开发权限
        fileChaineDefinitionMap.put("/login","anon");
        fileChaineDefinitionMap.put("/createCode","anon");
        fileChaineDefinitionMap.put("/validate","anon");
        fileChaineDefinitionMap.put("/static/**","anon");
        fileChaineDefinitionMap.put("/stuLogin","anon");

        //用户，需要角色权限 “user”
//        fileChaineDefinitionMap.put("/user/**","roles[user]");
        //管理员，需要角色权限 “admin”
//        fileChaineDefinitionMap.put("/admin/**","roles[admin]");
        //开放登陆接口
//        fileChaineDefinitionMap.put("/authorization/**","anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        fileChaineDefinitionMap.put("/**","jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fileChaineDefinitionMap);
        System.out.println("shior拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
    /**
     * 注入 securityManager
     */
    @Bean
    public  SecurityManager securityManager(CustomRealm customRealm,ShiroCacheManager shiroCacheManager){
        DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(customRealm);
        DefaultSubjectDAO subjectDAO= new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator =new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setCacheManager(shiroCacheManager);
        return securityManager;

    }
    @Bean
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor =new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return  advisor;
    }



}
