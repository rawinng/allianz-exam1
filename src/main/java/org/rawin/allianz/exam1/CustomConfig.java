package org.rawin.allianz.exam1;

import org.rawin.allianz.exam1.generator.CustomOnewayEncryptor;
import org.rawin.allianz.exam1.generator.OnewayEncryptor;
import org.rawin.allianz.exam1.generator.TokenGenerator;
import org.rawin.allianz.exam1.generator.UUIDTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

	@Bean("tokenGenerator")
	public TokenGenerator tokenGenerator() {
		return new UUIDTokenGenerator();
	}
	
	@Bean("onewayEncryptor")
	public OnewayEncryptor onewayEncryptor() {
		return new CustomOnewayEncryptor();
	}
}
