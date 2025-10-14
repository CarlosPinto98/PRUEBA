package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Airport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class AirportRepositoryTest extends AbstractRepositoryPSQL {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    @DisplayName("Busca aeropuerto por codigo")
    void findByCode() {
        Airport airport = new Airport();
        airport.setCode("123");
        airport.setName("Aeropuerto");
        airportRepository.save(airport);

        Optional<Airport> result = airportRepository.findByCode("123");

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("123");

    }

}