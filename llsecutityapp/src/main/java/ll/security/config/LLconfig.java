package ll.security.config;/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/21
  Time: 16:22
*/

import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AuthorizationServerEndpointsConfiguration.class, AuthorizationServerSecurityConfiguration.class})
public @interface LLconfig {

}
