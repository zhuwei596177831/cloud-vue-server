package com.example.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.core.entity.PageInfo;
import com.example.core.entity.Result;
import com.example.core.entity.ResultCode;
import com.example.core.util.ConstantsHolder;
import com.example.core.util.PasswordHelper;
import com.example.system.entity.Role;
import com.example.system.entity.User;
import com.example.system.entity.UserRole;
import com.example.system.entity.req.UserReq;
import com.example.system.mapper.RoleMapper;
import com.example.system.mapper.UserMapper;
import com.example.system.mapper.UserRoleMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
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
    public Result addUser(User user) {
        Result result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }
        user.setPassword(PasswordHelper.md5(user.getLoginName(), ConstantsHolder.DEFAULT_PASSWORD));
        userMapper.insert(user);
        addUserRole(user.getRoleIds(), user.getId(), user.getInputUserId());
        return Result.ok();
    }

    /**
     * 修改用户
     *
     * @param user:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:34
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result updateUser(User user) {
        Result result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }
        userMapper.updateById(user);
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        addUserRole(user.getRoleIds(), user.getId(), user.getUpdateUserId());
        return Result.ok();
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

    private Result validate(User user) {
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getLoginName, user.getLoginName()));
        if (exist != null && !exist.getId().equals(user.getId())) {
            return ResultCode.COMMON.getResult("登录名已存在");
        }
        exist = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, user.getPhone()));
        if (exist != null && !exist.getId().equals(user.getId())) {
            return ResultCode.COMMON.getResult("手机号已存在");
        }
        return Result.ok();
    }


    /**
     * 删除用户
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:51
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result deleteUserById(Long userId) {
        userMapper.deleteById(userId);
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        return Result.ok();
    }

    /**
     * 重置密码
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 18:54
     **/
    @Transactional(rollbackFor = Exception.class)
    public Result resetPassword(Long userId) {
        User exist = userMapper.selectById(userId);
        if (exist == null) {
            return ResultCode.COMMON.getResult("用户不存在");
        }
        User user = new User();
        user.setId(userId);
        user.setPassword(PasswordHelper.md5(exist.getLoginName(), ConstantsHolder.DEFAULT_PASSWORD));
        userMapper.updateById(user);
        return null;
    }
}
