package com.dut.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dut.commom.Code;
import com.dut.commom.R;
import com.dut.entity.User;
import com.dut.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public R<String> addUser(@RequestBody User user){
        userService.save(user);
        return R.success("注册成功");
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping
    public R<User> getOneUserById(Long id){
        User user = userService.getById(id);
        if(user != null) return R.success(user);
        else return R.error(Code.USER_NOT_FOUND, "用户不存在");
    }

}
