package com.dut.controller;

import com.dut.commom.R;
import com.dut.entity.Category;
import com.dut.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public R<List<Category>> getCategorys(){
        List<Category> list = categoryService.list();
        return R.success(list);
    }

}
