package com.course.todos.Controller;

import com.course.todos.Service.ItemService;
import com.course.todos.entity.Result.ResponseResult;
import com.course.todos.entity.TodoItem;
import com.course.todos.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;
    @GetMapping("/things")
    public ResponseResult <List<TodoItem>> queryThings(@RequestAttribute User user){
        System.out.println("queryThings执行了");
        if (user==null)
            return ResponseResult.requestError();
        return ResponseResult.success(itemService.getAllItem(user));
    }
    @PostMapping("/things")
    public ResponseResult<String> addThings(@RequestAttribute User user,@RequestBody() TodoItem todoItem){
        System.out.println("addThings执行了");
        return itemService.insertItem(user,todoItem)?ResponseResult.success():ResponseResult.fail();
    }
    @PutMapping("/things")
    public ResponseResult<String> updateThings(@RequestAttribute User user, @RequestBody TodoItem todoItem){
        System.out.println("updateThings执行了");
        todoItem.setUserId(user.getId());
        return itemService.updateItem(user,todoItem)?ResponseResult.success():ResponseResult.fail();
    }
    @DeleteMapping("/things/{id}")
    public  ResponseResult<String> deleteThings(@RequestAttribute User user,@PathVariable("id") int id){
        System.out.println("deleteThings执行了");
        return itemService.deleteItem(user,id)?ResponseResult.success():ResponseResult.fail();
    }
}
