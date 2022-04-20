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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ResponseResult<String> login(@RequestBody User user){
        if (user.getUsername()==null||user.getPassword()==null)
            return ResponseResult.authorizeError();
        if(!userService.authentication(user)){
            return ResponseResult.authorizeError();
        }
        else
            return ResponseResult.success(JWTUtil.getToken(user));
    }
    @RequestMapping("/validationTest")
    public ResponseResult <String> validationTest(@Valid @RequestBody UserParam userParam, BindingResult bindingResult){
        //System.out.println(bindingResult);
        System.out.println(userParam);
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error: errors){
                System.out.println(error);
            }
            return ResponseResult.requestError();
        }
        return ResponseResult.success();
    }

}
