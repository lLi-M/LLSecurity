package ll.security.vaildcode;

import ll.security.formProperties.SecurityProperties;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/13
  Time: 11:29
*/
@Controller
public class VaildController {

    public final static String VAILDCODE_KEY = "VAILDCODE";

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private CreateVaildCode getCreateVaildCode;

    @GetMapping("/login/vaildcode")
    public void getvaildcode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageCode imageCode = getCreateVaildCode.createVaildCode(request);
        SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
        sessionStrategy.setAttribute(new ServletWebRequest(request), VAILDCODE_KEY, imageCode);
        //判断当前环境是否为测试环境
        if (securityProperties.getRunSurrounding().equals("TEST")) {
            System.out.println(ReflectionToStringBuilder.toString(sessionStrategy.getAttribute(new ServletWebRequest(request), VAILDCODE_KEY)
                    , ToStringStyle.MULTI_LINE_STYLE));
        }
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}

//可覆盖的配置
//    public ImageCode createvaildcode(HttpServletRequest request) throws ServletRequestBindingException {
//        int width = ServletRequestUtils.getIntParameter(request,"width");
//        int height = 32;
//        //create the image
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics g = image.getGraphics();
//        // set the background color
//        g.setColor(new Color(0xDCDCDC));
//        g.fillRect(0, 0, width, height);
//        // draw the border
//        g.setColor(Color.black);
//        g.drawRect(0, 0, width - 1, height - 1);
//        // create a random instance to generate the codes
//        Random rdm = new Random();
//        String hash1 = Integer.toHexString(rdm.nextInt());
//        // make some confusion
//        for (int i = 0; i < 50; i++) {
//            int x = rdm.nextInt(width);
//            int y = rdm.nextInt(height);
//            g.drawOval(x, y, 0, 0);
//        }
//        // generate a random code
//        String capstr = hash1.substring(0, 4);
//        g.setColor(new Color(0, 100, 0));
//        g.setFont(new Font("Candara", Font.BOLD, 24));
//        g.drawString(capstr, 8, 24);
//        g.dispose();
//        return new ImageCode(image, capstr, DateTime.now().toString());
//    }
//}
