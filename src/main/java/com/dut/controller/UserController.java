package com.dut.controller;

import com.dut.commom.Code;
import com.dut.commom.R;
import com.dut.entity.User;
import com.dut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增用户
     * @param user 用户信息
     * @param code  验证码
     * @return
     */
    @PostMapping("/register")
    public R<String> addUser(@RequestBody User user, @RequestParam String code){
        // 检查验证码
        String realCode = (String)redisTemplate.opsForValue().get(user.getEmail());
        if(!code.equalsIgnoreCase(realCode)) return R.error(Code.CAPTCHA_ERROR, "验证码有误");
        // 检查密码
        if(user.getPassword()==null || user.getPassword().length()<8)
            return R.error(Code.PWD_TOO_SHORT, "密码太短");
        // 新建用户
        userService.save(user);
        redisTemplate.delete(user.getEmail());
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
        if(user != null){
            user.setPassword("");   // 敏感字段处理
            user.setEmail("");
            return R.success(user);
        }
        else return R.error(Code.USER_NOT_FOUND, "用户不存在");
    }

}
