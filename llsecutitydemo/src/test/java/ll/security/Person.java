package ll.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.util.List;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 13:50
*/
@Data
@AllArgsConstructor
public class Person  {

    private Integer id;
    private String name ;
    private String url="id=%sname=%s";
    private List<String> list;

    public Person(Integer id, String name, List<String> list) {
        this.id = id;
        this.name = name;
        this.list = list;

    }

    public String format(){
        String ur =String.format(url,id,name);
        return ur;
    }


    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;

    }

    public static void main(String[] args) {
        //用父类接受提高了通用性，实际内容和原来一样
        Person person=new Person(1,"can");
        System.out.println(person.format());
        System.out.println(ReflectionToStringBuilder.toString(person, ToStringStyle.MULTI_LINE_STYLE));
    }
}
