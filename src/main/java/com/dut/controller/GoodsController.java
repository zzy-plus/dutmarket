package com.dut.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dut.commom.R;
import com.dut.entity.Goods;
import com.dut.entity.User;
import com.dut.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @PostMapping
    public R<String> addGoods(Goods goods){
        goodsService.save(goods);
        return R.success("success");
    }

    @GetMapping
    public R<Page<Goods>> getGoodsByPage(Goods goods,
                                         @RequestParam(defaultValue = "1") Integer curPage,
                                         @RequestParam(defaultValue = "20") Integer pageSize){
        Page<Goods> pageInfo = new Page<>(curPage, pageSize);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(goods.getCategory()!=null, Goods::getCategory, goods.getCategory())
                    .and(qw-> qw
                            .like(goods.getName()!=null, Goods::getName, goods.getName())
                            .or()
                            .like(goods.getName()!=null, Goods::getDescription, goods.getName())
                    );


        Page<Goods> page = goodsService.page(pageInfo, queryWrapper);

        return R.success(page);
    }


}
