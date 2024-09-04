package com.dut.commom;

import com.alibaba.fastjson.JSON;
import com.dut.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class CustomInterceptor implements HandlerInterceptor {

    @Value("${jwt-secret-key}")
    String jwtSecretKey;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/error")) return true;
        if(servletPath.startsWith("/lg")) return true;
        if(servletPath.equals("/user/register")) return true;

        // 检查会话
        HttpSession session = request.getSession();
        Long userId = (Long)session.getAttribute("userId");
        if(userId!=null) return true;

        // 检查Cookies
        userId = this.checkCookies(request.getCookies());
        if(userId!=null){
            session.setAttribute("userId", userId);
            return true;
        }else {
            response.getWriter().write(JSON.toJSONString(R.error(Code.NEED_LOGIN, "需要登陆")));
            log.info("CustomInterceptor: 需要登陆");
            return false;
        }
    }

    public Long checkCookies(Cookie[] cookies){
        if(cookies == null) return null;    //用户没有cookies
        String userToken = null;
        for (Cookie cookie : cookies) {     //寻找用户认证cookie
            if(cookie.getName().equals("UserToken")){
                userToken = cookie.getValue();
                break;
            }
        }
        if(userToken == null) return null;
        //JWT 验证
        Claims claims = JwtUtil.parsePayload(userToken, jwtSecretKey);
        if(claims == null) return null;     //jwt 验证失败

        return claims.get("userId", Long.class);
    }

}
