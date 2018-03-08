package ll.security.social;


import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 13:24
*/
@SuppressWarnings("uncheck")
//重写父类用来自定义拦截页面
public class LlSocialConfig extends SpringSocialConfigurer {
    private String processesUrl;

    LlSocialConfig(String processesUrl){
        this.processesUrl=processesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter adapter = (SocialAuthenticationFilter) super.postProcess(object);
        adapter.setFilterProcessesUrl(processesUrl);
        return (T) adapter;
    }
}
