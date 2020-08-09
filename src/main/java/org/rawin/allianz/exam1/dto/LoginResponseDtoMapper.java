package org.rawin.allianz.exam1.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rawin.allianz.exam1.entity.UserSession;

@Mapper
public interface LoginResponseDtoMapper {
    LoginResponseDtoMapper INSTANCE = Mappers.getMapper(LoginResponseDtoMapper.class);

    @Mapping(source = "sessionToken", target = "generatedToken")
    @Mapping(source = "expiry", target = "expiryDate")
    LoginResponseDto fromUserSession(UserSession userSession);
}
