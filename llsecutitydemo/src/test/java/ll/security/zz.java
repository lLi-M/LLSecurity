package ll.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/21
  Time: 22:32
*/
@SpringBootTest
@RunWith(SpringRunner.class)
public class zz {

    @Autowired(required = false)
    private ll ll;
    @Test
    public void ll(){
        Integer integer=10;
        Integer integer1=3;
        if(ll!=null){
           integer=ll.sub(integer,integer1);
            System.out.println(integer);
        }
        System.out.println(integer);
    }
}
