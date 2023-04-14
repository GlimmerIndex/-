package org.glimmer.advice;

import org.glimmer.domain.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = IOException.class)
    public ResponseResult ioExceptionHandler() {
        return new ResponseResult(5002,"IO错误");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler() {
        return new ResponseResult(999,"未知异常");
    }
}
