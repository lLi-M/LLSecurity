package ll.security.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;


/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/20
  Time: 21:45
*/
@Component
@Slf4j
public class SocialUserDetailService implements UserDetailsService, SocialUserDetailsService {

//    @Autowired  这个是根据实现的接口自动查找实现类进行注入的
//    private PasswordEncoder passwordEncoder;

    //这个是根据IOC的名字进行注入的
    @Autowired
    private PasswordEncoder bcryptpasswordEncoder;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("enter Oauth2 Social authorization  Detail");
        String psw = bcryptpasswordEncoder.encode("123456");

        return new SocialUser(userId, psw,true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("enter Oauth2  authorization  Detail");
      String psw = bcryptpasswordEncoder.encode("123456");

        return new SocialUser(username, psw,true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER,ROLE_LILEI"));
    }
}
