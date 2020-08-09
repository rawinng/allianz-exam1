package org.rawin.allianz.exam1.repository;

import org.rawin.allianz.exam1.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {

    @Query("select userSession from UserSession userSession where userSession.sessionToken = ?1 and userSession.expiry > ?2 ")
    List<UserSession> findBySessionTokenAndLessThanExpiry(String sessionToken, Date lessThanExpiry);
}
