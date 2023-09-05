package com.xxx.xcx01.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.xcx01.web.entity.admin.MenuEntity;
import com.xxx.xcx01.web.mapper.admin.MenuMapper;
import com.xxx.xcx01.web.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Override
    public List<MenuEntity> treeMenu(Long userId) {
        List<MenuEntity> menuEntities = baseMapper.selectMenuByUserId(userId);

        Map<Long, List<MenuEntity>> groupMap = menuEntities.stream().collect(Collectors.groupingBy(MenuEntity::getPid));

        List<MenuEntity> topMenuList = groupMap.get(0L);
        if (topMenuList == null || topMenuList.isEmpty()) {
            return topMenuList;
        }
        for (MenuEntity p : topMenuList) {
            List<MenuEntity> sub = groupMap.get(p.getId());
            if(sub!=null){
                List<MenuEntity> collect = sub.stream().sorted(Comparator.comparing(MenuEntity::getSort)).collect(Collectors.toList());
                p.getSubMenu().addAll(collect);
            }

        }
        List<MenuEntity> sorted = topMenuList.stream().sorted(Comparator.comparing(MenuEntity::getSort)).collect(Collectors.toList());
        return sorted;

    }
}
