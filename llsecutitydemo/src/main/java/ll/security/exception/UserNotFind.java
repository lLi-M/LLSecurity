package ll.security.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 21:43
*/
@Getter
@NoArgsConstructor
public class UserNotFind extends RuntimeException {

    private Integer id;
    private String msg;

    public UserNotFind(Integer id,String msg){
        super(msg);
        this.msg=msg;
        this.id=id;
    }
}
