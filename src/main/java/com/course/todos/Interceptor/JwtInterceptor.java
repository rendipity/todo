package com.course.todos.Interceptor;

import com.course.todos.Util.JWTUtil;
import com.course.todos.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("Authorization");
        User user=JWTUtil.verify(token);
        if (user==null) {
            doResponse(response,"身份验证已过期,请重新登录!");
            return false;
        }
        request.setAttribute("user",user);
        return true;
    }
    private void doResponse(HttpServletResponse response ,String message) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        PrintWriter writer=response.getWriter();
        writer.write(message);
        writer.close();
    }
}
