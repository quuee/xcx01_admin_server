package com.xxx.xcx01.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.xcx01.support.util.PageResult;
import com.xxx.xcx01.web.entity.front.UserEntity;
import com.xxx.xcx01.web.params.XCXUserParam;
import com.xxx.xcx01.web.service.XCXUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XCXUserController {

    @Autowired
    private XCXUserService xcxUserService;
    @GetMapping("xcxUser/pageList")
    public PageResult<UserEntity> xcxUserPageList(XCXUserParam param){

        Page<UserEntity> userEntityPage = new Page<>(param.getPageNo(), param.getPageSize());
        LambdaQueryWrapper<UserEntity> userEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userEntityLambdaQueryWrapper.like(!ObjectUtils.isEmpty(param.getNickname()),UserEntity::getNickName,param.getNickname());
        Page<UserEntity> page = xcxUserService.page(userEntityPage, userEntityLambdaQueryWrapper);

        return new PageResult<>(page.getRecords(), page.getTotal());
    }
}
