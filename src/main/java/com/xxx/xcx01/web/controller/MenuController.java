package com.xxx.xcx01.web.controller;

import com.xxx.xcx01.support.aspect.SysLog;
import com.xxx.xcx01.support.util.Result;
import com.xxx.xcx01.web.entity.admin.MenuEntity;
import com.xxx.xcx01.web.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @SysLog(operation = "查询菜单")
    @RequestMapping(value = "tree",method = RequestMethod.GET)
    public Result<List<MenuEntity>> treeMenu(@RequestParam Long adminId){
        List<MenuEntity> tree = menuService.treeMenu(adminId);
        return Result.ok(tree);
    }
}
