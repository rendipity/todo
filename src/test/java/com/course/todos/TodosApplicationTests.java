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
    @Test
    public void todosQueryAll() {
        List<TodoItem> todoItems = todoItemMapper.queryAllItemByUserId(10);
        for(TodoItem t:todoItems){
            System.out.println(t);
        }
    }

    @Test
    public void todosQueryOne() {
        TodoItem todoItem=todoItemMapper.queryItemById(22);
        System.out.println(todoItem);
    }

    @Test
    public void deleteItemById() {
        int result=todoItemMapper.deleteItemById(21);
        System.out.println(result);
    }

    @Test
    public void updateItem () {
        int result=todoItemMapper.updateItem(new TodoItem(1,null,"0",null,10));
        System.out.println(result);
    }

    @Test
    public void insertItem () {
        int result=todoItemMapper.insertItem(new TodoItem(null,"洗衣服","1","2",10));
        System.out.println(result);
    }

    @Test
    public void queryPassword () {
        User user=todoItemMapper.queryPasswordByUsername("admin");
        System.out.println(user);
    }
    @Test
    public void redisTemplateTest(){
        redisTemplate.opsForValue().set("key","value",1, TimeUnit.MINUTES);
    }
}

