package com.unimag.aeropuerto.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class PassengerProfileDTO {

    public record passengerProfileCreateRequest(
            @NotBlank
            @Size(min = 3, max = 15)
            String phoneNumber,
            @NotBlank
            @Size(min = 2, max = 30)
            String countryCode) implements Serializable {}

    public record passengerProfileUpdateRequest(
            @Size(min = 3, max = 15)
            String phoneNumber,
            @Size(min = 2, max = 30)
            String countryCode) implements Serializable {}

    public record passengerProfileResponse(Long passengerProfileID, String phoneNumber, String countryCode) implements Serializable {}
    public record passengerProfileView(String phone, String countryCode) implements Serializable {}
}
