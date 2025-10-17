package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.*;
import com.unimag.aeropuerto.entidad.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.bind.annotation.Mapping;

//import org.mapstruct.Mapping;



@Mapper(componentModel = "spring", uses = FlightMapper.class)
public interface AirlineMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Airline toEntity(AirlineDTO.airlineCreateRequest request);


    AirlineDTO.airlineResponse toDTO(Airline airline);

    AirlineDTO.airlineFlightView toFlightView(Airline airline);

    @Mapping(target = "flights", ignore = true)
    void updateEntity(@MappingTarget Airline airline, AirlineDTO.airlineUpdateRequest request);
}
