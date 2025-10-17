package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.PassengerProfileDTO;
import com.unimag.aeropuerto.entidad.PassengerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassengerProfileService {

    PassengerProfileDTO.passengerProfileResponse create(PassengerProfileDTO.passengerProfileCreateRequest createRequest);

    PassengerProfile createObject(PassengerProfileDTO.passengerProfileCreateRequest createRequest);
    PassengerProfileDTO.passengerProfileResponse get(Long id);
    PassengerProfile getObject(Long id);
    PassengerProfileDTO.passengerProfileResponse update(Long id, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest);
    PassengerProfileDTO.passengerProfileResponse update(PassengerProfile profile, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest);
    Page<PassengerProfileDTO.passengerProfileResponse> list(Pageable pageable);
    void delete(Long id);
}
