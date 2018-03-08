package ll.security;

import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/21
  Time: 22:43
*/
@Component
public class llImpl implements ll {

    @Override
    public Integer sub(Integer integer, Integer integer1) {
        return integer-integer1;
    }
}
