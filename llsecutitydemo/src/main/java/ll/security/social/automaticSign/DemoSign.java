package ll.security.social.automaticSign;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 21:54
*/
@Component
public class DemoSign implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
       String userId= connection.getKey().getProviderUserId();
       return userId;
    }
}
