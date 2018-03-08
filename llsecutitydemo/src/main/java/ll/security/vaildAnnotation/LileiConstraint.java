package ll.security.vaildAnnotation;/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 19:28
*/

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//该注解可以放在什么上面，字段或者是方法甚至是包
@Target(ElementType.FIELD)
//该注解在什么时候运行，Runtime：运行时 Class：类中
@Retention(RetentionPolicy.RUNTIME)
//这个注解需要调用的验证类是什么
@Constraint(validatedBy = {VaildAnnotation.class})
public @interface LileiConstraint {
    String message() default "李雷的测试信息";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
