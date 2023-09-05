package com.xxx.xcx01.web.controller;

import com.xxx.xcx01.support.util.Result;
import com.xxx.xcx01.web.entity.front.CategoryEntity;
import com.xxx.xcx01.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "listA",method = RequestMethod.GET)
    public Result<List<CategoryEntity>> listA(){

        List<CategoryEntity> list = categoryService.list();
        return Result.ok(list);
    }
}
