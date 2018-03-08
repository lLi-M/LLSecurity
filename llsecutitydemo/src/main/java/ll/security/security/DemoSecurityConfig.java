package ll.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/20
  Time: 21:52
*/
@Configuration
public class DemoSecurityConfig {

    @Bean
    public PasswordEncoder bcryptpasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
