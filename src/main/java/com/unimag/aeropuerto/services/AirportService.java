package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.AirportDTO;
import com.unimag.aeropuerto.entidad.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirportService {

    AirportDTO.AirportResponse create(AirportDTO.AirportCreateRequest request);

    AirportDTO.AirportResponse get(Long id);

    Airport getObjectById(Long id);

    AirportDTO.AirportResponse update(Long id, AirportDTO.AirportUpdateRequest request);
    void delete(Long id);
    Page<AirportDTO.AirportResponse> list(Pageable pageable);
}
