package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Cabin;
import com.unimag.aeropuerto.entidad.Flight;
import com.unimag.aeropuerto.entidad.SeatInventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class SeatInventoryRepositoryTest extends AbstractRepositoryPSQL{


    @Autowired
    private SeatInventoryRepository seatInventoryRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Test
    @DisplayName("Inventario de asientos para cabina especifica")
    void findSeatInventoryByFlightIdAndCabinType() {

        Flight flight = createAndSaveFlight();
        var inventory = SeatInventory.builder().flight(flight).cabin(Cabin.ECONOMY).totalSeats(100).availableSeats(50).build();
        seatInventoryRepository.save(inventory);

        Optional<SeatInventory> foundInventory = seatInventoryRepository.findSeatInventoryByFlightIdAndCabinType(flight.getOriginAirport().getId(), Cabin.ECONOMY);

        assertThat(foundInventory).isPresent();
        assertThat(foundInventory.get().getFlight().getId()).isEqualTo(flight.getId());
        assertThat(foundInventory.get().getCabin()).isEqualTo(Cabin.ECONOMY);
    }

    @Test
    @DisplayName("Verifica si Asientos disponibles >= min para ese vuelo y cabina")
    void existsBySeatIdAndCabinTypeGreaterThanMin() {

        Flight flight = createAndSaveFlight();
        var inventory = SeatInventory.builder().flight(flight).cabin(Cabin.BUSINESS).totalSeats(30).availableSeats(20).build();
        seatInventoryRepository.save(inventory);

        Boolean hasEnoughSeats = seatInventoryRepository.existsBySeatIdAndCabinTypeGreaterThanMin(flight.getOriginAirport().getId(), Cabin.BUSINESS, 10
        );

        assertThat(hasEnoughSeats).isTrue();
    }

    private Flight createAndSaveFlight() {
        var flight = Flight.builder().build();
        return flightRepository.save(flight);
    }
}