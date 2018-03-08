package ll.security.social.QQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/16
  Time: 10:36
*/
@Slf4j
//实现Oauth2ApiBing就是让security把AccessToken管理起来
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {


    private ObjectMapper mapper = new ObjectMapper();
    //获得用户的openID
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    //获得用户的信息
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    public QQImpl(String appId,String accessToken) {
        //传入accessToken，一个参数默认策略将token放入头信息，QQ中是放入参数中
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        //spring内置的url查询器
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        //截取字符串注意，把“也截掉
        this.openId = StringUtils.substringBetween("\"openid\":\"", "\"}");
        System.out.println(openId);
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        try {
            return mapper.readValue(result, QQUserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("类型转换出错");
            return null;
        }
        }
}
