package org.rawin.allianz.exam1.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RestAuthenticationSupportServiceImpl implements RestAuthenticationSupportService {

	@Override
	public Authentication createAuthenticationFromToken(String token) {
		throw new UnsupportedOperationException();
	}

}
