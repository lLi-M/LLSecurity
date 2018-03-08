package ll.security.controller;

import ll.security.pojo.FileInfo;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/11
  Time: 13:27
*/
@RestController
@RequestMapping("/file")
public class UploadController {
    private final static String BASICPATH = "F:\\ll\\upload";

    @PostMapping("/upload")
    public FileInfo upload(MultipartFile file) throws IOException {
        File path = new File(BASICPATH);
        if (!path.isDirectory()) {
            path.mkdir();
        }
        String filePath = BASICPATH + File.separator + DateTime.now().toString("yyyyMMddHHmmss") + ".txt";
        File file1 = new File(filePath);
        file.transferTo(file1);
        System.out.println(filePath);
        FileInfo fileInfo = new FileInfo(file1.getAbsolutePath(),
                DateTime.now().toString(), file.getOriginalFilename());
        System.out.println(ReflectionToStringBuilder.toString(fileInfo, ToStringStyle.MULTI_LINE_STYLE));
        return fileInfo;
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") String id, HttpServletResponse response) {
        if (id.equals("1")) {
            String path = "F:\\ll\\upload\\20180211135020.txt";
            File file = new File(path);
            //放在try（）里可以自动关闭流,不用在final中关闭
            try (OutputStream outputStream = response.getOutputStream();
                 InputStream inputStream = new FileInputStream(file)) {
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition", "attachment;filename=Lilei.txt");
//                byte[] bytes = new byte[1024];
//                inputStream.read(bytes);
//                outputStream.write(bytes);
//  当我们使用输出流发送数据时，当数据不能填满输出流的缓冲区时，这时，数据就会被存储在输出流的缓冲区中。如果，我们这个时候调用关闭(close)输出流，存储在输出流的缓冲区中的数据就会丢失。所以说，关闭(close)输出流时，应先刷新(flush)换冲的输出流，话句话说就是：“迫使所有缓冲的输出数据被写出到底层输出流中”。
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
