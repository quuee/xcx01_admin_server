package com.xxx.xcx01.web.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.xcx01.web.entity.front.CategoryEntity;
import com.xxx.xcx01.web.mapper.front.CategoryMapper;
import com.xxx.xcx01.web.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@DS("xcx01")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {




}
