package ll.security.chonggou;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 12:49
*/
@Data
//lombok对继承类的全构造函数不起作用
@NoArgsConstructor
public class ImageVaildCode extends Vailcode {
    private BufferedImage image;


    public ImageVaildCode(String code, String expire, Integer expireSecond, BufferedImage image) {
        super(code, expire, expireSecond);
        this.image = image;
    }


}
