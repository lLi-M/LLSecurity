package ll.security;

import ll.security.formProperties.SecurityProperties;
import ll.security.session.MyexpiredSession;
import ll.security.session.MyinvalidSession;
import ll.security.sms.重构.SmsAuthenticationConfig;
import ll.security.vaildcode.VaildcodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.security.SpringSocialConfigurer;


import javax.sql.DataSource;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/11
  Time: 20:35
*/
@Configuration
public class BrowserSecurity extends WebSecurityConfigurerAdapter {

    //注册的redirect工具类
    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }

    //注册的requestCache类
    @Bean
    public RequestCache IOCrequestCache() {
        return new HttpSessionRequestCache();
    }

    @Autowired
    private DataSource dataSource;

    //进行rememberMe的userdetail类
    @Autowired
    private UserDetail userDetail;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        //告诉Spring用这个密码比对器
        return new BCryptPasswordEncoder();
    }

    //Remember-me功能实现模块提供token的DB服务
    @Bean
    public PersistentTokenRepository repository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
//        repository.setCreateTableOnStartup(true);
        return repository;
    }


    //读取配置文件的类
    @Autowired
    private SecurityProperties securityProperties;
    //成功页面的控制器
    @Autowired
    private SecuritySuccessHandle securitySuccessHandle;
    //失败页面的控制器
    @Autowired
    private Securityfailhanler securityfailhanler;
    //配置的图形验证码的过滤器
    @Autowired
    private VaildcodeFilter vaildcodeFilter;

    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    过滤器链中加入social,并且有拦截的url等信息
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(vaildcodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()   //这个是基于表单的配置,这个是匹配是否带有登录信息
                //http.httpBasic() //这个是基于SpringSecurity基础的默认配置
                .loginPage("/login/authentication")//自定义登录处理逻辑页面
                .loginProcessingUrl("/authentication/form") //告诉filter处理这个路径的登录请求
                .successHandler(securitySuccessHandle)  //处理成功登录的handler
                .failureHandler(securityfailhanler)
                //记住我的配置
                .and()
                .rememberMe()
                //提供DB服务的类
                .tokenRepository(repository())
                //token存在的时长
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                //使用的userService类
                .userDetailsService(userDetail)
                .and()
                //Session的控制
                .sessionManagement()
                //Session失效（超时）后要跳转到Url
//                .invalidSessionStrategy(XXXX类)
                .invalidSessionUrl("/url")
                .invalidSessionStrategy(new MyinvalidSession())
                //最大同时存在session的个数
                .maximumSessions(1)
                //当session存在为最大值时，阻止登录
                .maxSessionsPreventsLogin(true)
                //session若存在处理的逻辑
                .expiredUrl("/xxxx")
                .expiredSessionStrategy(new MyexpiredSession())
                .and()
                .and()
                .logout()
                //配置logout的处理路径
                .logoutUrl("/ll/hha")
//                .logoutSuccessHandler()
                .deleteCookies()
                .and()
                .authorizeRequests()
                .antMatchers("/login/authentication",
                        securityProperties.getBrowser().getLoginPage(),
                        "/login/vaildcode",
                        securityProperties.getBrowser().getLoginPage())//匹配到哪些路径
                .permitAll() //可以允许所有请求
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .authenticationProvider(daoAuthenticationProvider)
                //apply必须 extends SecurityConfigurerAdapter<O, B>
//        .apply((C) ll) ，错误的！！！
                //如果使用new的实例，很多依赖没有先被注入，会报很多空指针
//                .apply(new SmsAuthenticationConfig());
                .apply(smsAuthenticationConfig);
        //所有/auth的请求都会被Social拦截器拦截
//                .and().apply(springSocialConfigurer);
    }
}
