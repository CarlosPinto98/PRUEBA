package com.unimag.aeropuerto.DTO;

import com.unimag.aeropuerto.entidad.Cabin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public class SeatInventoryDTO {

    public record seatInventoryCreateRequest(
            @Positive
            @NotNull
            Integer totalSeats,
            @Positive
            @NotNull
            Integer availableSeats,
            @NotNull
            Cabin cabin) implements Serializable {}

    public record seatInventoryUpdateRequest(
            @Positive
            Integer totalSeats,
            @Positive
            Integer availableSeats,
            Cabin cabin) implements Serializable {}

    public record seatInventoryFlightView(
            Long seatInventoryId,
            Integer totalSeats,
            Integer availableSeats,
            Cabin cabin) implements Serializable {}

    public record seatInventoryDtoResponse(
            Long seatInventoryId,
            Integer totalSeats,
            Integer availableSeats,
            Cabin cabin,
            String flightNumber) implements Serializable{}
}
