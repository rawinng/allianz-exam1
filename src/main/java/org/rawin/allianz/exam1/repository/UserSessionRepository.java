package org.rawin.allianz.exam1.repository;

import org.rawin.allianz.exam1.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {

}
