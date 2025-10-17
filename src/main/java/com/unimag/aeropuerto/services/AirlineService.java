package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.AirlineDTO;
import com.unimag.aeropuerto.entidad.Airline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirlineService {

    public AirlineDTO.airlineResponse create(AirlineDTO.airlineCreateRequest Req);
    public AirlineDTO.airlineResponse get(Long id);
    public Airline getObjectById(Long id);
    public Page<AirlineDTO.airlineResponse> list(Pageable pageable);
    public void delete(Long id);
    public AirlineDTO.airlineResponse update(Long id, AirlineDTO.airlineUpdateRequest Req);
}
