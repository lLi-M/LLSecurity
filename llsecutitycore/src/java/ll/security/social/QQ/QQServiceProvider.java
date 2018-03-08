package ll.security.social.QQ;

import ll.security.social.oauth2.QQOauth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/16
  Time: 17:35
*/
//有两部分组成，一部分是构造Access-token，一个是个性化的api获取用户信息
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    private final static String authorizeUrl="https://graph.qq.com/oauth2.0/authorize";

    private final static String accessTokenUrl="https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOauth2Template(appId, appSecret, authorizeUrl, accessTokenUrl));
        this.appId=appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(appId, accessToken);
    }
}
