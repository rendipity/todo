package com.course.todos.entity.Result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseResult<T> {
    private String status;

    private String message;

    private T data;

    public static <T> ResponseResult<T> success(){
        return success(null);
    }
    public static <T> ResponseResult<T> success(T data){
        return ResponseResult.<T>builder()
                .data(data)
                .message(ResultStatus.SUCCESS.getDescription())
                .status(ResultStatus.SUCCESS.getStatus()).build();
    }

    public static <T> ResponseResult<T> fail(T data){
        return ResponseResult.<T>builder()
                .data(data)
                .message(ResultStatus.FAIL.getDescription())
                .status(ResultStatus.FAIL.getStatus())
                .build();
    }
    public static <T> ResponseResult<T> fail(){
        return fail(null);
    }

    public static <T> ResponseResult<T> fail400(T data){
        return ResponseResult.<T>builder()
                .data(data)
                .message(ResultStatus.HTTP_STATUS_400.getDescription())
                .status(ResultStatus.HTTP_STATUS_400.getStatus())
                .build();
    }
    public static <T> ResponseResult<T> fail400(){
        return fail400(null);
    }

    public static <T> ResponseResult<T> fail401(T data){
        return ResponseResult.<T>builder()
                .data(data)
                .message(ResultStatus.HTTP_STATUS_401.getDescription())
                .status(ResultStatus.HTTP_STATUS_401.getStatus())
                .build();
    }
    public static <T> ResponseResult<T> fail401(){
        return fail401(null);
    }
}
