package com.unimag.aeropuerto.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class AirportDTO {

    public record AirportCreateRequest(
            @NotBlank
            @Size(min = 2, max = 30)
            String name,
            @NotBlank
            @Size(min = 2, max = 30)
            String code,
            @NotBlank
            @Size(min = 2, max = 30)
            String city) implements Serializable {}

    public record AirportUpdateRequest(
            @Size(min = 2, max = 30)
            String name,
            @Size(min = 2, max = 30)
            String code,
            @Size(min = 2, max = 30)
            String city) implements Serializable {}

    public record AirportFlightView(Long id, String name, String code, String city) implements Serializable {}
    public record AirportResponse(Long id,
                                  String name,
                                  String code,
                                  String city,
                                  List<FlightDTO.flightAirportView> flightsOrigin,
                                  List<FlightDTO.flightAirportView> flightsDestination) implements Serializable {}
}
