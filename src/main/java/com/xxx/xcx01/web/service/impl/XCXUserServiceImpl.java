package com.xxx.xcx01.web.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.xcx01.web.entity.front.UserEntity;
import com.xxx.xcx01.web.mapper.front.XCXUserMapper;
import com.xxx.xcx01.web.service.XCXUserService;
import org.springframework.stereotype.Service;

@Service
@DS("xcx01")
public class XCXUserServiceImpl extends ServiceImpl<XCXUserMapper, UserEntity> implements XCXUserService {
}
