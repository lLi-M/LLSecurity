package ll.security.sms.重构;

import ll.security.chonggou.AbstractVaildProcessor;
import ll.security.chonggou.SaveEnum;
import ll.security.formProperties.SecurityProperties;
import ll.security.vaildcode.VaildCodeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/15
  Time: 14:17
*/

public class SmsImageVaildFiter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private Map<String,AbstractVaildProcessor> processorMap;

    //调用IOC中的失败处理器
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private Map<String, String> urlMap = new HashMap<>();

    //a路径匹配器
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    //实现InitializingBean可以在IOC初始化后，就快速拼装验证url的Map，K为url，V为Type（SMS，IMAGE）
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String imageUrl = securityProperties.getVaildCodeProperties().getUrls();
        String smsUrl = securityProperties.getSmsCodeProperties().getUrls();
        String[] imageUrls = imageUrl.split(",");
        String[] smsUrls = smsUrl.split(",");
        Arrays.stream(imageUrls).parallel().forEach(e -> urlMap.put(e, SaveEnum.IMAGE.toString()));
        Arrays.stream(smsUrls).parallel().forEach(e -> urlMap.put(e, SaveEnum.SMS.toString()));
    }

    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//      Set<String> set= urlMap.keySet().stream().filter(e
//              ->antPathMatcher.match(e,request.getRequestURI())).
//              collect(Collectors.toSet());
//        System.out.println(set.size());

        for (String url : urlMap.keySet()) {
            //用PathMatcher判断url，可以匹配*
            if (antPathMatcher.match(url, request.getRequestURI())) {
                //匹配Spring提供的依赖查找
             AbstractVaildProcessor vaildProcessor=processorMap.get(urlMap.get(url).toLowerCase()+"process");
                try {
                    vaildProcessor.vaild(new ServletWebRequest(request),request.getParameter("smscode"));
                } catch (VaildCodeException e) {
                    e.printStackTrace();
                    authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                    return;
                }
             }
             filterChain.doFilter(request,response);
        }

    }


}
