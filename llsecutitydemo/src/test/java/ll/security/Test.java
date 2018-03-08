package ll.security;

import ll.security.chonggou.SaveEnum;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 10:05
*/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    private WebApplicationContext wtx;

    private MockMvc mockMvc;

    @org.junit.Test
    public void testdatabind() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/databind")
                .param("name", "lieli")
//                .param("name","Lilei")
//                .param("name","hah")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wtx).build();
    }

    @org.junit.Test
    public void query() throws Exception {
        //mockMvc比对返回字符串类型
  /*
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("hello Spring security"));
   */

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username", "Lilei")
                .param("password", "123456")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()")
                        .value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @org.junit.Test
    public void queryByid() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                        .value("Lilei"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @org.junit.Test
    public void TestrequestBody() throws Exception {
        String s = "{\"username\":\"Lilei\",\"password\":null}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(s))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(1));
    }

    @org.junit.Test
    public void Put() throws Exception {
        long time = DateTime.now().plusDays(1).getMillis();
        String s = "{\"username\":\"Lilei\",\"password\":null,\"birthday\":" + time + "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(s)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(1));
    }

    @org.junit.Test
    public void Delete() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(1))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @org.junit.Test
    public void upload() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file/upload")
                .file(new MockMultipartFile("file", "Lilei.txt", "text/plain", "李雷在这里".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @org.junit.Test
    public void download() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file/download/F:\\ll\\upload\\20180211135020.txt"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @org.junit.Test
    public void collect() {
        String[] strings = {};
        String s="1";
        strings = s.split(",");
        System.out.println(strings);
        Set<String> set = Arrays.stream(strings).collect(Collectors.toSet());
        set.stream().forEach(e -> System.out.println(e));
    }

    @org.junit.Test
    @SuppressWarnings("uncheck")
    public void testSimpleName(){
        System.out.println(getClass().getSimpleName());
        System.out.println(SaveEnum.valueOf("SMSCODE"));
    }


}
