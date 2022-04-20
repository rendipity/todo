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

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
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
    @GetMapping("/verifyCode")
    public ResponseResult<String> verifyCode(@RequestParam(required = false) String phone){
        if(phone.equals(""))
            return ResponseResult.fail("手机号不能为空");
        return userService.sendVerifyCode(phone);
    }
    @PostMapping("/signup")
    public ResponseResult<String> signup(@RequestBody User user){
        System.out.println(user);
        if (user==null||user.getUsername()==null||user.getPassword()==null||user.getPhone()==null)
            return ResponseResult.fail("用户名或密码不能为空");
        return userService.signup(user)?ResponseResult.success("注册成功"):ResponseResult.success("注册失败，请重试");
    }
    @RequestMapping("/validationTest")
    public ResponseResult <String> validationTest(@Valid @RequestBody UserParam userParam, BindingResult bindingResult){
        System.out.println(userParam);
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error: errors){
                System.out.println(error);
            }
            return ResponseResult.fail("invalid parameter");
        }
        return ResponseResult.success();
    }


}
