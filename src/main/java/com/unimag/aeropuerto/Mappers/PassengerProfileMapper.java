package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.PassengerProfileDTO;
import com.unimag.aeropuerto.entidad.PassengerProfile;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

public interface PassengerProfileMapper {

    @Mapping(target = "phone", source = "phoneNumber")
    PassengerProfile toEntity(PassengerProfileDTO.passengerProfileCreateRequest dto);

    @Mapping(target = "phoneNumber", source = "phone")
    @Mapping(target = "passengerProfileID", source = "id")
    PassengerProfileDTO.passengerProfileResponse toDTO(PassengerProfile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", source = "phoneNumber" )
    void updateEntity(PassengerProfileDTO.passengerProfileUpdateRequest dto, @MappingTarget PassengerProfile entity);

    PassengerProfileDTO.passengerProfileView toPassengerProfileView(PassengerProfile passengerProfile);
}
