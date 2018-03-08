package ll.security.appproperties;

import lombok.Data;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/22
  Time: 15:11
*/
@Data
public class AppclientProperties {
    private String clientId;
    private String clientSecret;
    private Integer accessTokenExpire=7200;
}
