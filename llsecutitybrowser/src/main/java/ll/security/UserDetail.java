package ll.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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
  Date: 2018/2/11
  Time: 23:12
*/
//自定义用户名的验证
@Component
@Slf4j
public class UserDetail implements UserDetailsService{
//    这里可以自定义用户验证
//    @Autowired
//    private UsernameDao usernameDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //这里可以查询用户信息用于传入

        String psw = passwordEncoder.encode("123456");
        if (log.isInfoEnabled()) {
            log.error(psw);
        }
        //这个User是security提供的实现UserDetails的实现类，也可以自己来实现
        //这个就是表单登录的security的内部通行证
        return new User("lilei", psw
                //AuthorityUtils，Security提供的工具类这个可以对字符串的，进行分割
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
    }

//    //用短信验证码登录没有经过matcher？？？？？？？？？？？
//    @Override
//    //Social的UserDetailService验证DB
//    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
//
//        String psw = passwordEncoder.encode("123456");
//        if (log.isInfoEnabled()) {
//            log.error(psw);
//        }
//        //这个User是security提供的实现SocialUserDetails的实现类，也可以自己来实现
//        return new SocialUser(userId, psw
//                //这个组装就是security内部的通行证
//                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
   // }

}
