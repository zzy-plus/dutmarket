package com.dut.commom;

import com.alibaba.fastjson.JSON;
import com.dut.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String servletPath = request.getServletPath();
        System.out.println(servletPath);
        response.setCharacterEncoding("UTF-8");
        if(servletPath.equals("/goods")){
            response.getWriter().write(JSON.toJSONString(R.error(400, "需要登陆")));
            return false;
        }

        return true;
    }
}
