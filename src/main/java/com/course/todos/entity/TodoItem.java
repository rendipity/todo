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
    private String id;
    private String things;
    private String state;
    private String userId;
    private String Time;
}
