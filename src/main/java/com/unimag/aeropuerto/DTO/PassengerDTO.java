package com.unimag.aeropuerto.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class PassengerDTO {

    public record passengerCreateRequest(
            @NotBlank
            @Size(min = 2, max = 30)
            String fullName,
            @NotBlank
            @Size(min = 2, max = 30)
            @Email
            String email,
            @NotNull
            PassengerProfileDTO.passengerProfileCreateRequest passengerProfile) implements Serializable {}

    public record passengerUpdateRequest(
            @Size(min = 2, max = 30)
            String fullName,
            @Size(min = 2, max = 30)
            String email,
            PassengerProfileDTO.passengerProfileUpdateRequest passengerProfile) implements Serializable {}

    public record passengerResponse(Long id ,String fullName, String email, PassengerProfileDTO.passengerProfileView passengerProfile) implements Serializable {}
}
