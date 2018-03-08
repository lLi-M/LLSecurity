package ll.security;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 13:50
*/

import java.util.List;

public class Man extends Person {
    private String cansay;

    public Man(Integer id, String name, String url, List<String> list) {
        super(id, name, url, list);
    }

    //调用了父类只有name参数的构造方法


    public String getCansay() {
        return cansay;
    }

    public void setCansay(String cansay) {
        this.cansay = cansay;
    }
}
