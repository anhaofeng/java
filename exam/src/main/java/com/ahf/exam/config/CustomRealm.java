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

import java.io.UnsupportedEncodingException;
import java.util.Set;

@Component
public class CustomRealm extends AuthorizingRealm {


    private IStuService stuService;
    @Autowired
    public CustomRealm(IStuService stuService) {
        this.stuService = stuService;
    }
    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
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
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getClaim(token,SecurityConsts.ACCOUNT);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        // 从数据库获取对应用户名密码的用户
        String password = stuService.findPwdByUname(username);
        if (password == null) {
            throw new AccountException("用户名不正确");
        }
        try {
            if (!JwtUtil.verify(token)) {
                throw new AuthenticationException("Username or password error");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
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
