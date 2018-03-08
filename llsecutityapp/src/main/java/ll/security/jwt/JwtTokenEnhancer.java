package ll.security.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/22
  Time: 19:27
*/
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String,Object> map=new HashMap();
        map.put("Master","Lilei");
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(map);
        return accessToken;
    }
}
