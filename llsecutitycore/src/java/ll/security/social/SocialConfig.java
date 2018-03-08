package ll.security.social;

import ll.security.formProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 10:58
*/
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    //用来定位connectFactory
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix("ll_");
        //配置自动注册
        jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        return jdbcUsersConnectionRepository;
    }

    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        //改变了socialFilter拦截的url，变为可自己定义
        SpringSocialConfigurer configurer = new LlSocialConfig(securityProperties.getSocialProperties().getFilterProcessUrl());
        //可配置的sign的url
        configurer.signupUrl(securityProperties.getBrowser().getSignUp_url());
        return configurer;
    }

    @Bean
    //用来交互security中的信息，从别的地方放入，取出
    public ProviderSignInUtils providerSignInUtils() {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }
}
