package ll.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ll.security.formProperties.ReturnEnum;
import ll.security.formProperties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/12
  Time: 19:25
*/
@Component
//自定义了登录成功后的逻辑
@Slf4j
public class SecuritySuccessHandle extends SimpleUrlAuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("进入成功界面");
        if (ReturnEnum.JSON.equals(securityProperties.getBrowser().getReturnEnum())) {
            response.setContentType("application/json;charset=UTF-8");
            //authentication是provider组装的新的token
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
