package org.rawin.allianz.exam1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDto {
    @JsonProperty("generated_token")
    private String generatedToken;
    @JsonProperty("expiry_date")
    private String expiryDate;

    public String getGeneratedToken() {
        return generatedToken;
    }

    public void setGeneratedToken(String generatedToken) {
        this.generatedToken = generatedToken;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
