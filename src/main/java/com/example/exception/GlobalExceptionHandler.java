package com.example.exception;

import com.example.utils.Result;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        return Result.error("未知异常");
    }

    @ExceptionHandler(IOException.class)
    public Result ioExceptionHandler(IOException e){
        return Result.error("读取数据异常");
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public Result servletRequestBindingExceptionHandler(ServletRequestBindingException e){
        return Result.error("请求参数异常");
    }

    @ExceptionHandler(TypeMismatchException.class)
    public Result typeMismatchExceptionHandler(TypeMismatchException e){
        return Result.error("参数类型错误");
    }
}
