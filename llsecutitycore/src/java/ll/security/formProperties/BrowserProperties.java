package ll.security.formProperties;

import lombok.Data;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/12
  Time: 17:42
*/
@Data
public class BrowserProperties {

    private String signUp_url="/ll-sign.html";

    private String loginPage ="/login.html";

    private ReturnEnum returnEnum =ReturnEnum.JSON;

    private Integer rememberMeSeconds=3600;

}
