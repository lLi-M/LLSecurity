package ll.security.chonggou;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 14:26
*/
@Component
public class ImageProcessor extends AbstractVaildProcessor<ImageVaildCode> {

    @Override
    public void send(ServletWebRequest webRequest, ImageVaildCode vailcode) throws IOException {
        OutputStream outputStream=webRequest.getResponse().getOutputStream();
        ImageIO.write(vailcode.getImage(),"JPEG",outputStream);
    }

    @Override
    public void saveCode(ServletWebRequest request, ImageVaildCode vailcode) {
        sessionStrategy.setAttribute(request,SaveEnum.IMAGE.toString(),vailcode);
    }

}
