package ll.security.vaildcode;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/13
  Time: 20:18
*/
//自定义图片生成器
//@Component("MyVaildCodeCreate")
public class MyVaildCodeCreate implements CreateVaildCode {
    @Override
    public ImageCode createVaildCode(HttpServletRequest request) {
        System.out.println("自定义图片生成器");
        int width = 100;
        int height = 100;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        String hash1 = Integer.toHexString(rdm.nextInt());
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String capstr = hash1.substring(0, 4);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(capstr, 8, 24);
        g.dispose();
        return new ImageCode(image, capstr, DateTime.now().toString());
    }
}
