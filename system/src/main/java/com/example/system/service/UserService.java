package com.example.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.api.system.entity.Menu;
import com.example.api.system.entity.Role;
import com.example.api.system.entity.User;
import com.example.api.system.entity.UserRole;
import com.example.core.entity.Json;
import com.example.core.entity.PageInfo;
import com.example.core.enums.MenuType;
import com.example.core.responsecode.ApplicationResponseCode;
import com.example.core.util.BeanUtils;
import com.example.core.util.Constants;
import com.example.core.util.PasswordUtils;
import com.example.core.vo.system.UserInfoVo;
import com.example.core.vo.system.UserProfile;
import com.example.core.entity.ShiroUser;
import com.example.coreweb.exception.ApplicationException;
import com.example.shiroAuthencation.realm.UserNamePasswordRealm;
import com.example.shiroAuthencation.sessioncache.ShiroReisCache;
import com.example.system.config.SysMonitorConfig;
import com.example.system.mapper.MenuMapper;
import com.example.system.mapper.RoleMapper;
import com.example.system.mapper.UserMapper;
import com.example.system.mapper.UserRoleMapper;
import com.example.system.req.UserReq;
import com.example.system.responseCode.PwdResponseCode;
import com.example.system.responseCode.UserResponseCode;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 朱伟伟
 * @date 2021-05-15 17:23:25
 * @description 用户service
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private SysMonitorConfig sysMonitorConfig;
    @Autowired
    private ShiroReisCache shiroReisCache;

    /**
     * @param username:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:38
     * @description: 根据用户名查询用户
     **/
    public User findByLoginName(String username) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getLoginName, username);
        return userMapper.selectOne(userLambdaQueryWrapper);
    }

    /**
     * 用户列表数据
     *
     * @param userReq:
     * @param pageInfo:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:20
     **/
    public List<User> userList(UserReq userReq, PageInfo pageInfo) {
        if (pageInfo != null) {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        List<User> userList = userMapper.userList(userReq);
        if (pageInfo != null) {
            userList.forEach(u -> {
                List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, u.getId()));
                List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                u.setRoleIds(roleIds);
                if (!roleIds.isEmpty()) {
                    List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, roleIds));
                    u.setRoleNames(StringUtils.collectionToCommaDelimitedString(roles.stream().map(Role::getName).collect(Collectors.toList())));
                }
            });
        }
        return userList;
    }

    /**
     * 添加用户
     *
     * @param user:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:30
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json addUser(User user) {
        validate(user);
        user.setPassword(PasswordUtils.md5(user.getLoginName(), Constants.DEFAULT_PASSWORD));
        userMapper.insert(user);
        addUserRole(user.getRoleIds(), user.getId(), user.getInputUserId());
        return Json.success();
    }

    /**
     * 修改用户
     *
     * @param user:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:34
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json updateUser(User user) {
        validate(user);
        userMapper.updateById(user);
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        addUserRole(user.getRoleIds(), user.getId(), user.getUpdateUserId());
        return Json.success();
    }

    private void addUserRole(List<Long> roleIds, Long userId, Long inputUserId) {
        roleIds.forEach(i -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(i);
            userRole.setInputTime(LocalDateTime.now());
            userRole.setInputUserId(inputUserId);
            userRoleMapper.insert(userRole);
        });
    }

    private void validate(User user) {
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getLoginName, user.getLoginName()));
        if (exist != null && !exist.getId().equals(user.getId())) {
            throw new ApplicationException(UserResponseCode.LOGIN_NAME_EXIST);
        }
        exist = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, user.getPhone()));
        if (exist != null && !exist.getId().equals(user.getId())) {
            throw new ApplicationException(UserResponseCode.PHONE_EXIST);
        }
    }


    /**
     * 删除用户
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:51
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json deleteUserById(Long userId) {
        userMapper.deleteById(userId);
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        return Json.success();
    }

    /**
     * 重置密码
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:54
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json resetPassword(Long userId) {
        User exist = userMapper.selectById(userId);
        if (exist == null) {
            throw new ApplicationException(UserResponseCode.USER_NOT_EXIST);
        }
        User user = new User();
        user.setId(userId);
        user.setPassword(PasswordUtils.md5(exist.getLoginName(), Constants.DEFAULT_PASSWORD));
        userMapper.updateById(user);
        return Json.success();
    }

    /**
     * 获取用户信息 用于前端vuex存储
     *
     * @param shiroUser:
     * @author: 朱伟伟
     * @date: 2022-05-24 09:41
     **/
    public UserInfoVo findUserInfo(ShiroUser shiroUser) {
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(shiroUser, userInfoVo);
        Set<Role> roles = roleMapper.findRolesByUserId(shiroUser.getId());
        List<String> roleCodes = roles.stream().map(Role::getCode).collect(Collectors.toList());
        userInfoVo.setRoleCodes(roleCodes);
        Set<Menu> menus = menuMapper.findMenusByUserId(shiroUser.getId());
        List<String> menuCodes = menus.stream().filter(menu -> MenuType.MENU_BUTTON.getValue().equals(menu.getType())).map(Menu::getCode).collect(Collectors.toList());
        userInfoVo.setMenuCodes(menuCodes);
        List<String> menuPaths = menus.stream().map(Menu::getPath).filter(StringUtils::hasText).collect(Collectors.toList());
        userInfoVo.setMenuPaths(menuPaths);
        List<String> roleNameList = roles.stream().map(Role::getName).collect(Collectors.toList());
        userInfoVo.setRoleNames(StringUtils.collectionToCommaDelimitedString(roleNameList));
        userInfoVo.setJobAddress(sysMonitorConfig.getJob());
        userInfoVo.setNacosAddress(sysMonitorConfig.getNacos());
        userInfoVo.setMonitorAddress(sysMonitorConfig.getMonitor());
        return userInfoVo;
    }

    /**
     * 更新基本资料
     *
     * @param userProfile:
     * @author: 朱伟伟
     * @date: 2022-06-06 09:23
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json updateProfile(UserProfile userProfile, ShiroUser shiroUser, HttpServletRequest request) {
        String token = request.getHeader(Constants.X_TOKEN_NAME);
        if (!StringUtils.hasText(token)) {
            token = request.getParameter(Constants.X_TOKEN_NAME);
        }
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException();
        }
        User user = userMapper.selectById(shiroUser.getId());
        if (user == null) {
            throw new ApplicationException(ApplicationResponseCode.RECORD_NOT_EXIST);
        }
        //更新redis中存储的session的用户信息
        BeanUtils.copyPropertiesIgnoreNull(userProfile, shiroUser);
        Session session = shiroReisCache.get(token);
        session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY,
                new SimplePrincipalCollection(shiroUser, UserNamePasswordRealm.REALM_NAME));
        shiroReisCache.put(token, session);
        //更新数据库
        BeanUtils.copyPropertiesIgnoreNull(userProfile, user);
        user.setUpdateUserId(shiroUser.getId());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
        return Json.success();
    }

    /**
     * 修改用户密码
     *
     * @param userProfile:
     * @param shiroUser:
     * @author: 朱伟伟
     * @date: 2022-06-06 11:38
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json updatePwd(UserProfile userProfile, ShiroUser shiroUser) {
        if (!userProfile.getNewPassword().equals(userProfile.getConfirmPassword())) {
            throw new ApplicationException(PwdResponseCode.PASSWORD_OLD_NEW_NOT_EQUALS);
        }
        String oldPassword = PasswordUtils.md5(shiroUser.getLoginName(), userProfile.getOldPassword());
        if (!oldPassword.equals(shiroUser.getPassword())) {
            throw new ApplicationException(PwdResponseCode.PASSWORD_NOT_CORRECT);
        }
        String newPassword = PasswordUtils.md5(shiroUser.getLoginName(), userProfile.getNewPassword());
        if (newPassword.equals(shiroUser.getPassword())) {
            throw new ApplicationException(PwdResponseCode.NOT_USE_OLD_PASSWORD);
        }
        User user = userMapper.selectById(shiroUser.getId());
        user.setPassword(newPassword);
        userMapper.updateById(user);
        return Json.success();
    }

    /**
     * 用户密码重置
     *
     * @param shiroUser:
     * @author: 朱伟伟
     * @date: 2022-06-06 14:47
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json resetPwd(ShiroUser shiroUser) {
        if (shiroUser.getId() == null) {
            throw new ApplicationException(UserResponseCode.USER_ID_NOT_NULL);
        }
        if (!StringUtils.hasText(shiroUser.getPassword())) {
            throw new ApplicationException(UserResponseCode.USER_PASSWORD_NOT_NULL);
        }
        User user = userMapper.selectById(shiroUser.getId());
        user.setPassword(PasswordUtils.md5(user.getLoginName(), shiroUser.getPassword()));
        userMapper.updateById(user);
        return Json.success();
    }

    /**
     * 更新用户头像
     *
     * @param url:
     * @param shiroUser:
     * @author: 朱伟伟
     * @date: 2022-06-06 15:56
     **/
    @Transactional(rollbackFor = Exception.class)
    public Json updateHeadImageUrl(String url, ShiroUser shiroUser) {
        User user = userMapper.selectById(shiroUser.getId());
        user.setHeadImageUrl(url);
        userMapper.updateById(user);
        return Json.success();
    }
}
