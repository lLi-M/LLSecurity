package ll.security.sms.重构;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/15
  Time: 0:52
*/
@Component
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    //多个接口实现类通过形参名可以进行选择也可以通过 @Qualifier
    private SocialUserDetailsService smsUserService;

    @Override
    public void configure(HttpSecurity http) {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
       smsAuthenticationProvider.setSmsUserService(smsUserService);
        http.authenticationProvider(smsAuthenticationProvider)
                //短信提交的路径不用被permit因为打回请求的过滤器为最后一个，这个提前被拦截被认证了
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
