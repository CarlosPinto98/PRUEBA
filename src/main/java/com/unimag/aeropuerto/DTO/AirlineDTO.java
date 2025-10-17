package com.unimag.aeropuerto.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class AirlineDTO {

    public record airlineCreateRequest(
            @NotBlank
            @Size(min = 2, max = 30)
            String name,
            @NotBlank
            @Size(min = 2, max = 30)
            String code) implements Serializable {}

    public record airlineUpdateRequest(
            @NotNull
            Long airlineId,
            @NotBlank
            @Size(min = 2, max = 30)
            String name,
            @NotBlank
            @Size(min = 2, max = 30)
            String code) implements Serializable {}


    public record airlineFlightView(Long id, String name, String code) implements Serializable{} // para usar en flightMapper
    public record airlineResponse(Long id, String name, String code, List<FlightDTO.flightAirlineView> flights) implements Serializable{}
    public record airlineResponseBasic(Long id, String name, String code) implements Serializable{}
}
