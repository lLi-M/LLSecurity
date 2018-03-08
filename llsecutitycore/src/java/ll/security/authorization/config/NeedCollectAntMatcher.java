package ll.security.authorization.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/23
  Time: 18:17
*/
public interface NeedCollectAntMatcher {
    void buildAuthorizeRequests(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry builder);
}
