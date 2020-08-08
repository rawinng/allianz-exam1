package org.rawin.allianz.exam1.controller;

import org.rawin.allianz.exam1.dto.LoginRequestDto;
import org.rawin.allianz.exam1.dto.LoginResultDto;
import org.rawin.allianz.exam1.entity.User;
import org.rawin.allianz.exam1.generator.OnewayEncryptor;
import org.rawin.allianz.exam1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	OnewayEncryptor onewayEncryptor;
	
	@PostMapping
	public ResponseEntity<LoginResultDto> login(LoginRequestDto request) {
		if(request == null 
				|| StringUtils.isEmpty(request.getUsername())
				|| StringUtils.isEmpty(request.getPassword())
				) {
			
			return ResponseEntity.badRequest().build();
		}
		
		User user = userService.findUserByUsername(request.getUsername());
		if(user == null) {
			// wrong user
			return ResponseEntity.unprocessableEntity().build();
		}
		
		// checkPassword
		String saltedInputPassword = onewayEncryptor.encrypt(request.getPassword());
		if(!saltedInputPassword.equals(user.getPassword())) {
			// wrong password
			return ResponseEntity.unprocessableEntity().build();
		}
		
		
	}
}
