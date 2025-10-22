package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.API.Error.NotFoundException;
import com.unimag.aeropuerto.DTO.PassengerDTO;
import com.unimag.aeropuerto.Mappers.PassengerMapper;
import com.unimag.aeropuerto.entidad.Passenger;
import com.unimag.aeropuerto.entidad.PassengerProfile;
import com.unimag.aeropuerto.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor

public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerProfileServiceImpl passengerProfileService;
    private final PassengerMapper passengerMapper;


    @Override
    public PassengerDTO.passengerResponse create(PassengerDTO.passengerCreateRequest createRequest) {
        Passenger passenger = passengerMapper.toEntity(createRequest);
        if (createRequest.passengerProfile() != null) {
            var profile =  passengerProfileService.createObject(createRequest.passengerProfile());
            passenger.setProfile(profile);
            profile.setPassenger(passenger);
        }
        var save = passengerRepository.save(passenger);
        return passengerMapper.toDTO(save);
    }

    @Override
    public PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest) {
        var m = this.getObject(id);
        passengerMapper.updateEntity(m, updateRequest);
        if (updateRequest.passengerProfile() != null) {
            if (m.getProfile() == null) {
                m.setProfile(new PassengerProfile());
            }
            passengerProfileService.update(m.getProfile(), updateRequest.passengerProfile());
        }
        return passengerMapper.toDTO(m);
    }

    @Override
    public void delete(Long id) {
        var m = this.getObject(id);
        passengerRepository.delete(m);
    }

    @Override
    public Page<PassengerDTO.passengerResponse> list(Pageable pageable) {
        return  passengerRepository.findAll(pageable).map(passengerMapper::toDTO);
    }

    @Override
    public Passenger getObject(Long id) {
        var p =  passengerRepository.findById(id).orElseThrow(() -> new NotFoundException("Passenger with id " + id + " not found"));
        return p;
    }

    @Override
    public PassengerDTO.passengerResponse get(Long passengerId) {
        return passengerMapper.toDTO(this.getObject(passengerId));
    }
}
