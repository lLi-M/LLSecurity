package ll.security.config;

import ll.security.appproperties.AppclientProperties;
import ll.security.appproperties.Appclients;
import ll.security.formProperties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.support.RedisRepositoryFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/20
  Time: 21:49
*/
@Configuration
@EnableAuthorizationServer
@Slf4j
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    //当配置文件中有ll.security.storeType=redis,加载该Bean（本地化accessToken）
    @ConditionalOnProperty(prefix = "ll.security", name = "storeType", havingValue = "redis")
    public TokenStore redistokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Autowired(required = false)
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer tokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        if (tokenStore != null) {
            log.info("加载了Access_Token");
            //配置token的储存模式，常用的有两种
            //（1）redisTokenStore : redisTokenStore是将access_token保存在redis中
            //（2）JwtTokenStore :  不清楚是干神马的
            endpoints.tokenStore(tokenStore);
        }
        if (jwtAccessTokenConverter != null && tokenEnhancer != null) {
            log.info("加载了jwtConvert");
            //token增强链，这里用来增加返回的token字段
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//            List<TokenEnhancer> list = new ArrayList();
//            list.add(tokenEnhancer);
//            list.add(jwtAccessTokenConverter);
//            enhancerChain.setTokenEnhancers(list);
            enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer,jwtAccessTokenConverter));
            //设置增强和token转换器
            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //将clients信息存储到内存中
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        //遍历组装clients信息（appId，appSecret）
        if (!CollectionUtils.isEmpty(Arrays.asList(securityProperties.getAppclients().getAppclients()))) {
            Arrays.asList(securityProperties.getAppclients().getAppclients())
                    .forEach(e -> buildClient(builder, e));
        } else {
            throw new RuntimeException("Clients为空，请检查配置是否正确");
        }
    }

    private void buildClient(InMemoryClientDetailsServiceBuilder builder, AppclientProperties appclientProperties) {
        log.info("进入lambda循环配置阶段");
        builder.withClient(appclientProperties.getClientId())
                .secret(appclientProperties.getClientSecret())
                .authorizedGrantTypes("authorization_code","refresh_token","password")
                .accessTokenValiditySeconds(appclientProperties.getAccessTokenExpire())
                .scopes("all", "read", "write");
    }
}
