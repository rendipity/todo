package com.course.todos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoItem {
    private Integer id;
    private String things;
    String state;
    String priority;
    int userId;
}
