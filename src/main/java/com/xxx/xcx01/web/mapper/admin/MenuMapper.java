package com.xxx.xcx01.web.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.xcx01.web.entity.admin.MenuEntity;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuEntity> {

    List<MenuEntity> selectMenuByAdminId(Long adminId);
}
