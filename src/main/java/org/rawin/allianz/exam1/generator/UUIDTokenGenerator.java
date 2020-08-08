package org.rawin.allianz.exam1.generator;

import java.util.UUID;

/**
 * Generate Token bu UUID
 * @author rawin
 *
 */
public class UUIDTokenGenerator implements TokenGenerator {

	@Override
	public String generateToken() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
