package org.rawin.allianz.exam1.service;

import org.springframework.security.core.Authentication;

public interface RestAuthenticationSupportService {
	Authentication createAuthenticationFromToken(String token);
}
