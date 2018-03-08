package ll.security.social.oauth2;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 17:05
*/
@Slf4j
public class QQOauth2Template extends OAuth2Template {


    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        //将boolean设为true，否则不会带上id，secret这两个参数
        setUseParametersForClientAuthentication(true);

    }

    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        //QQ互联，返回的与spring定义的不一样，所以需要重写
        if (log.isInfoEnabled()) {
            log.info("获取AccessToken返回值为{}", responseStr);
        }
        String[] strings = StringUtils.split(responseStr, "&");
        String accessToken = StringUtils.substringAfterLast(strings[0], "=");
        Long expiresIn = Long.parseLong(StringUtils.substringAfterLast(strings[1], "="));
        String refreshToken = StringUtils.substringAfterLast(strings[2], "=");
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    //扩展转换器方法，否则无法完成获取AccessToken时返回的text/html类型的页面
    @Override
    protected RestTemplate createRestTemplate() {
            RestTemplate restTemplate = super.createRestTemplate();
            //替换charset否则默认为ISO-8859-1
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
            return restTemplate;
    }
}
