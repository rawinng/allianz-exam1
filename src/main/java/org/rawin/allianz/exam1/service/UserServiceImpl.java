package org.rawin.allianz.exam1.service;

import java.util.Optional;

import org.hibernate.criterion.Example;
import org.rawin.allianz.exam1.entity.User;
import org.rawin.allianz.exam1.entity.UserSession;
import org.rawin.allianz.exam1.generator.TokenGenerator;
import org.rawin.allianz.exam1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	TokenGenerator tokenGenerator;
	
	UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(@Qualifier("tokenGenerator") TokenGenerator tokenGenerator, UserRepository userRepository) {
		this.tokenGenerator = tokenGenerator;
		this.userRepository = userRepository;
	}

	@Override
	public User findUserByUsername(String username) {
		Optional<User> user = userRepository.findById(username);
		if(user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}	
	
}
