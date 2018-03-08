package ll.security.config;

import ll.security.formProperties.SecurityProperties;
import ll.security.jwt.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/22
  Time: 18:43
*/
@Configuration
//matchIfMissing指的是当没有ll.security.storeType配置项默认加载当前类
@ConditionalOnProperty(prefix = "ll.security", name = "storeType",
        havingValue = "jwt", matchIfMissing = true)
public class JwtConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(securityProperties.getJwtProperties().getSignKey());
        return jwtAccessTokenConverter;
    }

    //在IOC中注册Token增强器
    @Bean
    @ConditionalOnMissingBean(name = "myTokenEnhancer")
    public TokenEnhancer tokenEnhancer() {
        return new JwtTokenEnhancer();
    }
}
