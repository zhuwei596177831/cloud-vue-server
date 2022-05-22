package com.example.shiroAuthencation.realm;

import com.example.core.vo.system.MenuVo;
import com.example.core.vo.system.RoleVo;
import com.example.core.vo.system.UserVo;
import com.example.shiroAuthencation.openfeign.MenuFeign;
import com.example.shiroAuthencation.openfeign.RoleFeign;
import com.example.shiroAuthencation.openfeign.UserFeign;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2021-05-19 10:04:36
 * @description 用户名/密码登录认证源
 */
public class UserNamePasswordRealm extends AuthorizingRealm {

    @Autowired(required = false)
    private UserFeign userFeign;
    @Autowired(required = false)
    private RoleFeign roleFeign;
    @Autowired(required = false)
    private MenuFeign menuFeign;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        UserVo userVo = userFeign.findByLoginName(username);
        if (userVo == null || userVo.getId() == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(userVo, userVo.getPassword(), ByteSource.Util.bytes(username), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserVo userVo = (UserVo) principals.getPrimaryPrincipal();
        Set<RoleVo> roleVos = roleFeign.findRolesByUserId(userVo.getId());
        Set<String> roleCodes = roleVos.stream().map(RoleVo::getCode).collect(Collectors.toSet());
        Set<MenuVo> menus = menuFeign.findMenusByUserId(userVo.getId());
        Set<String> menuCodes = menus.stream().map(MenuVo::getCode).collect(Collectors.toSet());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roleCodes);
        simpleAuthorizationInfo.setStringPermissions(menuCodes);
        return simpleAuthorizationInfo;
    }
}
