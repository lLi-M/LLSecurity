package ll.security.formProperties;

import ll.security.appproperties.Appclients;
import ll.security.appproperties.JwtProperties;
import ll.security.formProperties.vaildproperties.SmsCodeProperties;
import ll.security.formProperties.vaildproperties.VaildCodeProperties;
import ll.security.social.QQproperties.SocialProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/12
  Time: 17:42
*/
@ConfigurationProperties(prefix = "ll.security")
@Data
public class SecurityProperties {
    //必须实例化
    private BrowserProperties browser =new BrowserProperties();

    private String runSurrounding ="RUNTIME";

    private Appclients appclients=new Appclients();

    private JwtProperties jwtProperties=new JwtProperties();

    //必须在这里实例化，如果不在这里实例化就会空指针异常，因为当前环境没有该实例
    private VaildCodeProperties vaildCodeProperties =new VaildCodeProperties();

    private SmsCodeProperties smsCodeProperties=new SmsCodeProperties();

    private SocialProperties socialProperties=new SocialProperties();


}
