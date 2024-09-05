package com.dut.commom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class})
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception e){
        if(e.getMessage().contains("Duplicate entry")){
            return R.error( Code.USER_ALREADY_EXISTS, "用户名已存在");
        }else if(e.getMessage().contains("doesn't have a default value")){
            log.error(e.getMessage());
            return R.error( Code.SQL_FIELD_ERROR, "参数有误");
        }
        log.error(e.getMessage());
        return R.error(Code.UNKNOW_ERROR, e.getMessage());
    }

}
