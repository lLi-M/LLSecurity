package ll.security;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/21
  Time: 11:58
*/
@Component
public class TestAutowire {

    private  Wirte wirte;

    TestAutowire(Wirte wirte){
        this.wirte=wirte;
    }

    public void wirte(){
        wirte.write();
    }
}
