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
		
		Object authDetail = authentication.getDetails();
		Object authPrin = authentication.getPrincipal();
		String token = authDetail.toString();
		Authentication restResult = this.restAuthen.createAuthenticationFromToken(token);
		return new UsernamePasswordAuthenticationToken(restResult.getPrincipal(), 
				restResult.getCredentials(), new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		LOGGER.debug("class name to check = {}", authentication.getName()
				);
		return true;
	}
}
