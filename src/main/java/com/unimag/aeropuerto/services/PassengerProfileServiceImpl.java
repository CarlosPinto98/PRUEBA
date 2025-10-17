package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.PassengerProfileDTO;
import com.unimag.aeropuerto.Mappers.PassengerProfileMapper;
import com.unimag.aeropuerto.entidad.PassengerProfile;
import com.unimag.aeropuerto.repository.PassengerProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor

public class PassengerProfileServiceImpl implements PassengerProfileService {

    private final PassengerProfileRepository passengerProfileRepository;
    private final PassengerProfileMapper passengerProfileMapper;

    @Override
    public PassengerProfileDTO.passengerProfileResponse create(PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        var profile = passengerProfileMapper.toEntity(createRequest);
        return passengerProfileMapper.toDTO(passengerProfileRepository.save(profile));
    }

    @Override
    public PassengerProfile createObject(PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        var profile = passengerProfileMapper.toEntity(createRequest);
        profile =  passengerProfileRepository.save(profile);
        return profile;
    }

    @Override
    public PassengerProfileDTO.passengerProfileResponse get(Long id) {
        var profile = this.getObject(id);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public PassengerProfile getObject(Long id) {
        PassengerProfile profile = passengerProfileRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Profile with id " + id + " not found"
        ));
        return profile;
    }

    @Override
    public PassengerProfileDTO.passengerProfileResponse update(Long id, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        var profile = this.getObject(id);
        passengerProfileMapper.updateEntity(updateRequest, profile);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public PassengerProfileDTO.passengerProfileResponse update(PassengerProfile profile, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        passengerProfileMapper.updateEntity(updateRequest, profile);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public Page<PassengerProfileDTO.passengerProfileResponse> list(Pageable pageable) {
        return passengerProfileRepository.findAll(pageable).map(passengerProfileMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        var profile = this.getObject(id);
        passengerProfileRepository.delete(profile);
    }
}
