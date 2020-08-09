package org.rawin.allianz.exam1.service;

import org.rawin.allianz.exam1.entity.UserSession;
import org.rawin.allianz.exam1.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserSessionServiceImpl implements UserSessionService {

	UserSessionRepository userSessionRepository;
	
	@Autowired
	public UserSessionServiceImpl(UserSessionRepository userSessionRepository) {
		this.userSessionRepository = userSessionRepository;
	}

	@Override
	public void insertOrReplace(UserSession userSession) {
		// Delete the existing
		UserSession criteriaUserSession = new UserSession();
		criteriaUserSession.setUser(userSession.getUser());
		Optional<UserSession> userSessionFromDb = this.userSessionRepository.findOne(Example.of(criteriaUserSession));
		userSessionFromDb.ifPresent( obj -> userSessionRepository.delete(obj));
		// Save the new one
		this.userSessionRepository.save(userSession);
	}

	@Override
	public UserSession searchSessionToken(String sessionToken) {
		Date now = Calendar.getInstance().getTime();
		List<UserSession> userSessionList =
				this.userSessionRepository.findBySessionTokenAndLessThanExpiry(sessionToken, now);
		if(userSessionList.size() == 0) {
			return null;
		}
		if(userSessionList.size() > 1) {
			throw new IllegalStateException("UserSession should not have concurrent session for each user");
		}
		return userSessionList.get(0);
	}
}
