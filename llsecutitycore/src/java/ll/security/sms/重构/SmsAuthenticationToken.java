package ll.security.sms.重构;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/14
  Time: 19:05
*/

public class SmsAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 440L;
    private final Object principal;


    public SmsAuthenticationToken(Object mobile) {
        super((Collection)null);
        this.principal = mobile;

        this.setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object mobile, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = mobile;

        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();

    }
}
