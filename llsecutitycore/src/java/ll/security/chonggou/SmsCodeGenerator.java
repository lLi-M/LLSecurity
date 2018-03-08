package ll.security.chonggou;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 13:15
*/

import ll.security.formProperties.SecurityProperties;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;


public class SmsCodeGenerator implements AllCodeGeneratorInterface<Vailcode> {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public Vailcode createCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new Vailcode(code, DateTime.now().toString(), securityProperties.getVaildCodeProperties().getExpire());
    }
}
