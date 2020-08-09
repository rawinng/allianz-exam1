package org.rawin.allianz.exam1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class LoginResponseDto {
    @JsonProperty("generated_token")
    private String generatedToken;
    @JsonProperty("expiry_date")
    private Date expiryDate;

    public String getGeneratedToken() {
        return generatedToken;
    }

    public void setGeneratedToken(String generatedToken) {
        this.generatedToken = generatedToken;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
