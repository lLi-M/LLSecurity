package ll.security;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.boot.jaxb.SourceType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/10
  Time: 17:02
*/
@SpringBootTest
@RunWith(SpringRunner.class)
public class DateFormatterTest {



    @Test
    public void DateTime() {

        System.out.println(DateTime.now().centuryOfEra().toString());
        System.out.println("DateTime" + "--------->" + DateTime.now().toDateTime().toString());
        System.out.println("LocalDate" + "--------->" + DateTime.now().toLocalDate().toString());
        System.out.println("LocalDateTime" + "--------->" + DateTime.now().toLocalDateTime().toString());
        System.out.println("LocalTime" + "--------->" + DateTime.now().toLocalTime().toString());
        System.out.println("TimeStamp" + "--------->" + System.currentTimeMillis());
        System.out.println("TimeStamp" + "--------->" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println("TimeStamp" + "--------->" + DateTime.now().getMillis());
        System.out.println(DateTime.now().toString());
        Integer integer = DateTimeComparator.getInstance()
                .compare(new DateTime().toString(), new DateTime().plusSeconds(180).toString());
        //后比前大为-1，前比后打为1，前后一样为0(String可以，datetime可以)
        System.out.println(integer);
    }



    @Test
    //lambda的tomap方法
    @SuppressWarnings("uncheck")
    public void testLambdatomap() {
        List list = new ArrayList();
        list.add(new Person(1, "w"));
        list.add(new Person(2, "p"));

        Map<Integer, String> map = (Map<Integer, String>) list.stream().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println(ReflectionToStringBuilder.toString(map, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(map.size());
        System.out.println(map.keySet());
        System.out.println(map.values());
        Map<Integer, String> stringMap = Arrays.asList(new Person(1, "w"), new Person(2, "p"))
                .stream().collect(Collectors.toMap(Person::getId, Person::getName));

    }

    @Test
    public void testAnyMatch() {
        Set<Integer> set = new HashSet();
        set.add(1);
        set.add(2);
        set.add(3);
        set.stream().anyMatch(e -> e < 3);
        //比较大小，调用原来的CompareTo方法
        Optional<Integer> integer=set.stream().max(Integer::compareTo);
    }

    @Test
    public void testjava8() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        List<Person> list1 = Arrays.asList(new Person(1, "lilei", list),(new Person(3, "lilei", list)),(new Person(2, "lixiao", list)));
        //flatMap的作用是引出另一个流进行操作(流只能消费一次)一般用来把集合中的集合导出为流
        Stream<String> stream = list1.stream().flatMap(e -> e.getList().stream());

        //System.out.println(stream.count());
        //map方法是遍历出的对象组装为另一个对象
        List<String> list2 = stream.parallel().map(e -> e.concat("1")).collect(Collectors.toList());

        //lambda的ForEach封装了for循环，函数式编程
        list2.forEach(e -> System.out.println(e));

        //从集合中筛选出符合条件的值
        Set set=list1.stream().filter(person -> person.getId() >1).collect(Collectors.toSet());

        System.out.println("->>>>>>"+set.size());

//        list1.stream().
        //通过findOne找到第一个匹配的值
        for (int i=0;i<10;i++) {
            Optional<Person> person1 = list1.stream().filter(person -> person.getId() < 3).findAny();
            System.out.println(person1);
        }
        //判断集合中是否有匹配的值返回boolean
        System.out.println(list1.stream().allMatch(person -> person.getId() < 1));

    }

    @Test
    public void Datetime(){
        System.out.println(TimeUnit.SECONDS.toMinutes(60));
    }
}
