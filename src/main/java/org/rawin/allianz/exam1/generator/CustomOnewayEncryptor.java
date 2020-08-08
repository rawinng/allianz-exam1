package org.rawin.allianz.exam1.generator;

import liquibase.util.MD5Util;

public class CustomOnewayEncryptor implements OnewayEncryptor {

	private static final String SALT1 = "sX2i@";
	private static final String SALT2 = "s78@C";

	@Override
	public String encrypt(String source) {
		return MD5Util.computeMD5(applySalt(source));
	}

	private String applySalt(String source) {
		return SALT1 + source + SALT2;
	}

}
