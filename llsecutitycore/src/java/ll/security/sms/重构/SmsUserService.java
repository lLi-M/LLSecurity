package ll.security.sms.重构;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 23:45
*/
@Component
//两个实现UserDetailService会让usernamepassword的Daoprovider失效
public class SmsUserService implements SocialUserDetailsService {

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {

        System.out.println("在数据库中根据手机号查找了用户");
        return new SocialUser(s,"1",
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
