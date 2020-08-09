package org.rawin.allianz.exam1.ext;

import java.util.ArrayList;

import org.rawin.allianz.exam1.service.RestAuthenticationSupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomRestApiAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRestApiAuthenticationProvider.class);
	
	private	RestAuthenticationSupportService restAuthen;
	
	public CustomRestApiAuthenticationProvider(RestAuthenticationSupportService restAuthen) {
		this.restAuthen = restAuthen;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(authentication == null) {
			throw new InternalAuthenticationServiceException("auth object is null");
		}

		String token = (String) authentication.getPrincipal();
		Authentication authenticationFromToken = this.restAuthen.createAuthenticationFromToken(token);
		if(authenticationFromToken == null) {
			throw new TokenAccessDenied("Invalid token : " + token);
		}
		return authenticationFromToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SessionTokenAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
