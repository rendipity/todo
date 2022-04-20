package com.course.todos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {

    @NotEmpty(message = "id could not be empty")
    private Integer id;
    @NotBlank(message = "username could not be empty")
    private String username;
    @NotBlank(message = "password could not be empty")
    private String password;
}
