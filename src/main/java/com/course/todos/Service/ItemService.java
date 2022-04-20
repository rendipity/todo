package com.course.todos.Service;

import com.course.todos.DAO.TodoItemMapper;
import com.course.todos.entity.TodoItem;
import com.course.todos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    TodoItemMapper todoItemMapper;

    public List<TodoItem> getAllItem(User user){
        return todoItemMapper.queryAllItemByUserId(user.getId());
    }
    public boolean insertItem(User user,TodoItem todoItem){
        todoItem.setUserId(user.getId());
        return todoItemMapper.insertItem(todoItem)==1;
    }

    public boolean updateItem(User user,TodoItem todoItem){
        if (!verifyAuthority(user.getId(),todoItem.getId()))
            return false;
        return todoItemMapper.updateItem(todoItem)==1;
    }
    public boolean deleteItem(User user,int id){
        if (!verifyAuthority(user.getId(),id))
            return false;
        return todoItemMapper.deleteItemById(id)==1;
    }
    private boolean verifyAuthority(int userId,int itemId){
        TodoItem item=todoItemMapper.queryItemById(itemId);
        if (item==null||item.getUserId()!=userId)
            return false;
        return true;
    }
}
