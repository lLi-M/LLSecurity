package ll.security.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/23
  Time: 18:28
*/
@Component
public class IBuildCollectMatcher implements BuildCollectMatcher {

    @Autowired
    private Set<NeedCollectAntMatcher> needCollectAntMatchers;

    @Override
    public void buildCollect(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry builder) {
        needCollectAntMatchers.forEach(e -> e.buildAuthorizeRequests(builder));
        builder.anyRequest().authenticated();
    }
}
