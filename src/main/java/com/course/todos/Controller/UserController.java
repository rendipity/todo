package com.course.todos.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.course.todos.Service.UserService;
import com.course.todos.Util.JWTUtil;
import com.course.todos.entity.Result.ResponseResult;
import com.course.todos.entity.User;
import com.course.todos.entity.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("user/login")
    public ResponseResult<String> login(@RequestBody User user){
        System.out.println(user);
        if (user.getUsername()==null||user.getPassword()==null)
            return ResponseResult.fail400("用户名和密码不能为空");
        if(!userService.authentication(user)){
            return ResponseResult.fail400("用户名或密码不正确");
        }
        else
            return ResponseResult.success(JWTUtil.getToken(user));
    }
    @GetMapping("user/verifyCode")
    public ResponseResult<String> verifyCode(@RequestParam(required = false) String phone){
        return userService.sendVerifyCode(phone);
    }
    @PostMapping("user/signup")
    public ResponseResult<String> signup(@RequestBody UserParam user){
        System.out.println(user);
        if (user==null||user.getUsername()==null||user.getPassword()==null)
            return ResponseResult.fail("用户名或密码不能为空");
        else if (user.getVerifyCode()==null||user.getPhone()==null)
            return ResponseResult.fail("信息不完整");
        return userService.signup(user);
    }
}
