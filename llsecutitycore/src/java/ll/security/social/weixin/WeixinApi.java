package ll.security.social.weixin;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/18
  Time: 18:10
*/
//设置了装配规则会自动装配AccessToken
public class WeixinApi extends AbstractOAuth2ApiBinding implements Weixin {
    private static final String GetUserInfoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    WeixinApi(String accessToken){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

    }
//    @Autowired
//    private RedisTemplate<Object,String> redisTemplate;

    @Override
    public WeixinUser getUserInfo() {
        return null;
    }
}
