package ll.security.config;

import ll.security.authorization.config.IBuildCollectMatcher;
import ll.security.formProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;


/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/21
  Time: 0:05
*/
@Configuration
@EnableResourceServer
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {
    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SecuritySuccessHanler securitySuccessHanler;
    @Autowired
    private SecurityFailureHandler securityFailureHandler;

    @Autowired
    private IBuildCollectMatcher iBuildCollectMatcher;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()   //这个是基于表单的配置,这个是匹配是否带有登录信息
                //http.httpBasic() //这个是基于SpringSecurity基础的默认配置
                .loginPage("/login/authentication")//自定义登录处理逻辑页面
                .loginProcessingUrl("/authentication/form") //告诉filter处理这个路径的登录请求
                .successHandler(securitySuccessHanler)  //处理成功登录的handler
                .failureHandler(securityFailureHandler)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login/authentication",
//                        securityProperties.getBrowser().getLoginPage(),
//                        "/login/vaildcode","/xx/x",
//                        securityProperties.getBrowser().getLoginPage())//匹配到哪些路径
//                .permitAll() //可以允许所有请求
                //这里的Role，在UserDetailService中药加前缀ROLE_
//                .antMatchers("/user/me").hasRole("LILEI")
//                .anyRequest()
//                .authenticated()
                .and()
                .csrf().disable();
//提高了权限管理的灵活性
        iBuildCollectMatcher.buildCollect(http.authorizeRequests());
    }
}
