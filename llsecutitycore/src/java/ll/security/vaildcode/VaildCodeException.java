package ll.security.vaildcode;

import org.springframework.security.core.AuthenticationException;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/13
  Time: 12:54
*/
public class VaildCodeException extends AuthenticationException {


    public VaildCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public VaildCodeException(String msg) {
        super(msg);
    }
}
