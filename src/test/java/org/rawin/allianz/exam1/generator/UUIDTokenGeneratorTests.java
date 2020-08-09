package org.rawin.allianz.exam1.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UUIDTokenGeneratorTests {
    @Test
    public void testGenerateToken_shouldSuccess() {
        UUIDTokenGenerator uuidTokenGenerator = new UUIDTokenGenerator();
        String token = uuidTokenGenerator.generateToken();
        assertThat(token).isNotEmpty();
        assertThat(token).hasSize(36);
    }
}
