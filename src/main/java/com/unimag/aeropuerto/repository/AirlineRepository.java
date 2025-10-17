package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository  extends JpaRepository<Airline, Long> {


    Optional<Airline> findAirlineById(Long id);
    Optional<Airline> findAirlineByCodeIgnoreCase(String code);
    Optional<Airline> findAirlineByNameIgnoreCase(String name);
}
