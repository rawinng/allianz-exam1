package org.rawin.allianz.exam1.service;

import org.rawin.allianz.exam1.entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RestAuthenticationSupportServiceImpl implements RestAuthenticationSupportService {

	UserSessionService userSessionService;

	@Autowired
	public RestAuthenticationSupportServiceImpl(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}

	@Override
	public Authentication createAuthenticationFromToken(String token) {
		UserSession userSession = userSessionService.searchSessionToken(token);
		if(userSession == null) { return  null; }

		return new UsernamePasswordAuthenticationToken(
				userSession.getUser().getUsername(),
				userSession.getSessionToken(),
				new ArrayList<>() );
	}

}
