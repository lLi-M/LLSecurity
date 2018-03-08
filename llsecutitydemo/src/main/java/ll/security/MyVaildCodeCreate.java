package ll.security;

import ll.security.vaildcode.CreateVaildCode;
import ll.security.vaildcode.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/20
  Time: 19:28
*/
@Slf4j
@Component("MyVaildCodeCreate")
public class MyVaildCodeCreate implements CreateVaildCode{

    @Override
    public ImageCode createVaildCode(HttpServletRequest request) {
        log.info("装配了该类");
        return null;
    }
}
