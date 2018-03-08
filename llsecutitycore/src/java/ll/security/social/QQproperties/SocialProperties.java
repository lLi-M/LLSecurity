package ll.security.social.QQproperties;

import lombok.Data;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 11:34
*/
@Data
public class SocialProperties{
    private QQProperties qqProperties =new QQProperties();

    private String filterProcessUrl="/auth";
}
