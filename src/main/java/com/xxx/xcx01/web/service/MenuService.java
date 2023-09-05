package com.xxx.xcx01.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.xcx01.web.entity.admin.MenuEntity;

import java.util.List;

public interface MenuService extends IService<MenuEntity> {
    List<MenuEntity> treeMenu(Long userId);
}
