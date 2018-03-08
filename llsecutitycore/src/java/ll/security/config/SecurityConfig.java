package ll.security.config;

import ll.security.formProperties.SecurityProperties;
import ll.security.vaildcode.CreateVaildCode;
import ll.security.vaildcode.DefaultCreateVaildCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/12
  Time: 17:53
*/
@Configuration
//启用配置类
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {


    @Bean
    @ConditionalOnMissingBean(name="MyVaildCodeCreate")
    public CreateVaildCode getCreateVaildCode(){
        return new DefaultCreateVaildCode();
    }
}
