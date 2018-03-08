package ll.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ll.security.formProperties.ReturnEnum;
import ll.security.formProperties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/12
  Time: 19:46
*/
@Component
@Slf4j
public class Securityfailhanler extends SimpleUrlAuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("进入失败");
        if (ReturnEnum.JSON.equals(securityProperties.getBrowser().getReturnEnum())) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Status:" + HttpStatus.UNAUTHORIZED.value() + objectMapper.writeValueAsString(exception.getMessage()));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
