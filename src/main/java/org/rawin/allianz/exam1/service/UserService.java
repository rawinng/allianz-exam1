package org.rawin.allianz.exam1.service;

import org.rawin.allianz.exam1.entity.User;

public interface UserService {
	User findUserByUsername(String username);
}
