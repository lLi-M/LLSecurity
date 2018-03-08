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
  Time: 12:04
*/
@SpringBootTest
@RunWith(SpringRunner.class)
public class invoke {

    @Autowired
    private TestAutowire testAutowire;

    @Test
    public void invoke() {
        testAutowire.wirte();
    }
}
