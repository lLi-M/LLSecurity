package ll.security.social.weixin;

import lombok.Data;

import java.util.List;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/18
  Time: 18:02
*/
@Data
public class WeixinUser {

    private String openid;
    private String nickname;
    private Integer sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private List<?> privilege;
    private String unionid;

}
