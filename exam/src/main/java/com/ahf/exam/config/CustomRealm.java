package com.ahf.exam.config;

import com.ahf.exam.model.Role;
import com.ahf.exam.service.IStuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomRealm extends AuthorizingRealm {


    private IStuService stuService;
    @Autowired
    public CustomRealm(IStuService stuService) {
        this.stuService = stuService;
    }

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String token = (String)authenticationToken.getPrincipal();
        String account  = JwtUtil.getClaim(token,SecurityConsts.ACCOUNT);
        if (account == null) {
            throw new AuthenticationException("token invalid");
        }
        // 从数据库获取对应用户名密码的用户
        String password = stuService.findPwdByUname(token.getUsername());
        if (password == null) {
            throw new AccountException("用户名不正确");
        }
        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        if (JwtUtil.verify(token) && cacheClient.exists(refreshTokenCacheKey)) {
            String currentTimeMillisRedis = cacheClient.get(refreshTokenCacheKey);
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "shiroRealm");
            }
        }
        throw new AuthenticationException("Token expired or incorrect.");
//        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        Set<Role> roles = stuService.findUserRoles(username);
        Set<String> roleString=null;
        for (Role role:roles) {
            roleString.add(role.getRes_name());
        }
//        Set<String> set = new HashSet<>();
//        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
//        set.add(role);
//        //设置该用户拥有的角色
        info.setRoles(roleString);
        return info;
    }
}
