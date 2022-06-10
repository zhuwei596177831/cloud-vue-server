package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.api.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-06-14 16:45:44
 * @description 菜单mapper
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * @param menuReq:
     * @author: 朱伟伟
     * @date: 2021-06-14 17:03
     * @description: 菜单列表数据
     **/
    List<Menu> menuList(Menu menu);

    /**
     * 根据用户id查询菜单集合
     *
     * @param userId:
     * @author: 朱伟伟
     * @date: 2021-07-25 16:45
     **/
    Set<Menu> findMenusByUserId(Long userId);
}
