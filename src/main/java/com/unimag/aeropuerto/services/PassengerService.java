package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.PassengerDTO;
import com.unimag.aeropuerto.entidad.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassengerService {

    PassengerDTO.passengerResponse create(PassengerDTO.passengerCreateRequest createRequest);
    PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest);
    void delete(Long id);
    Page<PassengerDTO.passengerResponse> list(Pageable pageable);
    Passenger getObject(Long id);
    PassengerDTO.passengerResponse get(Long passengerId);
}
