package com.course.todos.DAO;

import com.course.todos.entity.TodoItem;
import com.course.todos.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TodoItemMapper {

    List<TodoItem> queryAllItemByUserId(@Param("userId") int userId);

    TodoItem queryItemById(@Param("id") int id);

    int deleteItemById(@Param("id") int id);

    int updateItem(TodoItem todoItem);

    int insertItem(TodoItem todoItem);

    User queryPasswordByUsername(@Param("userName") String userName);

    int insertUser(User user);

}
