package ll.security;

import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/21
  Time: 12:00
*/
@Component
public class Wirte {

    public String write(){
        System.out.println("success autowire");
        return "success";
    }
}
