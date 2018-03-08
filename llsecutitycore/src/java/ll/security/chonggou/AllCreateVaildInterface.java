package ll.security.chonggou;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 11:54
*/
public interface AllCreateVaildInterface {
    void create(ServletWebRequest request) throws IOException;

    void vaild(ServletWebRequest request,String usercod);
}
