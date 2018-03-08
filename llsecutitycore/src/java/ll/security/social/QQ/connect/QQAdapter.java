package ll.security.social.QQ.connect;

import ll.security.social.QQ.QQ;
import ll.security.social.QQ.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.util.Assert;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/17
  Time: 10:30
*/
//将个性化信息和security进行适配
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo qqUserInfo=api.getUserInfo();
        values.setDisplayName(qqUserInfo.getNickname());
        values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(qqUserInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
