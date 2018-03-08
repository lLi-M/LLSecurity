package ll.security.authorization.config;

import ll.security.formProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/23
  Time: 18:25
*/
@Component
public class CommonMatcher implements NeedCollectAntMatcher {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void buildAuthorizeRequests(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry builder) {
        builder.antMatchers("/login/authentication",
                securityProperties.getBrowser().getLoginPage(),
                "/login/vaildcode","/xx/x",
                securityProperties.getBrowser().getLoginPage())//匹配到哪些路径
                .permitAll(); //可以允许所有请求
    }
}
