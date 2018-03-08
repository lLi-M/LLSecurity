package ll.security.social.QQ.connect;

import ll.security.social.QQ.QQ;
import ll.security.social.QQ.QQServiceProvider;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 10:53
*/
public class QQconnectFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId      the provider id e.g. "facebook"

     */
    public QQconnectFactory(String providerId ,String appId,String appSecret){
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
