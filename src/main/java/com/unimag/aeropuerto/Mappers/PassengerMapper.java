package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.PassengerDTO;
import com.unimag.aeropuerto.entidad.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring", uses = {PassengerProfileMapper.class})

public interface PassengerMapper {

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "id", ignore = true)
    Passenger toEntity(PassengerDTO.passengerCreateRequest dto);

    @Mapping(target ="passengerProfile" ,source = "profile")
    PassengerDTO.passengerResponse toDTO(Passenger entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    void updateEntity(@MappingTarget Passenger passenger, PassengerDTO.passengerUpdateRequest updateRequest);
}
