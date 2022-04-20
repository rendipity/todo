package com.course.todos.Service;

import com.course.todos.DAO.TodoItemMapper;
import com.course.todos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    TodoItemMapper todoItemMapper;

    public Boolean authentication(User user){
        User user1=todoItemMapper.queryPasswordByUsername(user.getUsername());
        if (user1.getPassword()!=null&&user1.getPassword().equals(user.getPassword())){
            user.setId(user1.getId());
            return true;
        }
        else
            return false;
    }
}
