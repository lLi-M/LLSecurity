package ll.security.vaildAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 19:24
*/
//ConstraintValidator<A, T> A:是指注解类，可以获得注解类的信息  T：T为你可以放在什么上面
public class VaildAnnotation implements ConstraintValidator<LileiConstraint, String> {

    @Override
    public void initialize(LileiConstraint lileiConstraint) {
        System.out.println("初始化了");
        System.out.println(lileiConstraint.message());
    }

    //s为T，就是ConstraintValidator的String，表示了该注解的注解方的信息
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(value);
        //true为通过了验证，false为没有
        return false;
    }
}
