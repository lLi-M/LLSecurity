package ll.security.filter;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Date;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 22:16
*/
//@Component 可以加component
//拦截器中servlet中只能拿到request和response，如果需要spring管理的内容需要使用拦截器

public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化servlet");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        DateTime statrtime = DateTime.now();
        long time = new Date().getTime();
        System.out.println("Filter:Do Filter");
        filterChain.doFilter(servletRequest, servletResponse);
        Duration duration = new Duration(statrtime, DateTime.now());
        System.out.println(duration.getStandardSeconds());
        System.out.println(new Date().getTime() - time);
        System.out.println("Fliter:Filter..finish");
    }

    @Override
    public void destroy() {
        System.out.println("销毁了");
    }
}
