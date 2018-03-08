package ll.security.social.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/21
  Time: 19:41
*/
@Controller
public class Controllertest {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "lilei";
    }
}
