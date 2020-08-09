package org.rawin.allianz.exam1.ext;

import org.springframework.security.core.AuthenticationException;

public class TokenAccessDenied extends AuthenticationException {
    public TokenAccessDenied(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenAccessDenied(String msg) {
        super(msg);
    }
}
