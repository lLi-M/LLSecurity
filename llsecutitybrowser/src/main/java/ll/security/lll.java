package ll.security;

import ll.security.formProperties.SecurityProperties;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/20
  Time: 16:42
*/
public class lll implements RedirectStrategy{

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

    }
}
