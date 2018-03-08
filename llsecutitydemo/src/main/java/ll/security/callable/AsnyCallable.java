package ll.security.callable;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/11
  Time: 16:47
*/
@Controller
public class AsnyCallable {

    @GetMapping("/create")
    @ResponseBody
    public Callable<String> order() {
        System.out.println("主线程开始");
        Callable<String> callable = () -> {
            System.out.println("副线程开始");
            Thread.sleep(1000);
            System.out.println("副线程结束");
            return "success";
        };
        System.out.println("主线程结束");

        new Thread(()->
        {

        });
        return callable;
    }

    //当接口只有一个抽象方式的时候可以使用lambda表示进行简化输出
    public String ll(){
        Lilei lilei=() ->{
           return "ll";
        };
        return "ll";
    }
}
