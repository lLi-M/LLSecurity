package ll.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ll.security.formProperties.SecurityProperties;
import ll.security.pojo.FileInfo;
import ll.security.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ShellProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 10:41
*/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SecurityProperties securityProperties;


    @PostMapping("/user/sign")
    public void sign(User user, HttpServletRequest request) {
        Connection connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        connection.getKey().getProviderId();   //获得供应商Id eg：QQ
        String userId = connection.getKey().getProviderUserId();//Adapter的中设置进去的openId
        connection.getDisplayName();//昵称
        connection.getImageUrl();//头像Url
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
    }


    @GetMapping()
    //public List<User> query( String username) 这样写参数可以不传
    //PageableDefault 配置默认的分页参数注解  query()
    @JsonView(User.UserSimpleView.class)
    public List<User> query(User user, @PageableDefault Pageable pageable) {
        //调用反射的方法apache commons的工具类，不用在类中写tostring方法
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        //自动调取实现类pageRequest
        System.out.println(pageable.getPageSize());
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }

    @GetMapping("/databind")
    public int datbind(@RequestParam(required = false) String[] name) {
        String[] strings = null;
        System.out.println(name.length);
        return name.length;
    }

    //正则表达式，控制id的匹配，只有数字才能进来  queryByid()
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User queryid(@PathVariable("id") Integer id) {
        if (id.equals(1)) {
            System.out.println("Lilei");
            return new User("Lilei", "111", 1);
        }
        return null;
    }

    @PostMapping()
    //解析Json中的属性，用requestBoy注解 TestrequestBody()
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        if (errors.hasErrors()) {
            errors.getFieldErrors().stream().limit(0).
                    forEach(e -> System.out.println(e.getField() + ":" + e.getDefaultMessage()));
        }
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.SHORT_PREFIX_STYLE));
        user.setId(1);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().stream().forEach(e ->
                    System.out.println(e.getField() + ":" + e.getDefaultMessage()));

        }
        user.setId(1);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public User delete(@PathVariable("id") Integer id) {
        System.out.println(id);
        return new User("Lilei", "llll", 1);
    }

    @GetMapping("ex")
    public void ex(Integer psd, String name) {
        System.out.println("执行------方法");
        // throw new UserNotFind(111,"出错了");
    }

    @GetMapping("/me")
    public Authentication user(Authentication authentication, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfterLast(header, "bearer ");
        log.info("获取到的Token为{}", token);
        try {
            //使用jjwt解析JWT中的信息
            //必须将token放在头信息上，key：Authorization value：bearer token
            Claims claims = Jwts.parser().setSigningKey(securityProperties.getJwtProperties().getSignKey()
                    .getBytes("UTF-8")).parseClaimsJws(token).getBody();
            String master = (String) claims.get("Master");
            log.info("解析得Claims为{}", claims);
            log.info("从Token中获得的Master为{}", master);

        } catch (UnsupportedEncodingException e) {
            log.error("token签名解析出错");
            e.printStackTrace();
            throw new RuntimeException("token签名解析出错");
        }
        log.info(getClass().getTypeName() + "进入了该方法");
        return authentication;
    }


}
