package com.course.todos.DAO;

import com.course.todos.entity.TodoItem;
import com.course.todos.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TodoItemMapper {

    List<TodoItem> queryAllItemByUserId(@Param("userId") String userId);

    List<TodoItem> queryWeekItemByUserId(@Param("userId") String userId);

    List<TodoItem> queryDayItemByUserId(@Param("userId") String userId);

    TodoItem queryItemById(@Param("id") String id);

    int deleteItemById(@Param("id") String id);

    int updateItem(TodoItem todoItem);

    int insertItem(TodoItem todoItem);

    User queryPasswordByUsername(@Param("userName") String userName);

    int insertUser(User user);

}
