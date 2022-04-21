package com.course.todos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {
    private String username;
    private String password;
    private String phone;
    private Integer verifyCode;
}
