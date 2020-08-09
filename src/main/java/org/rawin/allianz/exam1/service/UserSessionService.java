package org.rawin.allianz.exam1.service;

import org.rawin.allianz.exam1.entity.UserSession;

public interface UserSessionService {
	void insertOrReplace(UserSession userSession);

	UserSession searchSessionToken(String sessionToken);
}
