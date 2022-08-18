package com.example.shiroAuthencation.realm;

import com.example.api.system.entity.Menu;
import com.example.api.system.entity.Role;
import com.example.api.system.entity.User;
import com.example.api.system.openfeign.client.MenuFeign;
import com.example.api.system.openfeign.client.RoleFeign;
import com.example.api.system.openfeign.client.UserFeign;
import com.example.core.entity.GenericJson;
import com.example.core.entity.ShiroUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2021-05-19 10:04:36
 * @description 用户名/密码登录认证源
 * 说明：Realm中的Feign不可直接注入，只要有一个提前注入了，那么在执行FeignClientFactoryBean的getObject时，默认会从父容器获取
 * 唯一的feign.Client类型的Bean，此时Client在registerBeanPostProcessors阶段被实例化，导致SeataBeanPostProcessor失效，
 * 无法将父容器中的LoadBalancerFeignClient封装为SeataLoadBalancerFeignClient，最终在执行Client的execute时无法传递Xid
 * 根本原因：ShiroFilterFactoryBean是一个BeanPostProcessor
 * @see org.springframework.cloud.openfeign.FeignClientFactoryBean
 * @see com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration
 * @see org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration
 * @see org.springframework.cloud.openfeign.ribbon.HttpClientFeignLoadBalancedConfiguration
 * @see org.apache.shiro.spring.web.ShiroFilterFactoryBean
 */
public class UserNamePasswordRealm extends AuthorizingRealm {

    public static final String REALM_NAME = "UserNamePasswordRealm";

    @Autowired
    @Lazy
    private UserFeign userFeign;

    @Autowired
    @Lazy
    private RoleFeign roleFeign;

    @Autowired
    @Lazy
    private MenuFeign menuFeign;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public String getName() {
        return REALM_NAME;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String loginName = usernamePasswordToken.getUsername();
        GenericJson<User> genericJson = userFeign.findByLoginName(loginName);
        User user = genericJson.getData();
        if (user == null || user.getId() == null) {
            return null;
        }
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(user, shiroUser);
        return new SimpleAuthenticationInfo(shiroUser, shiroUser.getPassword(), ByteSource.Util.bytes(loginName), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        Set<Role> roles = roleFeign.findRolesByUserId(shiroUser.getId()).getData();
        Set<String> roleCodes = roles.stream().map(Role::getCode).collect(Collectors.toSet());
        Set<Menu> menus = menuFeign.findMenusByUserId(shiroUser.getId()).getData();
        Set<String> menuCodes = menus.stream().map(Menu::getCode).collect(Collectors.toSet());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roleCodes);
        simpleAuthorizationInfo.setStringPermissions(menuCodes);
        return simpleAuthorizationInfo;
    }
}
