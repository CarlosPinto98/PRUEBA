package com.unimag.aeropuerto.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public class FlightDTO {

    public record flightCreateRequest(
            @NotBlank
            @Size(min = 2, max = 30)
            String number,
            OffsetDateTime arrivalTime,
            OffsetDateTime departureTime,
            @NotNull
            Long airlineId,
            @NotBlank
            @Size(min = 2, max = 30)
            String originAirportCode,
            @NotBlank
            @Size(min = 2, max = 30)
            String destinationAirportCode,
            @NotEmpty
            Set<String> tags,
            List<SeatInventoryDTO.seatInventoryCreateRequest> seatInventories
    ) implements Serializable {}

    public record flightUpdateRequest(
            @Size(min = 2, max = 30)
            String number,
            OffsetDateTime arrivalTime,
            OffsetDateTime departureTime,
            Long airlineId,
            @Size(min = 2, max = 30)
            String originAirportCode,
            @Size(min = 2, max = 30)
            String destinationAirportCode,
            Set<String> tags ) implements Serializable {}



    public record flightResponse(
            Long flightId,
            String number,
            OffsetDateTime arrivalTime,
            OffsetDateTime departureTime,
            AirlineDTO.airlineFlightView airline,
            AirportDTO.AirportFlightView originAirport,
            AirportDTO.AirportFlightView destinationAirport,
            Set<TagDTO.tagResponse> tags,
            List<SeatInventoryDTO.seatInventoryFlightView> seatInventories) implements Serializable {}


    public record flightAirportView(Long flightId, String number, OffsetDateTime arrivalTime,
                                    OffsetDateTime departureTime, AirlineDTO.airlineFlightView airline, Set<TagDTO.tagResponse> tags) implements Serializable {}

    public record flightAirlineView(Long flightId, String number, OffsetDateTime arrivalTime, OffsetDateTime departureTime, Set<TagDTO.tagResponse> tags) implements Serializable {}
}
