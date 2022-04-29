package com.course.todos;

import com.course.todos.DAO.TodoItemMapper;
import com.course.todos.Util.JWTUtil;
import com.course.todos.entity.TodoItem;
import com.course.todos.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest("com.course.todos.TodosApplication")
class TodosApplicationTests {

    @Autowired
    TodoItemMapper todoItemMapper;

    @Autowired
    RedisTemplate redisTemplate;

}

