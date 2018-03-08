package ll.security.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import ll.security.vaildAnnotation.LileiConstraint;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Past;
import java.util.Date;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 11:18
*/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {
    //这个是JosnView依赖的视图接口类，相当于身份，这个身份显示什么
    public interface UserSimpleView {
    }

    ;

    public interface UserDetailView extends UserSimpleView {
    }

    ;
    //在filed上注解表示是什么身份的信息，在Controller上则是表示显示什么身份的信息
    @JsonView(UserSimpleView.class)
    @LileiConstraint
    private String username;

    @JsonView(UserDetailView.class)
    @NotEmpty(message = "密码不能为空")
    private String password;

    private Integer id;

    @Past
    private Date birthday;

    public User(String username, String password, Integer id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }
}
