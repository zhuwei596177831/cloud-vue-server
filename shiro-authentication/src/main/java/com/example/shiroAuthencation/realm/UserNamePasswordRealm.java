package com.example.shiroAuthencation.realm;

import com.example.core.util.ConstantsHolder;
import com.example.system.entity.Menu;
import com.example.system.entity.Role;
import com.example.system.entity.User;
import com.example.system.openFeign.MenuFeign;
import com.example.system.openFeign.RoleFeign;
import com.example.system.openFeign.UserFeign;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2021-05-19 10:04:36
 * @description
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
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        CredentialsMatcher cm = getCredentialsMatcher();
        if (cm != null) {
            if (!cm.doCredentialsMatch(token, info)) {
                //not successful - throw an exception to indicate this:
                throw new IncorrectCredentialsException(ConstantsHolder.USER_LOGIN_ERROR);
            }
        } else {
            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify " +
                    "credentials during authentication.  If you do not wish for credentials to be examined, you " +
                    "can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userFeign.getUserByLoginName(username);
        if (user == null || user.getId() == null) {
            throw new AuthenticationException(ConstantsHolder.USER_LOGIN_ERROR);
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(username), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        Set<Role> roles = roleFeign.findRolesByUserId(user.getId());
        Set<String> roleCodes = roles.stream().map(Role::getCode).collect(Collectors.toSet());
        Set<Menu> menus = menuFeign.findMenusByUserId(user.getId());
        Set<String> menuCodes = menus.stream().map(Menu::getCode).collect(Collectors.toSet());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roleCodes);
        simpleAuthorizationInfo.setStringPermissions(menuCodes);
        return simpleAuthorizationInfo;
    }
}
