package ll.security.sms.重构;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 19:16
*/
//拦截短信登录获得到mobile（手机号）
public class SmsAuthenticationFilter  extends AbstractAuthenticationProcessingFilter {
    public static final String LL_SECURITY_MOBILE_KEY = "mobile";

    private String mobileParameter = LL_SECURITY_MOBILE_KEY;
    private boolean postOnly = true;

    public SmsAuthenticationFilter() {
        //filter匹配路径，和匹配的method类型
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    //第一次组装未认证成功的smsauthenticationToken
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobile = this.obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }
            mobile = mobile.trim();
            SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile);
            //将request中的细节设置进去（ip。。等等）
            this.setDetails(request, authRequest);
            //交给authenticationManager去寻找匹配的authenticationProvider验证
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    //从request获得名字为mobile的手机号
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    //setDetail的方法
    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return this.mobileParameter;
    }


}
