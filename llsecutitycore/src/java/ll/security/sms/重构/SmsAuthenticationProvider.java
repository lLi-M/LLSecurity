package ll.security.sms.重构;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;


/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 19:33
*/
@SuppressWarnings("uncheck")
//注册一个Sms的provider用来处理SmsAuthentication
@Data
public class SmsAuthenticationProvider implements AuthenticationProvider {

    //为什么不能进行自动装配

    private SocialUserDetailsService smsUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;

        //自定义的验证类
        SmsUser smsUser = (SmsUser) smsUserService.loadUserByUserId((String) smsAuthenticationToken.getPrincipal());

        if (smsUser == null) {
            throw new InternalAuthenticationServiceException("没找到该用户信息");
        }

        //组装通过验证的smsauthenticationToken

        SmsAuthenticationToken smsAuthenticationToke1 = new SmsAuthenticationToken(smsUser, smsUser.getAuthorities());
        smsAuthenticationToke1.setDetails(smsAuthenticationToken.getDetails());
        return smsAuthenticationToke1;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);

    }
}
