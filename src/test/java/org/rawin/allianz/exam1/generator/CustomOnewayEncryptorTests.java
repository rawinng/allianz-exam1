package org.rawin.allianz.exam1.generator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CustomOnewayEncryptorTests {
    @Test
    public void testEncryptPassword() {
        CustomOnewayEncryptor customOnewayEncryptor = new CustomOnewayEncryptor();
        String passwordResult = customOnewayEncryptor.encrypt("password");
        assertThat(passwordResult).isEqualTo("337eb0c47e17a547226e8900c10ab84b");
    }

    @Test
    public void testEncrypt() {
        CustomOnewayEncryptor customOnewayEncryptor = new CustomOnewayEncryptor();
        String source1 = "passd4w";
        String source2 = "pa92178ssdw";
        String source3 = "passd4w";

        String result1 = customOnewayEncryptor.encrypt(source1);
        String result2 = customOnewayEncryptor.encrypt(source2);
        String result3 = customOnewayEncryptor.encrypt(source3);
        String result4 = customOnewayEncryptor.encrypt(source1);

        assertThat(result1).isNotEmpty();
        assertThat(result2).isNotEmpty();
        assertThat(result3).isNotEmpty();
        assertThat(result4).isNotEmpty();

        assertThat(result1).isEqualTo(result3);
        assertThat(result1).isEqualTo(result4);
        assertThat(result1).isNotEqualTo(result2);
    }
}
