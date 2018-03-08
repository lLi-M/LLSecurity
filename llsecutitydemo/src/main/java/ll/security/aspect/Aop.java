package ll.security.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 23:51
*/
//@Aspect
//@Component
public class Aop {

    private  static Integer i=0;

    @Around("execution(* ll.security.controller..*(..))")
    public Object aop(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Aop:aop begin");
        Object[] args = pjp.getArgs();
        Arrays.stream(args).forEach(e -> System.out.println("Aop:arg" + Addnumber() + ":" + e));
        Object o = pjp.proceed();//表示可以调用被他拦截的方法 和chain.doFilter一样
        System.out.println("Aop:aop finish");

        return o;
    }

    public  Integer Addnumber() {
        i++;
        return i;
    }

}
