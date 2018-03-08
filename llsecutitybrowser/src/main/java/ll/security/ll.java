package ll.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/20
  Time: 16:07
*/
@Configuration
public class ll {
    @Bean
    public RedirectStrategy MyredirectStrategy() {
        return new lll();
    }

}
