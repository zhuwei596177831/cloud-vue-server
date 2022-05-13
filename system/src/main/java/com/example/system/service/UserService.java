package com.example.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.core.entity.Json;
import com.example.core.entity.PageInfo;
import com.example.core.util.Constants;
import com.example.core.util.PasswordHelper;
import com.example.coreweb.exception.ApplicationException;
import com.example.system.entity.Role;
import com.example.system.entity.User;
import com.example.system.entity.UserRole;
import com.example.system.entity.req.UserReq;
import com.example.system.mapper.RoleMapper;
import com.example.system.mapper.UserMapper;
import com.example.system.mapper.UserRoleMapper;
import com.example.system.responsecode.UserResponseCode;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
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

    /**
     * @param username:
     * @author: 朱伟伟
     * @date: 2021-05-22 18:38
     * @description: 根据用户名查询用户
     **/
    public User getUserByLoginName(String username) {
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
        user.setPassword(PasswordHelper.md5(user.getLoginName(), Constants.DEFAULT_PASSWORD));
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
        user.setPassword(PasswordHelper.md5(exist.getLoginName(), Constants.DEFAULT_PASSWORD));
        userMapper.updateById(user);
        return Json.success();
    }
}
