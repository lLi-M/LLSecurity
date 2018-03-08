package ll.security.exception.controller;

import ll.security.exception.UserNotFind;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 21:45
*/
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFind.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> usernotfindException(UserNotFind userNotFind){
        Map<String,Object> map=new HashMap<>();
        map.put("id",userNotFind.getId());
        map.put("msg",userNotFind.getMsg());
        return map;
    }
}
