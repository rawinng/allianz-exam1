package org.rawin.allianz.exam1.ext;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class SessionTokenAuthenticationToken extends AbstractAuthenticationToken {

    private String sessionToken;

    public SessionTokenAuthenticationToken(String sessionToken) {
        super(Collections.emptyList());this.sessionToken = sessionToken;
    }

    @Override
    public Object getCredentials() {
        return sessionToken;
    }

    @Override
    public Object getPrincipal() {
        return sessionToken;
    }
}
