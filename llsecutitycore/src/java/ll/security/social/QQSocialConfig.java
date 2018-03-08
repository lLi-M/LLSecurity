package ll.security.social;

import ll.security.formProperties.SecurityProperties;
import ll.security.social.QQ.connect.QQconnectFactory;
import ll.security.social.QQproperties.QQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.social.connect.ConnectionFactory;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 11:43
*/
@Configuration
//配置文件中有appID存在才会注册这个bean
@ConditionalOnProperty(prefix = "ll.security.socialProperties.qqProperties" ,name = "appId")
public class QQSocialConfig extends SocialAutoConfigurerAdapter {

    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties=securityProperties.getSocialProperties().getQqProperties();
        return new QQconnectFactory(qqProperties.getProviderId()
                ,qqProperties.getAppId(),qqProperties.getAppSecret());
    }
}
