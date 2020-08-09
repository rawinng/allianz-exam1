package org.rawin.allianz.exam1.dto;

import org.junit.jupiter.api.Test;
import org.rawin.allianz.exam1.entity.Employee;
import org.rawin.allianz.exam1.entity.UserSession;

import java.beans.SimpleBeanInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginResponseDtoMapperTests {
    @Test
    public void testMapFromUserSession() throws Exception {
        UserSession userSession = new UserSession();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("01/02/2020");
        userSession.setExpiry(date);
        userSession.setSessionToken("2212-219a-b29291c72b89a12f8");
        LoginResponseDto loginResponseDto = LoginResponseDtoMapper.INSTANCE.fromUserSession(userSession);
        assertThat(loginResponseDto.getGeneratedToken()).isEqualTo("2212-219a-b29291c72b89a12f8");
        assertThat(dateFormat.format(loginResponseDto.getExpiryDate())).isEqualTo("01/02/2020");
    }
}
