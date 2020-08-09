package org.rawin.allianz.exam1.controller;

import org.rawin.allianz.exam1.dto.LoginRequestDto;
import org.rawin.allianz.exam1.dto.LoginResponseDto;
import org.rawin.allianz.exam1.dto.LoginResponseDtoMapper;
import org.rawin.allianz.exam1.entity.User;
import org.rawin.allianz.exam1.entity.UserSession;
import org.rawin.allianz.exam1.generator.OnewayEncryptor;
import org.rawin.allianz.exam1.generator.TokenGenerator;
import org.rawin.allianz.exam1.service.UserService;
import org.rawin.allianz.exam1.service.UserSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequestMapping("/login")
public class LoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserSessionService userSessionService;

	@Autowired
	OnewayEncryptor onewayEncryptor;
	
	@Autowired
	TokenGenerator tokenGenerator;

	@PostMapping
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
		if(request == null 
				|| StringUtils.isEmpty(request.getUsername())
				|| StringUtils.isEmpty(request.getPassword())
				) {
			
			return ResponseEntity.badRequest().build();
		}
		
		User user = userService.findUserByUsername(request.getUsername());
		if(user == null) {
			// wrong user
			LOGGER.info("No user found : {}", request.getUsername());
			return ResponseEntity.notFound().build();
		}
		if(!user.getEnabled()) {
			// wrong user
			LOGGER.info("User found but disabled : {}", request.getUsername());
			return ResponseEntity.notFound().build();
		}

		// checkPassword
		String saltedInputPassword = onewayEncryptor.encrypt(request.getPassword());
		if(!saltedInputPassword.equals(user.getPassword())) {
			// wrong password
			LOGGER.info("Not matching password : {}", request.getPassword());
			return ResponseEntity.notFound().build();
		}
		
		// generate session 
		UserSession userSession = new UserSession();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		userSession.setSessionToken(tokenGenerator.generateToken());
		userSession.setUser(user);
		userSession.setExpiry(cal.getTime());
		userSessionService.insertOrReplace(userSession);

		LoginResponseDto resultDto = LoginResponseDtoMapper.INSTANCE.fromUserSession(userSession);
		return ResponseEntity.ok(resultDto);
	}
}
