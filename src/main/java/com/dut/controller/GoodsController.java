package com.dut.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dut.commom.R;
import com.dut.entity.Goods;
import com.dut.entity.User;
import com.dut.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    /**
     * 添加商品
     * @param goods
     * @return 添加状态
     */
    @PostMapping
    public R<String> addGoods(@RequestBody Goods goods, HttpSession session){
        Long publisherId = (Long) session.getAttribute("userId");
        goods.setPublisherId(publisherId);
        System.out.println(goods);
        goodsService.save(goods);
        return R.success("success");
    }

    /**
     * 分页查询商品
     * @param goods
     * @param curPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public R<Page<Goods>> getGoodsByPage(Goods goods,
                                         @RequestParam(defaultValue = "1") Integer curPage,
                                         @RequestParam(defaultValue = "20") Integer pageSize){
        Page<Goods> pageInfo = new Page<>(curPage, pageSize);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(goods.getCategory()!=null && goods.getCategory()!=-1, Goods::getCategory, goods.getCategory());
        if(StringUtils.hasLength(goods.getName())){
            queryWrapper.and(qw -> qw
                    .like(Goods::getName, goods.getName())
                    .or()
                    .like( Goods::getDescription, goods.getName())
            );
        }

        Page<Goods> page = goodsService.page(pageInfo, queryWrapper);

        return R.success(page);
    }

    @GetMapping("/detail")
    public R<Goods> getGoodsDetail(Long id){
        Goods goods = goodsService.getById(id);
        if(goods == null){
            return R.error(Long.toString(id) + "商品不存在!");
        }
        return R.success(goods);
    }

    @DeleteMapping
    public R<String> deleteGoodById(Long id){
        goodsService.removeById(id);
        return R.success("success");
    }


}
