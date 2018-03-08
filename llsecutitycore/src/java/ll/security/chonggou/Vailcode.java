package ll.security.chonggou;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 12:06
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vailcode {

    private String code;

    private String expire;

    private Integer expireSecond;

    //父类的默认比较方法，180s
    public Boolean expireTimeIspast() {
        Integer i = DateTimeComparator.getInstance().compare(new DateTime(expire).plusSeconds(expireSecond).toString(), DateTime.now().toString());
        //后者比前者大，返回-1
        if (i == -1) {
            return true;
        }
        return false;
    }
}
