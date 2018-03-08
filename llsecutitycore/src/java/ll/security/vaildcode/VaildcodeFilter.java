package ll.security.vaildcode;


import ll.security.chonggou.AllCreateVaildInterface;
import ll.security.formProperties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/13
  Time: 12:37
*/
@Component
@Slf4j
public class VaildcodeFilter extends OncePerRequestFilter {

    public SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private Map<String, AllCreateVaildInterface> allCreateVaildInterfaceMaps;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (securityProperties.getRunSurrounding().equals("TEST")) {

            System.out.println("验证器。。。。。。。。");
            System.out.println((request.getRequestURI() + "\n" + request.getMethod()));
        }
        String[] urls = securityProperties.getVaildCodeProperties().getUrls().split(",");
        Set<String> urlset = Arrays.stream(urls).collect(Collectors.toSet());
        urlset.add("/form/login");
        for (String url : urls) {
            System.out.println(url);
            if (antPathMatcher.match(url, request.getRequestURI())) {
                try {
//                  allCreateVaildInterfaceMaps.get();
                    vaild(new ServletWebRequest(request));
                } catch (VaildCodeException e) {
                    authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                    return;
                }
            }
        }
        log.info("通过了图片验证码");
        filterChain.doFilter(request, response);
    }

    //详细验证逻辑
    public void vaild(ServletWebRequest webRequest) throws VaildCodeException {
        ImageCode vaildcode = (ImageCode) sessionStrategy.getAttribute(webRequest, VaildController.VAILDCODE_KEY);
        if (securityProperties.getRunSurrounding().equals("TEST")) {
            System.out.println("进入验证验证码方法");
        }

        if (StringUtils.isEmpty(vaildcode)) {
            throw new VaildCodeException("验证码不能为空");
        }

        if (vaildcode.isExpire()) {
            throw new VaildCodeException("验证码已经过期");
        }

        if (!(webRequest.getParameter("vaildcode").equals(vaildcode.getImageCode()))) {
            log.info(webRequest.getParameter("vaildcode"));
            log.info(vaildcode.getImageCode());
            throw new VaildCodeException("您输入验证码不匹配");
        }

    }

}
