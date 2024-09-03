package com.dut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.dut.commom.Code;
import com.dut.commom.R;
import com.dut.entity.User;
import com.dut.service.UserService;
import com.dut.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/lg")
public class LoginController {
    @Autowired
    UserService userService;
    @Value("${jwt-secret-key}")
    String jwtSecretKey;

    @PostMapping("/login")
    public R<String> login(@RequestBody User user, HttpSession session, HttpServletResponse response){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, user.getAccount());
        User one = userService.getOne(queryWrapper);
        if(one == null) return R.error(Code.NO_SUCH_USER, "用户未找到，请注册一个新账户");
        if(!one.getPassword().equals(user.getPassword())) return R.error(Code.PASSWORD_INCORRECT, "密码错误");

        // 登陆成功 会话处理
        session.setAttribute("userId", one.getId());   //保存id
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", one.getId());
        String token = JwtUtil.generateJwt(claims, jwtSecretKey);
        Cookie cookie = new Cookie("UserToken", token);
        cookie.setPath("/");    // 设置cookie的作用路径，“/”表示该域下所有请求都会携带这个cookie
        cookie.setMaxAge(3600 * 24 * 365); //设置cookie有效时长，否则默认是会话cookie，浏览器关闭后会自动删除
        response.addCookie(cookie);

        return R.success("登陆成功");
    }

    @GetMapping("/logout")
    public R<String> logout(){
        return null;
    }

}
