package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.FlightDTO;
import com.unimag.aeropuerto.entidad.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    FlightDTO.flightResponse create(FlightDTO.flightCreateRequest createRequest);

    FlightDTO.flightResponse update(Long id, FlightDTO.flightUpdateRequest updateRequest);

    FlightDTO.flightResponse get(Long id);

    Page<FlightDTO.flightResponse> list(Pageable pageable);

    void delete(Long id);

    Flight getFlightObject(Long id);
}
