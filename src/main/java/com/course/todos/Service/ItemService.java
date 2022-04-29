package com.course.todos.Service;

import com.course.todos.DAO.TodoItemMapper;
import com.course.todos.entity.Result.ResponseResult;
import com.course.todos.entity.TimeParam;
import com.course.todos.entity.TodoItem;
import com.course.todos.entity.User;
import com.course.todos.entity.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    @Autowired
    TodoItemMapper todoItemMapper;

    public List<TodoItem> getAllItem(User user,String timeType){
        List<TodoItem> items;
        if (timeType==null||timeType.equals("3"))
            items=todoItemMapper.queryAllItemByUserId(user.getId());
        else if (timeType.equals("2"))
            items=todoItemMapper.queryWeekItemByUserId(user.getId());
        else
            items=todoItemMapper.queryDayItemByUserId(user.getId());
        return items;
    }
    public boolean insertItem(User user,TodoItem todoItem){
        todoItem.setUserId(user.getId());
        return todoItemMapper.insertItem(todoItem)==1;
    }

    public ResponseResult<String> updateItem(User user,TodoItem todoItem){
        if (!verifyAuthority(user.getId(), todoItem.getId()))
            return ResponseResult.fail("权限不足");
        return todoItemMapper.updateItem(todoItem)==1?ResponseResult.success("修改成功"):ResponseResult.fail("服务端错误，修改失败");
    }
    public ResponseResult<String> deleteItem(User user, String id){
        if (!verifyAuthority(user.getId(), id))
            return ResponseResult.fail("权限不足");
        return todoItemMapper.deleteItemById(id)==1?ResponseResult.success("删除成功"):ResponseResult.fail("服务端错误，删除失败");
    }
    private boolean verifyAuthority(String userId,String itemId){
        TodoItem item=todoItemMapper.queryItemById(itemId);
        return item!=null && item.getUserId().equals(userId);
    }
}
