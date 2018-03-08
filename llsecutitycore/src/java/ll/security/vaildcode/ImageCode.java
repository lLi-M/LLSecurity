package ll.security.vaildcode;

import ll.security.formProperties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.awt.image.BufferedImage;


/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/13
  Time: 11:53
*/


@NoArgsConstructor
@Data
public class ImageCode {

    @Autowired
    private SecurityProperties securityProperties;

    private BufferedImage image;
    private String imageCode;
    private String expireTime;

    public boolean isExpire() {
        String plusTime = new DateTime(expireTime).plusSeconds(securityProperties.getVaildCodeProperties().getExpire()).toString();
        if (DateTimeComparator.getInstance().compare(plusTime, DateTime.now().toString())== -1){
            //后比前大为-1，前比后打为1，前后一样为0(String可以，datetime可以)
            return true;
        }
        return false;

    }

    public ImageCode(BufferedImage image, String imageCode, String expireTime) {
        this.image = image;
        this.imageCode = imageCode;
        this.expireTime = expireTime;
    }
}
