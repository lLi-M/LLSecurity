package ll.security.vaildcode;

import javax.servlet.http.HttpServletRequest;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/13
  Time: 17:25
*/
public interface CreateVaildCode {
    ImageCode createVaildCode(HttpServletRequest request);
}
