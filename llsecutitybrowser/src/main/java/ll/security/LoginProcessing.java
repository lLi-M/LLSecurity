package ll.security;

import ll.security.formProperties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/12
  Time: 12:16
*/
@RestController
@Slf4j
public class LoginProcessing {

    //request的cache，可以获得转发之前的路径
    @Autowired
    private RequestCache requestCache;

    @Autowired
    private RedirectStrategy redirectStrategy;

    @Autowired
    private SecurityProperties securityProperties;


    //这个类为了使返回去登录逻辑为restful风格，而不是直接的html页面
    @GetMapping("/login/authentication")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String loginProcessing(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        if(savedRequest!=null){
            //网址上输入页面后，被security拦截打会配置的loginpage，进入这里后判断，然后进行处理
            String requestUrl=savedRequest.getRedirectUrl();
            if(StringUtils.endsWithIgnoreCase(requestUrl,".html")){
                redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,securityProperties.getBrowser().getLoginPage());
            }
        }
        return "您未经过授权认证";
    }
}
