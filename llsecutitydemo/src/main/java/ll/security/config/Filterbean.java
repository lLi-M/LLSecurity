package ll.security.config;

import ll.security.filter.FirstFilter;
import ll.security.inteceptor.interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 22:10
*/
//@Configuration
public class Filterbean extends WebMvcConfigurerAdapter{

    @Autowired
private interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(interceptor);
    }

   // @Bean
    public FilterRegistrationBean filters() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new FirstFilter());
        List list=new ArrayList();
        list.add("/*");
        filterRegistrationBean.setUrlPatterns(list);
        return filterRegistrationBean;
    }
}
